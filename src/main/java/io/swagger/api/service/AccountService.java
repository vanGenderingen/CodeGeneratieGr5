package io.swagger.api.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.api.controllers.AccountsApiController;
import io.swagger.api.repository.AccountRepository;
import io.swagger.api.repository.UserRepository;
import io.swagger.model.Account;
import io.swagger.model.DTO.CreateAccountDTO;
import io.swagger.model.DTO.GetAccountDTO;
import io.swagger.model.DTO.UpdateAccountDTO;
import io.swagger.model.User;
import org.hibernate.MappingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class AccountService {
    private static final Logger log = LoggerFactory.getLogger(AccountsApiController.class);

    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ObjectMapper objectMapper;

    public Account add(CreateAccountDTO createAccountDTO) {
        try {
            // Get the user
            User user = getUserByID(createAccountDTO.getUserId());

            // Set the user and generate IBAN
            Account account = objectMapper.convertValue(createAccountDTO, Account.class);
            account.setUser(user);
            account.setIBAN(IBANService.generateIBAN());

            // Save the account
            return accountRepository.save(account);
        }catch (IllegalArgumentException | NullPointerException e){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Failed to add account due to invalid data");
        }
    }
    public List<GetAccountDTO> getAllAccounts(Integer limit, Integer offset, String searchStrings, String IBAN) {
        return convertAccountsToGetAccountToDTO(accountRepository.getAll(IBAN, searchStrings, PageRequest.of(offset, limit)));
    }

    public GetAccountDTO getAccountByAccountID(UUID accountID) {
        try {
            return objectMapper.convertValue(accountRepository.getAccountByAccountID(accountID), GetAccountDTO.class);
        } catch (MappingException e) {
            log.error("Couldn't serialize response for content type application/json", e);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Couldn't serialize response for content type application/json");
        }catch (NullPointerException e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "This account does not exist");
        }
    }
    public List<GetAccountDTO> getAccountsOfUser(UUID userId, Integer limit, Integer offset, String searchStrings) {
        return convertAccountsToGetAccountToDTO(accountRepository.getAccountsOfUser(userId, searchStrings, PageRequest.of(offset, limit)));
    }

    public GetAccountDTO updateAccount(UUID accountID, UpdateAccountDTO updateAccountDTO) {
        try {
            return objectMapper.convertValue(accountRepository.save(updateAccountFieldsIfNeeded(
                    accountRepository.getAccountByAccountID(accountID), updateAccountDTO)), GetAccountDTO.class);
        } catch (MappingException e) {
            log.error("Couldn't serialize response for content type application/json", e);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Couldn't serialize response for content type application/json");
        }catch (NullPointerException e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "This account does not exist");
        }
    }

    // Check if user exists
    // Directly use the repository instead of using the service since we need a whole user and not a DTO
    private User getUserByID(UUID userID) {
        User user = userRepository.getUserByUserID(userID);
        if (user == null){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "This user does not exist");
        }
        return user;
    }

    // Convert A list of accounts to a list of GetAccountDTOs
    private List<GetAccountDTO> convertAccountsToGetAccountToDTO(List<Account> accounts) {
        if (accounts == null) throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No Accounts found");
        try {
            return accounts.stream()
                    .map(account -> objectMapper.convertValue(account, GetAccountDTO.class))
                    .collect(Collectors.toList());
        } catch (MappingException e) {
            log.error("Couldn't serialize response for content type application/json", e);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Couldn't serialize response for content type application/json");
        }
    }

    // Make sure that only the fields that are not null are updated
    private Account updateAccountFieldsIfNeeded(Account existingAccount, UpdateAccountDTO updateAccountDTO) {
        if (updateAccountDTO.getName() != null) {
            existingAccount.setName(updateAccountDTO.getName());
        }
        if (updateAccountDTO.getBalance() != null) {
            existingAccount.setBalance(updateAccountDTO.getBalance());
        }
        if (updateAccountDTO.getMinBal() != null) {
            existingAccount.setMinBal(updateAccountDTO.getMinBal());
        }
        if (updateAccountDTO.isActive() != null) {
            existingAccount.setActive(updateAccountDTO.isActive());
        }
        return existingAccount;
    }
}
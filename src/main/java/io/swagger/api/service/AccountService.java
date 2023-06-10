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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class AccountService {
    private static final Logger log = LoggerFactory.getLogger(AccountsApiController.class);

    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private UserService userService;

    public ResponseEntity<Account> add(CreateAccountDTO createAccountDTO) {
        UUID userId = createAccountDTO.getUserId();
        User user = userRepository.getUserByUserID(userId);

        if (user == null) {
            // Handle the case when the user does not exist
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        Account account = objectMapper.convertValue(createAccountDTO, Account.class);
        account.setUser(user);
        Account result = accountRepository.save(account);

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    public ResponseEntity<List<GetAccountDTO>> getAllAccounts(Integer limit, Integer offset, String searchStrings, String IBAN) {
        try {
            Pageable pageable = PageRequest.of(offset, limit);
            List<Account> accounts = accountRepository.getAll(IBAN, searchStrings, pageable);
            List<GetAccountDTO> accountDTOS = new ArrayList<>();
            for (Account account : accounts) {
                GetAccountDTO accountDTO = objectMapper.convertValue(account, GetAccountDTO.class);
                accountDTOS.add(accountDTO);
            }
            return new ResponseEntity<>(accountDTOS, HttpStatus.OK);
        } catch (Exception e) {
            log.error("Couldn't serialize response for content type application/json", e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<GetAccountDTO> getAccountByAccountID(UUID accountID) {
        try {
            Account account = accountRepository.getAccountByAccountID(accountID);
            GetAccountDTO accountDTO = objectMapper.convertValue(account, GetAccountDTO.class);
            return new ResponseEntity<>(accountDTO, HttpStatus.OK);
        } catch (Exception e) {
            log.error("Couldn't serialize response for content type application/json", e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    public ResponseEntity<List<GetAccountDTO>> getAccountsOfUser(UUID userId, Integer limit, Integer offset, String searchStrings) {
        try {
            Pageable pageable = PageRequest.of(offset, limit);
            Page<Account> accounts = accountRepository.getAccountsOfUser(userId, searchStrings, pageable);
            List<GetAccountDTO> accountDTOS = new ArrayList<>();
            for (Account account : accounts) {
                GetAccountDTO accountDTO = objectMapper.convertValue(account, GetAccountDTO.class);
                accountDTOS.add(accountDTO);
            }
            int totalAccounts = accountRepository.countAccountsOfUser(userId, searchStrings);
            return ResponseEntity.ok()
                    .header("X-Total-Accounts", String.valueOf(totalAccounts))
                    .body(accountDTOS);
        } catch (Exception e) {
            log.error("Couldn't serialize response for content type application/json", e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public int getTotalPages(UUID userId, String searchStrings) {
        return accountRepository.countAccountsOfUser(userId, searchStrings);
    }

    public ResponseEntity<GetAccountDTO> updateAccount(UUID accountID, UpdateAccountDTO updateAccountDTO) {
        try {
            Account existingAccount = accountRepository.getAccountByAccountID(accountID);
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
            Account updatedAccount = accountRepository.save(existingAccount);
            GetAccountDTO accountDTO = objectMapper.convertValue(updatedAccount, GetAccountDTO.class);
            return new ResponseEntity<>(accountDTO, HttpStatus.OK);
        } catch (Exception e) {
            log.error("Couldn't serialize response for content type application/json", e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
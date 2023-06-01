package io.swagger.api.service;

import io.swagger.api.exceptions.ValidationException;
import io.swagger.api.repository.AccountRepository;
import io.swagger.model.Account;
import io.swagger.model.DTO.UpdateAccountDTO;
import io.swagger.model.DTO.UpdateUserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class AccountService {

    @Autowired
    private AccountRepository accountRepository;

    public Account add(Account account) {
        return accountRepository.save(account);
    }

    public List<Account> getAllAccounts() {
        return accountRepository.findAll();
    }

    public Account getAccountByAccountID(UUID accountID) {
        return accountRepository.getAccountByAccountID(accountID);
    }

    public Account update(UpdateAccountDTO updateAccountDTO, UUID accountID) throws ValidationException {
        Account existingAccount = getAccountByAccountID(accountID);
        existingAccount.setName(updateAccountDTO.getName());
        existingAccount.setBalance(updateAccountDTO.getBalance());
        existingAccount.setMinBal(updateAccountDTO.getMinBal());
        existingAccount.setActive(updateAccountDTO.isActive());
        try {
            return accountRepository.save(existingAccount);
        } catch (Exception e) {
            throw new ValidationException("Error while updating user");
        }
    }
}

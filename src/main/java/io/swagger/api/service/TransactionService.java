package io.swagger.api.service;

import io.swagger.api.repository.AccountRepository;
import io.swagger.api.repository.TransactionRepository;
import io.swagger.api.repository.UserRepository;
import io.swagger.api.specification.SearchCriteria;
import io.swagger.api.specification.TransactionSpecification;
import io.swagger.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class TransactionService {

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private AccountRepository accountsRepository;

    @Autowired
    private UserRepository userRepository;

    public Transaction add(Transaction transaction) {
        if(transaction.getAmount() < 0){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Amount cannot be negative");
        }
        //Get the account from the IBAN
        Account fromAccount = getAccountByIBAN(transaction.getFromIBAN());
        Account toAccount = getAccountByIBAN(transaction.getToIBAN());

        //Verify transaction and daily limit
        User fromUser = userRepository.getUserByUserID(fromAccount.getUserID());
        validateTransactionLimit(fromUser, transaction.getAmount());
        validateDailyLimit(fromUser, transaction.getFromIBAN(), transaction.getAmount());

        if(!validateUserIsEmployee(transaction.getUserPerforming()) && (transaction.getTransactionType() == TransactionType.TRANSFER)){
            // Verify if the user is the owner of the account
            validateFromAccountIsFromPerformingUser(transaction.getUserPerforming(), fromUser);
            // Verify if the account is a savings account and from the user
            validateSavingsAccountIsFromUser(toAccount.getType(), fromAccount, toAccount);
        }

        //Verify if the account has enough money
        validateAccountHasEnoughMoney(fromAccount, transaction.getAmount());

        fromAccount.setBalance(fromAccount.getBalance() - transaction.getAmount());
        toAccount.setBalance(toAccount.getBalance() + transaction.getAmount());
        accountsRepository.save(fromAccount);
        accountsRepository.save(toAccount);

        return transactionRepository.save(transaction);
    }

    public List<Transaction> getTransactions(Integer offset, Integer limit, SearchCriteria filters, Principal principal) {
        if(!validateUserIsEmployee(UUID.fromString(principal.getName()))){
            if (filters.getAccountID() == null){
                List<Account> userAccounts = accountsRepository.getAccountsByUserID(UUID.fromString(principal.getName()));

                List<Transaction> transactions = new ArrayList<>();
                for(Account account : userAccounts){
                    filters.setAccountID(account.getAccountID());
                    transactions.addAll(getAllTransactions(PageRequest.of(offset, limit), new TransactionSpecification(setCriteriaForAccount(filters, account))));
                }
                return transactions.stream()
                        .distinct()
                        .collect(Collectors.toList());
            }
            Account account = accountsRepository.getAccountByAccountID(filters.getAccountID());
            return getAllTransactions(PageRequest.of(offset, limit), new TransactionSpecification(setCriteriaForAccount(filters, account)));

        }
        if (filters.getAccountID() != null) {
            Account account = accountsRepository.getAccountByAccountID(filters.getAccountID());
            filters.setToIBAN(account.getIBAN());
            filters.setFromIBAN(account.getIBAN());
        }
        return getAllTransactions(PageRequest.of(offset, limit), new TransactionSpecification(filters));
    }

    public List<Transaction> getAllTransactions(PageRequest pageRequest, TransactionSpecification specification) {
        return transactionRepository.findAll(specification, pageRequest).getContent();
    }

    public Account getAccountByIBAN(String IBAN) {
        if (accountsRepository.getAccountByIBAN(IBAN) != null) {
            return accountsRepository.getAccountByIBAN(IBAN);
        }
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "The IBAN is not valid");
    }

    public boolean validateUserIsEmployee(UUID userPerforming){
        try {
            User user = userRepository.getUserByUserID(userPerforming);
            return user.getRoles().contains(Role.ROLE_EMPLOYEE);
        } catch (NullPointerException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "The user performing the transaction is not valid");
        }
    }

    public void validateFromAccountIsFromPerformingUser(UUID userPerforming, User fromUser){
        try {
            if (!Objects.equals(fromUser.getUserID().toString(), userPerforming.toString())){
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "You can't perform a transaction from an account that isn't yours");
            }
        } catch (NullPointerException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "The user performing the transaction is not valid");
        }
    }

    public void validateSavingsAccountIsFromUser(AccountType accountType, Account fromAccount, Account toAccount){
        if (accountType == AccountType.SAVINGS && !Objects.equals(fromAccount.getUserID().toString(), toAccount.getUserID().toString())){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "You can't perform a transaction to a savings account that isn't yours");
        }
    }

    public void validateAccountHasEnoughMoney(Account fromAccount, double amount){
        if (fromAccount.getBalance() < fromAccount.getMinBal()+amount) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "You don't have enough money to transfer this amount");
        }
    }

    public void validateTransactionLimit(User user, double amount){
        if (user.getTransactionLimit() < amount) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "You can't withdraw more than your transaction limit");
        }
    }

    public void validateDailyLimit(User user, String fromIBAN, double amount) {
        SearchCriteria searchCriteria = new SearchCriteria();
        searchCriteria.setFromIBAN(fromIBAN);
        searchCriteria.setDate(LocalDateTime.now());

        List<Account> userAccounts = accountsRepository.getAccountsByUserID(user.getUserID());
        List<Transaction> transactions = new ArrayList<>();

        for(Account account : userAccounts){
            searchCriteria.setAccountID(account.getAccountID());
            transactions.addAll(transactionRepository.findAll(new TransactionSpecification(searchCriteria)));
        }

        Double dailyLimit = user.getDailyLimit();
        double total = transactions.stream()
                .distinct()
                .mapToDouble(Transaction::getAmount)
                .sum();

        if (dailyLimit < total + amount) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "You can't withdraw more than your daily limit");
        }
    }

    public SearchCriteria setCriteriaForAccount(SearchCriteria searchCriteria, Account account) {
        if (searchCriteria.getFromIBAN() == null && searchCriteria.getToIBAN() == null) {
            searchCriteria.setToIBAN(account.getIBAN());
            searchCriteria.setFromIBAN(account.getIBAN());
        } else if (searchCriteria.getToIBAN() == null) {
            searchCriteria.setToIBAN(account.getIBAN());
        } else {
            searchCriteria.setFromIBAN(account.getIBAN());
        }
        return searchCriteria;
    }
}

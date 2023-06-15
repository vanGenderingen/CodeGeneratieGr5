package io.swagger.api.service;

import io.swagger.api.controllers.UsersApiController;
import io.swagger.api.repository.AccountRepository;
import io.swagger.api.repository.TransactionRepository;
import io.swagger.api.repository.UserRepository;
import io.swagger.api.specification.SearchCriteria;
import io.swagger.api.specification.TransactionSpecification;
import io.swagger.model.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.security.Principal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.UUID;

@Service
public class TransactionService {

    private static final Logger log = LoggerFactory.getLogger(UsersApiController.class);

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

        //Verify if the account has enough money
        validateAccountHasEnoughMoney(fromAccount, transaction.getAmount());

        Account toAccount = getAccountByIBAN(transaction.getToIBAN());

        //Verify transaction and daily limit
        User fromUser = userRepository.getUserByUserID(fromAccount.getUserID());
        validateAmountIsLowerThanTransactionLimit(fromUser, transaction.getAmount());
        validateDailyLimit(fromUser, transaction.getFromIBAN(), transaction.getAmount());

        if(!validatePerformingUserIsEmployee(transaction.getUserPerforming())){
            // Verify if the user is the owner of the account
            validateFromAccountIsFromPerformingUser(transaction.getUserPerforming(), fromAccount);
            // Verify if the account is a savings account and from the user
            validateSavingsAccountIsFromUser(fromAccount.getType(), fromAccount, toAccount);
        }

        fromAccount.setBalance(fromAccount.getBalance() - transaction.getAmount());
        toAccount.setBalance(toAccount.getBalance() + transaction.getAmount());
        accountsRepository.save(fromAccount);
        accountsRepository.save(toAccount);

        return transactionRepository.save(transaction);
    }

    public List<Transaction> getTransactions(Integer offset, Integer limit, SearchCriteria filters, Principal principal) {
        //Add user verification
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

    public boolean validatePerformingUserIsEmployee(UUID userPerforming){
        try {
            User user = userRepository.getUserByUserID(userPerforming);
            return user.getRoles().contains(Role.ROLE_EMPLOYEE);
        } catch (NullPointerException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "The user performing the transaction is not valid");
        }
    }

    public void validateFromAccountIsFromPerformingUser(UUID userPerforming, Account fromAccount){
        try {
            User user = userRepository.getUserByUserID(userPerforming);
            if (user.getUserID() != fromAccount.getUserID()){
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "You can't perform a transaction from an account that isn't yours");
            }
        } catch (NullPointerException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "The user performing the transaction is not valid");
        }
    }

    public void validateSavingsAccountIsFromUser(AccountType accountType, Account fromAccount, Account toAccount){
        if (accountType == AccountType.SAVINGS&& fromAccount.getUserID() != toAccount.getUserID()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "You can't perform a transaction to a savings account that isn't yours");
        }
    }

    public void validateAccountHasEnoughMoney(Account fromAccount, double amount){
        if (fromAccount.getBalance() < fromAccount.getMinBal()+amount) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "You don't have enough money to transfer this amount");
        }
    }

    public void validateAmountIsLowerThanTransactionLimit(User user, double amount){
        if (user.getTransactionLimit() < amount) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "You can't withdraw more than your transaction limit");
        }
    }

    public void validateDailyLimit(User user, String fromIBAN, double amount){
        SearchCriteria searchCriteria = new SearchCriteria();
        searchCriteria.setFromIBAN(fromIBAN);

        LocalDate date = LocalDate.now();

        DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE_TIME;
        LocalDateTime currentDate = date.atStartOfDay();

        // Convert LocalDateTime to string using the formatter
        String dateString = currentDate.format(formatter);

        //TODO: Hij haalt hier de exacte start van de dag op. Je moet dus nog toevoegen dat hij alles na deze datum pakt. Misschien met een if statement ofzo?

        // Set the converted string as the search criteria date
        searchCriteria.setDate(LocalDateTime.parse(dateString));

        List<Transaction> transactions = transactionRepository.findAll(new TransactionSpecification(searchCriteria));

        Double dailyLimit = user.getDailyLimit();
        double total = transactions.stream()
                .mapToDouble(Transaction::getAmount)
                .sum();

        if (dailyLimit < total + amount) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "You can't withdraw more than your daily limit");
        }

    }


}

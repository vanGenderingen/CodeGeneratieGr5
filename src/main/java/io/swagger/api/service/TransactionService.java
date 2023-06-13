package io.swagger.api.service;

import io.swagger.api.repository.AccountRepository;
import io.swagger.api.repository.TransactionRepository;
import io.swagger.api.repository.UserRepository;
import io.swagger.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class TransactionService {

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private AccountRepository accountsRepository;

    @Autowired
    private UserRepository userRepository;


    public Transaction add(Transaction transaction) {
        Account fromAccount = accountsRepository.getAccountByIBAN(transaction.getFromIBAN());
        Account toAccount = accountsRepository.getAccountByIBAN(transaction.getToIBAN());

        if (fromAccount == null || toAccount == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "The IBAN is not valid");
        }

        User userPerforming = userRepository.getUserByUserID(transaction.getUserPerforming());

        if (userPerforming == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "The user performing the transaction is not valid");
        }

        User fromUser = userRepository.getUserByUserID(fromAccount.getUserID());
        User toUser = userRepository.getUserByUserID(toAccount.getUserID());

        if(!userPerforming.getRoles().contains(Role.ROLE_EMPLOYEE)){
            if(userPerforming.getUserID() != fromUser.getUserID()){
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "You can't perform a transaction from an account that isn't yours");
            }
            if (toAccount.getType() == Account.TypeEnum.SAVINGS && fromUser.getUserID() != toUser.getUserID()){
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "You can't transfer money to a savings account that isn't yours");
            }
            if (fromAccount.getType() == Account.TypeEnum.SAVINGS && fromUser.getUserID() != toUser.getUserID()) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "You can't transfer money from a savings account that isn't yours");
            }
        }

        if (fromAccount.getBalance() < fromAccount.getMinBal()+transaction.getAmount()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "You don't have enough money to transfer this amount");
        }
        if (fromUser.getTransactionLimit() < transaction.getAmount()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "You can't withdraw more than your transaction limit");
        }

        fromAccount.setBalance(fromAccount.getBalance() - transaction.getAmount());
        toAccount.setBalance(toAccount.getBalance() + transaction.getAmount());
        accountsRepository.save(fromAccount);
        accountsRepository.save(toAccount);

        return transactionRepository.save(transaction);
    }

    public List<Transaction> getTransactions(Integer offset, Integer limit, IBANFilter accountFilter, AmountFilter amountFilter, String type) {
        Pageable pageRequest = PageRequest.of(offset, limit);

        if (accountFilter == null && amountFilter == null && type == null) {
            return transactionRepository.findAll(pageRequest).getContent();
        }

        if (accountFilter.getFromIBAN() != null) {
            if (amountFilter.allNull()){
                return transactionRepository.getTransactionsByFromIBAN(accountFilter.getFromIBAN(), pageRequest);
            }
            String fromIBAN = accountFilter.getFromIBAN();
            return processAccountFilter(fromIBAN, null, amountFilter, pageRequest);
        }

        if (accountFilter.getToIBAN() != null) {
            if (amountFilter.allNull()){
                return transactionRepository.getTransactionsByToIBAN(accountFilter.getToIBAN(), pageRequest);
            }
            String toIBAN = accountFilter.getToIBAN();
            return processAccountFilter(null, toIBAN, amountFilter, pageRequest);
        }

        if (accountFilter.getAccountID() != null) {
            Account account = accountsRepository.getAccountByAccountID(accountFilter.getAccountID());
            if (account == null) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "The account ID is not valid");
            }
            if (amountFilter.allNull()){
                return transactionRepository.getTransactionsByToIBANAndFromIBAN(account.getIBAN(), account.getIBAN(), pageRequest);
            }
            String accountIBAN = account.getIBAN();
            return processAccountFilter(accountIBAN, accountIBAN, amountFilter, pageRequest);
        }

        if (amountFilter != null){
            return processAccountFilter(null, null, amountFilter, pageRequest);
        }

        return transactionRepository.findAll(pageRequest).getContent();

    }

    private List<Transaction> processAccountFilter(String fromIBAN, String toIBAN, AmountFilter amountFilter, Pageable pageRequest) {
        if (amountFilter.getHigher() != null) {
            Double higherAmount = amountFilter.getHigher();
            if (fromIBAN != null && toIBAN != null) {
                return transactionRepository.getTransactionsByToIBANAndFromIBANAndAmountGreaterThan(toIBAN, fromIBAN, higherAmount, pageRequest);
            } else if (fromIBAN != null) {
                return transactionRepository.getTransactionsByFromIBANAndAmountGreaterThan(fromIBAN, higherAmount, pageRequest);
            } else if (toIBAN != null) {
                return transactionRepository.getTransactionsByToIBANAndAmountGreaterThan(toIBAN, higherAmount, pageRequest);
            } else {
                return transactionRepository.getTransactionsByAmountGreaterThan(higherAmount, pageRequest);
            }
        }

        if (amountFilter.getLower() != null) {
            Double lowerAmount = amountFilter.getLower();
            if (fromIBAN != null && toIBAN != null) {
                return transactionRepository.getTransactionsByToIBANAndFromIBANAndAmountLessThan(toIBAN, fromIBAN, lowerAmount, pageRequest);
            } else if (fromIBAN != null) {
                return transactionRepository.getTransactionsByFromIBANAndAmountLessThan(fromIBAN, lowerAmount, pageRequest);
            } else if (toIBAN != null) {
                return transactionRepository.getTransactionsByToIBANAndAmountLessThan(toIBAN, lowerAmount, pageRequest);
            } else {
                return transactionRepository.getTransactionsByAmountLessThan(lowerAmount, pageRequest);
            }
        }

        if (amountFilter.getEqual() != null) {
            Double equalAmount = amountFilter.getEqual();
            if (fromIBAN != null && toIBAN != null) {
                return transactionRepository.getTransactionsByToIBANAndFromIBANAndAmountEquals(toIBAN, fromIBAN, equalAmount, pageRequest);
            } else if (fromIBAN != null) {
                return transactionRepository.getTransactionsByFromIBANAndAmountEquals(fromIBAN, equalAmount, pageRequest);
            } else if (toIBAN != null) {
                return transactionRepository.getTransactionsByToIBANAndAmountEquals(toIBAN, equalAmount, pageRequest);
            } else {
                return transactionRepository.getTransactionsByAmountEquals(equalAmount, pageRequest);
            }
        }

        return transactionRepository.findAll(pageRequest).getContent();
    }



}

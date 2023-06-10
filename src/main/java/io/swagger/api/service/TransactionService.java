package io.swagger.api.service;

import io.swagger.api.repository.AccountRepository;
import io.swagger.api.repository.TransactionRepository;
import io.swagger.api.repository.UserRepository;
import io.swagger.model.Account;
import io.swagger.model.Transaction;
import io.swagger.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.UUID;

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

        User fromUser = userRepository.getUserByUserID(fromAccount.getUserID());
        User toUser = userRepository.getUserByUserID(toAccount.getUserID());

        if (transaction.getTransactionType() == Transaction.TransactionTypeEnum.WITHDRAWAL && fromAccount.getBalance() < fromAccount.getMinBal()+transaction.getAmount()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "You don't have enough money to withdraw");
        }
        if (transaction.getTransactionType() == Transaction.TransactionTypeEnum.WITHDRAWAL && fromUser.getTransactionLimit() < transaction.getAmount()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "You can't withdraw more than your transaction limit");
        }
//        if (toAccount.getType() == Account.TypeEnum.SAVINGS && fromUser.getUserID() != toUser.getUserID()){
//            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "You can't transfer money to a savings account that isn't yours");
//        }
//        if (fromAccount.getType() == Account.TypeEnum.SAVINGS && fromUser.getUserID() != toUser.getUserID()) {
//            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "You can't transfer money from a savings account that isn't yours");
//        }

        fromAccount.setBalance(fromAccount.getBalance() - transaction.getAmount());
        toAccount.setBalance(toAccount.getBalance() + transaction.getAmount());
        accountsRepository.save(fromAccount);
        accountsRepository.save(toAccount);

        return transactionRepository.save(transaction);
    }

    public List<Transaction> getTransactions(Integer offset, Integer limit, String toIBAN, String fromIBAN, Double lower, Double higher, Double equal, UUID accountID, String type) {
        Pageable pageRequest = PageRequest.of(offset, limit);
//        if (dateRange != null){
//            return transactionRepository.getTransactionsByTimeStampBetween(dateRange, dateRange.plusDays(1)).subList(offset, limit);
//        }

        if (toIBAN != null) {
            return transactionRepository.getTransactionsByToIBAN(toIBAN, pageRequest);
        }

        if (fromIBAN != null) {
            return transactionRepository.getTransactionsByFromIBAN(fromIBAN, pageRequest);
        }

        if (lower != null) {
            return transactionRepository.getTransactionsByAmountLessThan(lower, pageRequest);
        }

        if (higher != null) {
            return transactionRepository.getTransactionsByAmountGreaterThan(higher, pageRequest);
        }

        if (equal != null) {
            return transactionRepository.getTransactionsByAmountEquals(equal, pageRequest);
        }

        if (accountID != null) {
            Account account = accountsRepository.getAccountByAccountID(accountID);
            return transactionRepository.getTransactionsByToIBANAndFromIBAN(account.getIBAN(), account.getIBAN(), pageRequest);
        }

        if (type != null) {
            return transactionRepository.getTransactionsByTransactionType(Transaction.TransactionTypeEnum.valueOf(type), pageRequest);
        }

        return transactionRepository.findAll(pageRequest).getContent();
    }
}

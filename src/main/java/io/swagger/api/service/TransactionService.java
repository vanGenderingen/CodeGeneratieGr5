package io.swagger.api.service;

import io.swagger.api.repository.AccountsRepository;
import io.swagger.api.repository.TransactionRepository;
import io.swagger.model.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class TransactionService {

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private AccountsRepository accountsRepository;

    public Transaction add(Transaction transaction) {
<<<<<<< Updated upstream
        return transaction = transactionRepository.save(transaction);
    }

    public List<Transaction> getTransactions(Integer offset, Integer limit, OffsetDateTime dateRange, String toIBAN, String fromIBAN, Double lower, Double higher, Double equal, UUID account, String type) {
=======
<<<<<<< Updated upstream
        transaction = transactionRepository.save(transaction);
        return transaction;
    }

    public List<Transaction> getAllTransactions() {
=======
        return transactionRepository.save(transaction);
    }

    public List<Transaction> getTransactions(Integer offset, Integer limit, OffsetDateTime dateRange, String toIBAN, String fromIBAN, Double lower, Double higher, Double equal, UUID account, String type) {

        if (dateRange != null){
            return transactionRepository.getTransactionsByTimeStampBetween(dateRange, dateRange.plusDays(1)).subList(offset, limit);
        }

        if (toIBAN != null) {
            return transactionRepository.getTransactionsByToIBAN(toIBAN).subList(offset, limit);
        }

        if (fromIBAN != null) {
            return transactionRepository.getTransactionsByFromIBAN(fromIBAN).subList(offset, limit);
        }

        if (lower != null) {
            return transactionRepository.getTransactionsByAmountLessThan(lower).subList(offset, limit);
        }

        if (higher != null) {
            return transactionRepository.getTransactionsByAmountGreaterThan(higher).subList(offset, limit);
        }

        if (equal != null) {
            return transactionRepository.getTransactionsByAmountEquals(equal).subList(offset, limit);
        }

        if (account != null) {
            accountsRepository
            return transactionRepository.getTransactionsByToIBANAndFromIBAN(account.toString(), account.toString()).subList(offset, limit);
        }

        if (offset != null) {
            return transactionRepository.findAll().subList(offset, limit);
        }
>>>>>>> Stashed changes
>>>>>>> Stashed changes
        return transactionRepository.findAll();

    }
}

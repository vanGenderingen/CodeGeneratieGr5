package io.swagger.api.service;

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

    public Transaction add(Transaction transaction) {
        return transaction = transactionRepository.save(transaction);
    }

    public List<Transaction> getTransactions(Integer offset, Integer limit, OffsetDateTime dateRange, String toIBAN, String fromIBAN, Double lower, Double higher, Double equal, UUID account, String type) {
        return transactionRepository.findAll();

    }
}

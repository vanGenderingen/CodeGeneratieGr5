package io.swagger.api.service;

import io.swagger.api.repository.TransactionRepository;
import io.swagger.model.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TransactionService {

    @Autowired
    private TransactionRepository transactionRepository;

    public Transaction add(Transaction transaction) {
        transaction = transactionRepository.save(transaction);
        return transaction;
    }

    public List<Transaction> getAllTransactions() {
        return null;
    }
}

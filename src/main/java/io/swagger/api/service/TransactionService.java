package io.swagger.api.service;

import io.swagger.api.repository.TransactionRepository;
import io.swagger.model.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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
        return transactionRepository.findAll();
    }

    public List<Transaction> getTransactionsByIBAN(String iban) {
        List<Transaction> transactions = new ArrayList<>();
        transactions.addAll(transactionRepository.getTransactionsByToIBAN(iban));
        transactions.addAll(transactionRepository.getTransactionsByFromIBAN(iban));
        return transactions;
    }
}

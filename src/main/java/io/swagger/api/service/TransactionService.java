package io.swagger.api.service;

import io.swagger.api.repository.TransactionRepository;
import io.swagger.model.Account;
import io.swagger.model.Transaction;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class TransactionService {

    @Autowired
    private TransactionRepository transactionRepository;

//    public Transaction add(Transaction transaction) {
////        Account sender = transaction.getSender();
////        Account receiver = transaction.getReceiver();
//        transactionRepository.save(transaction);
//        return transaction;
//    }

    public List<Transaction> getAllTransactions() {
        return transactionRepository.findAll();
    }
}

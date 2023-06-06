package io.swagger.api.service;

import io.swagger.api.repository.AccountRepository;
import io.swagger.api.repository.TransactionRepository;
import io.swagger.model.Account;
import io.swagger.model.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class TransactionService {

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private AccountRepository accountsRepository;

    public Transaction add(Transaction transaction) {
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

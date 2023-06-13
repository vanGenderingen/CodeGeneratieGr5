package io.swagger.api.repository;

import io.swagger.model.transactions.Transaction;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long>, PagingAndSortingRepository<Transaction, Long> {
    List<Transaction> getTransactionsByAmountLessThan(Double amount, Pageable pageable);
    List<Transaction> getTransactionsByAmountGreaterThan(Double amount, Pageable pageable);
    List<Transaction> getTransactionsByAmountEquals(Double amount, Pageable pageable);
    List<Transaction> getTransactionsByFromIBANAndAmountGreaterThan(String fromIBAN, Double amount, Pageable pageable);
    List<Transaction> getTransactionsByFromIBANAndAmountLessThan(String fromIBAN, Double amount, Pageable pageable);
    List<Transaction> getTransactionsByFromIBANAndAmountEquals(String fromIBAN, Double amount, Pageable pageable);
    List<Transaction> getTransactionsByFromIBAN(String fromIBAN, Pageable pageable);
    List<Transaction> getTransactionsByToIBANAndAmountLessThan(String toIBAN, Double amount, Pageable pageable);
    List<Transaction> getTransactionsByToIBANAndAmountEquals(String toIBAN, Double amount, Pageable pageable);
    List<Transaction> getTransactionsByToIBAN(String toIBAN, Pageable pageable);
    List<Transaction> getTransactionsByToIBANAndFromIBAN(String toIBAN, String fromIBAN, Pageable pageable);
    List<Transaction> getTransactionsByToIBANAndAmountGreaterThan(String toIBAN, Double amount, Pageable pageable);
    List<Transaction> getTransactionsByToIBANAndFromIBANAndAmountGreaterThan(String toIBAN, String fromIBAN, Double amount, Pageable pageable);
    List<Transaction> getTransactionsByToIBANAndFromIBANAndAmountLessThan(String toIBAN, String fromIBAN, Double amount, Pageable pageable);
    List<Transaction> getTransactionsByToIBANAndFromIBANAndAmountEquals(String toIBAN, String fromIBAN, Double amount, Pageable pageable);

}


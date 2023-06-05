package io.swagger.api.repository;

import io.swagger.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long>, PagingAndSortingRepository<Transaction, Long> {
    List<Transaction> getTransactionsByToIBAN(String iban);
    List<Transaction> getTransactionsByFromIBAN(String iban);
    List<Transaction> getTransactionsByToIBANAndFromIBAN(String toIBAN, String fromIBAN);

//    List<Transaction> getTransactionsByTimeStampBetween(OffsetDateTime from, OffsetDateTime to);

    List<Transaction> getTransactionsByAmountLessThan(Double amount);

    List<Transaction> getTransactionsByAmountGreaterThan(Double amount);

    List<Transaction> getTransactionsByAmountEquals(Double amount);

    List<Transaction> getTransactionsByTransactionType(Transaction.TransactionTypeEnum transactionType);

//    List<Transaction> getTransactionsByOwner(User user);
//
//    boolean existsByOwnerAndId(User owner, Long id);
}

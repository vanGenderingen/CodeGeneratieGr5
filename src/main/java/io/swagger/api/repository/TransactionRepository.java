package io.swagger.api.repository;

import io.swagger.model.Transaction;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long>, PagingAndSortingRepository<Transaction, Long> {
    List<Transaction> getTransactionsByToIBAN(String iban, Pageable pageable);
    List<Transaction> getTransactionsByFromIBAN(String iban, Pageable pageable);
    List<Transaction> getTransactionsByToIBANAndFromIBAN(String toIBAN, String fromIBAN, Pageable pageable);

//    List<Transaction> getTransactionsByTimeStampBetween(OffsetDateTime from, OffsetDateTime to);

    List<Transaction> getTransactionsByAmountLessThan(Double amount, Pageable pageable);

    List<Transaction> getTransactionsByAmountGreaterThan(Double amount, Pageable pageable);

    List<Transaction> getTransactionsByAmountEquals(Double amount, Pageable pageable);

    List<Transaction> getTransactionsByTransactionType(Transaction.TransactionTypeEnum transactionType, Pageable pageable);

//    List<Transaction> getTransactionsByOwner(User user);
//
//    boolean existsByOwnerAndId(User owner, Long id);
}

package io.swagger.api.repository;

import io.swagger.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.time.OffsetDateTime;
import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long>, JpaSpecificationExecutor<Transaction> {
    List<Transaction> getTransactionsByToIBAN(String iban);
    List<Transaction> getTransactionsByFromIBAN(String iban);
    List<Transaction> getTransactionsByToIBANAndFromIBAN(String toIBAN, String fromIBAN);

    List<Transaction> getTransactionsByTimeStampBetween(OffsetDateTime from, OffsetDateTime to);

    List<Transaction> getTransactionsByAmountLessThan(Double amount);

    List<Transaction> getTransactionsByAmountGreaterThan(Double amount);

    List<Transaction> getTransactionsByAmountEquals(Double amount);



//    List<Transaction> getTransactionsByOwner(User user);
//
//    boolean existsByOwnerAndId(User owner, Long id);
}

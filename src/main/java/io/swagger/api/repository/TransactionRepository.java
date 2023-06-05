package io.swagger.api.repository;

import io.swagger.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long>, JpaSpecificationExecutor<Transaction> {
    List<Transaction> getTransactionsByToIBAN(String iban);
    List<Transaction> getTransactionsByFromIBAN(String iban);
    List<Transaction> getTransactionsByToIBANAndFromIBAN(String toIBAN, String fromIBAN);

//    List<Transaction> getTransactionsByOwner(User user);
//
//    boolean existsByOwnerAndId(User owner, Long id);
}

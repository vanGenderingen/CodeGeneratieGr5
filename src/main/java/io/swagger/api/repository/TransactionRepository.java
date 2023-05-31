package io.swagger.api.repository;

import io.swagger.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    List<Transaction> getTransactionsByToIBAN(String iban);
    List<Transaction> getTransactionsByFromIBAN(String iban);

//    List<Transaction> getTransactionsByOwner(User user);
//
//    boolean existsByOwnerAndId(User owner, Long id);
}

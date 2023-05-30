package io.swagger.api.repository;

import io.swagger.model.Transaction;
import io.swagger.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    Transaction getTransactionById(Long id);

    List<Transaction> getTransactionsByOwner(User user);

    boolean existsByOwnerAndId(User owner, Long id);
}

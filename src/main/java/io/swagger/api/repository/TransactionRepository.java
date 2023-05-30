package io.swagger.api.repository;

import io.swagger.model.Transaction;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface TransactionRepository extends CrudRepository<Transaction, Long> {
//    Transaction getTransactionById(Long id);
//
//    List<Transaction> getTransactionsByOwner(User user);
//
//    boolean existsByOwnerAndId(User owner, Long id);
}

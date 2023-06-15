package io.swagger.api.repository;

import io.swagger.api.specification.TransactionSpecification;
import io.swagger.model.Transaction;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long>, JpaSpecificationExecutor<Transaction> {
    List<Transaction> getTransactionByTimeStampContainingAndFromIBANEquals(LocalDateTime date, String fromIBAN);
    List<Transaction>getAllBy(TransactionSpecification specification, Pageable pageable);

}


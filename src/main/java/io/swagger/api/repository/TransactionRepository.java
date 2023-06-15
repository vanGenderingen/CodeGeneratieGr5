package io.swagger.api.repository;

import io.swagger.api.specification.TransactionSpecification;
import io.swagger.model.DTO.Transaction;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long>, JpaSpecificationExecutor<Transaction> {

    List<Transaction>getAllBy(TransactionSpecification specification, Pageable pageable);

}


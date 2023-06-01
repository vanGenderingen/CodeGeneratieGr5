package io.swagger.api.repository;

import io.swagger.model.Account;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface AccountRepository extends JpaRepository<Account, Long> {
    Account getAccountByAccountID(UUID accountID);
    @Query("SELECT a FROM Account a WHERE a.user.userID = :userId AND (:searchStrings IS NULL OR LOWER(a.name) LIKE CONCAT('%', LOWER(:searchStrings), '%'))")
    Page<Account> getAccountsOfUser(
            @Param("userId") UUID userId,
            @Param("searchStrings") String searchStrings,
            Pageable pageable
    );
}
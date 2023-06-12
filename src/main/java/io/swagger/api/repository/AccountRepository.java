package io.swagger.api.repository;

import io.swagger.model.Account;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {
    @Query("SELECT a FROM Account a WHERE (:IBAN IS NULL OR LOWER(a.IBAN) LIKE CONCAT('%', LOWER(:IBAN), '%')) AND (:searchStrings IS NULL OR LOWER(a.name) LIKE CONCAT('%', LOWER(:searchStrings), '%'))")
    List<Account> getAll(String IBAN, String searchStrings, Pageable pageable);

    @Query("SELECT a FROM Account a WHERE a.accountID = :accountID")
    Account getAccountByAccountID(UUID accountID);
    @Query("SELECT a FROM Account a WHERE a.user.userID = :userId AND (:searchStrings IS NULL OR LOWER(a.name) LIKE CONCAT('%', LOWER(:searchStrings), '%'))")
    Page<Account> getAccountsOfUser(
            @Param("userId") UUID userId,
            @Param("searchStrings") String searchStrings,
            Pageable pageable
    );
    @Query("SELECT COUNT(a) FROM Account a WHERE a.userID = :userId AND (:searchStrings IS NULL OR a.name LIKE %:searchStrings%)")
    int countAccountsOfUser(@Param("userId") UUID userId, @Param("searchStrings") String searchStrings);

    Account getAccountByIBAN(String IBAN);
}
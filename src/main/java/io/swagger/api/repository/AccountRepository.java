package io.swagger.api.repository;

import io.swagger.model.Account;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {
    @Query("SELECT a FROM Account a WHERE (:IBAN IS NULL OR LOWER(a.IBAN) LIKE CONCAT('%', LOWER(:IBAN), '%')) AND (:searchStrings IS NULL OR LOWER(a.name) LIKE CONCAT('%', LOWER(:searchStrings), '%'))")
    List<Account> getAll(String IBAN, String searchStrings, Pageable pageable);

    @Query("SELECT a FROM Account a WHERE a.user.userID = :userId AND (:searchStrings IS NULL OR LOWER(a.name) LIKE CONCAT('%', LOWER(:searchStrings), '%'))")
    List<Account> getAccountsOfUser(UUID userId, String searchStrings, Pageable pageable);

    List<Account> getAccountsByUserID(UUID userId);
    Account getAccountByAccountID(UUID accountID);
    Account getAccountByIBAN(String IBAN);
}
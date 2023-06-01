package io.swagger.api.repository;

import io.swagger.model.Account;
import io.swagger.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface AccountRepository extends JpaRepository<Account, Long> {
    Account getAccountByAccountID(UUID accountID);
    @Query("SELECT a FROM Account a WHERE a.user.userID = :userId")
    List<Account> getAccountsOfUser(
            @Param("userId") UUID userId,
            @Param("limit") Integer limit,
            @Param("offset") Integer offset,
            @Param("searchstrings") String searchStrings
    );
}

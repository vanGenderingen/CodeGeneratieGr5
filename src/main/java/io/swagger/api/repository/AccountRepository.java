package io.swagger.api.repository;

import io.swagger.model.Account;
import io.swagger.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface AccountRepository extends JpaRepository<Account, Long> {
    Account getAccountByAccountID(UUID accountID);
}

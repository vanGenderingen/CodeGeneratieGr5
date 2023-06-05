package io.swagger.api.repository;

import io.swagger.model.Account;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountsRepository extends CrudRepository<Account, Long> {
}

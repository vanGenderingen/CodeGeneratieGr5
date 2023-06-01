package io.swagger.repositories;

import io.swagger.model.Account;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountsRepository extends CrudRepository<Account, Long> {
}

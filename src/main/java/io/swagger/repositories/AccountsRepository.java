package io.swagger.repositories;

import io.swagger.model.Account;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountsRepository extends CrudRepository<Account, Long> {
    //NOT DONE MAAR ZICO DEED NAAR
    //@Query("SELECT a FROM Account a WHERE a.someField = :someValue")
    //List<Account> findBySomeCriteria(@Param("someValue") String someValue);
}
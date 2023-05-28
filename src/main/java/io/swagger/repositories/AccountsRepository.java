package io.swagger.repositories;

import io.swagger.model.Account;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AccountsRepository extends CrudRepository<Account, Long> {
    @Query("SELECT a FROM Account a WHERE a.someField LIKE %:searchString%")
    Page<Account> findBySearchString(@Param("searchString") String searchString, Pageable pageable);

    default List<Account> searchAccounts(String searchString, int limit, int offset) {
        Pageable pageable = PageRequest.of(offset, limit);
        Page<Account> accountPage = findBySearchString(searchString, pageable);
        return accountPage.getContent();
    }
}
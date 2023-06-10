/*
package io.swagger.api.repository;

import io.swagger.model.Account;
import io.swagger.model.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.UUID;

@DataJpaTest
public class AccountRepositoryTest {

        @Autowired
        private AccountRepository accountRepository;

        @Test
        public void testGetAll() {
                // Create test data
                Account account1 = new Account();
                account1.setIBAN("IBAN1");
                account1.setName("Account 1");
                accountRepository.save(account1);

                Account account2 = new Account();
                account2.setIBAN("IBAN2");
                account2.setName("Account 2");
                accountRepository.save(account2);

                // Perform the getAll() method invocation
                List<Account> accounts = accountRepository.getAll(null, null, Pageable.unpaged());

                // Verify the results
                Assertions.assertEquals(2, accounts.size());
        }

        @Test
        public void testGetAccountByAccountID() {
                // Create test data
                UUID accountId = UUID.randomUUID();
                Account account = new Account();
                account.setAccountID(accountId);
                account.setIBAN("IBAN");
                account.setName("Test Account");
                accountRepository.save(account);

                // Perform the getAccountByAccountID() method invocation
                Account resultAccount = accountRepository.getAccountByAccountID(accountId);

                // Verify the result
                Assertions.assertNotNull(resultAccount);
                Assertions.assertEquals(accountId, resultAccount.getAccountID());
        }

        @Test
        public void testGetAccountsOfUser() {
                // Create test data
                UUID userId = UUID.randomUUID();
                Account account1 = new Account();
                User user = new User();
                user.setUserID(userId);
                account1.setUser(user);
                account1.setName("User Account 1");
                accountRepository.save(account1);

                UUID userId2 = UUID.randomUUID();
                Account account2 = new Account();
                User user2 = new User();
                user2.setUserID(userId2);
                account1.setUser(user2);
                account2.setName("User Account 2");
                accountRepository.save(account2);

                // Perform the getAccountsOfUser() method invocation
                Pageable pageable = PageRequest.of(0, 10, Sort.unsorted());
                Page<Account> accountPage = accountRepository.getAccountsOfUser(userId, null, pageable);

                // Verify the results
                Assertions.assertEquals(1, accountPage.getTotalElements());
                Assertions.assertEquals(account1.getName(), accountPage.getContent().get(0).getName());
        }

        @Test
        public void testCountAccountsOfUser() {
                // Create test data
                UUID userId = UUID.randomUUID();
                Account account1 = new Account();
                User user = new User();
                user.setUserID(userId);
                account1.setUser(user);
                account1.setName("User Account 1");
                accountRepository.save(account1);

                UUID userId2 = UUID.randomUUID();
                Account account2 = new Account();
                User user2 = new User();
                user2.setUserID(userId2);
                account1.setUser(user2);
                account2.setName("User Account 2");;
                accountRepository.save(account2);

                // Perform the countAccountsOfUser() method invocation
                int count = accountRepository.countAccountsOfUser(userId, null);

                // Verify the result
                Assertions.assertEquals(1, count);
        }

        @Test
        public void testGetAccountByIBAN() {
                // Create test data
                Account account = new Account();
                account.setIBAN("IBAN123");
                account.setName("Test Account");
                accountRepository.save(account);

                // Perform the getAccountByIBAN() method invocation
                Account resultAccount = accountRepository.getAccountByIBAN("IBAN123");

                // Verify the result
                Assertions.assertNotNull(resultAccount);
                Assertions.assertEquals("IBAN123", resultAccount.getIBAN());
        }
}
*/
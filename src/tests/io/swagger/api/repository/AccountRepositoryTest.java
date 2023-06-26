package io.swagger.api.repository;

import io.swagger.model.Account;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.mockito.Mockito.*;

public class AccountRepositoryTest {

        @Test
        public void testGetAll() {
                // Create test data
                Account account1 = new Account();
                account1.setUserID(UUID.randomUUID());

                Account account2 = new Account();
                account2.setUserID(UUID.randomUUID());

                // Create the mock AccountRepository
                AccountRepository accountRepository = mock(AccountRepository.class);

                // Mock the behavior of the accountRepository.getAll() method
                Mockito.when(accountRepository.getAll(null, null, Pageable.unpaged()))
                        .thenReturn(Arrays.asList(account1, account2));

                // Perform the getAll() method invocation
                List<Account> accounts = accountRepository.getAll(null, null, Pageable.unpaged());

                // Verify the results
                Assertions.assertEquals(2, accounts.size());

                // Verify the interactions
                verify(accountRepository, times(1)).getAll(null, null, Pageable.unpaged());
        }

        @Test
        public void testGetAccountByAccountID() {
                // Create and save a test account
                Account account = new Account();
                account.setUserID(UUID.randomUUID());

                // Create the mock AccountRepository
                AccountRepository accountRepository = mock(AccountRepository.class);

                // Mock the behavior of the accountRepository.getAccountByAccountID() method
                Mockito.when(accountRepository.getAccountByAccountID(account.getAccountID()))
                        .thenReturn(account);

                // Perform the query
                Account result = accountRepository.getAccountByAccountID(account.getAccountID());

                // Assert the result
                Assertions.assertEquals(account, result);
        }

        @Test
        public void testGetAccountsOfUser() {
                // Create test data
                UUID userId = UUID.randomUUID();
                Account account1 = new Account();
                account1.setUserID(userId);

                Account account2 = new Account();
                account2.setUserID(userId);

                List<Account> accounts = Arrays.asList(account1, account2);

                // Create the mock AccountRepository
                AccountRepository accountRepository = mock(AccountRepository.class);

                // Mock the behavior of the accountRepository.getAccountsOfUser() method
                Mockito.when(accountRepository.getAccountsOfUser(userId, "John", PageRequest.of(0, 10)))
                        .thenReturn(accounts);

                // Perform the query
                List<Account> result = accountRepository.getAccountsOfUser(userId, "John", PageRequest.of(0, 10));

                // Assert the results
                Assertions.assertEquals(2, result.size());
        }

        @Test
        public void testGetAccountByIBAN() {
                // Create and save a test account
                Account account = new Account();
                account.setUserID(UUID.randomUUID());

                // Create the mock AccountRepository
                AccountRepository accountRepository = mock(AccountRepository.class);

                // Mock the behavior of the accountRepository.getAccountByIBAN() method
                Mockito.when(accountRepository.getAccountByIBAN(account.getIBAN()))
                        .thenReturn(account);

                // Perform the query
                Account result = accountRepository.getAccountByIBAN(account.getIBAN());

                // Assert the result
                Assertions.assertEquals(account, result);
        }
}
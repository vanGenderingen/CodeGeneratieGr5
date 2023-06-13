package io.swagger.api.service;

import io.swagger.api.repository.AccountRepository;
import io.swagger.api.repository.TransactionRepository;
import io.swagger.api.repository.UserRepository;
import io.swagger.model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.server.ResponseStatusException;
import org.threeten.bp.OffsetDateTime;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class TransactionServiceTest {

    @Mock
    private TransactionRepository transactionRepository;

    @Mock
    private AccountRepository accountRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private TransactionService transactionService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void add_validTransaction_successfullyAdded() {
        // Arrange
        User userPerforming = new User();
        userPerforming.setUserID(UUID.fromString("6a54d1d2-b39c-4952-b3a2-af04e9afd76e"));
        userPerforming.setRoles(Arrays.asList(Role.ROLE_USER));
        userPerforming.setTransactionLimit(1000.00);

        Transaction transaction = new Transaction();
        transaction.setFromIBAN("fromIBAN");
        transaction.setToIBAN("toIBAN");
        transaction.setAmount(100.0);
        transaction.setUserPerforming(userPerforming.getUserID());

        Account fromAccount = new Account();
        fromAccount.setUserID(userPerforming.getUserID());
        fromAccount.setBalance(500.0);
        fromAccount.setMinBal(100.0);

        Account toAccount = new Account();
        toAccount.setUserID(userPerforming.getUserID());
        toAccount.setBalance(200.0);
        toAccount.setMinBal(100.0);

        when(accountRepository.getAccountByIBAN("fromIBAN")).thenReturn(fromAccount);
        when(accountRepository.getAccountByIBAN("toIBAN")).thenReturn(toAccount);
        when(userRepository.getUserByUserID(UUID.fromString("6a54d1d2-b39c-4952-b3a2-af04e9afd76e"))).thenReturn(userPerforming);
        when(transactionRepository.save(any(Transaction.class))).thenReturn(transaction);

        // Act
        Transaction addedTransaction = transactionService.add(transaction);

        // Assert
        assertNotNull(addedTransaction);
        assertEquals(transaction, addedTransaction);
        assertEquals(400.00, fromAccount.getBalance(), 0.001);
        assertEquals(300.00, toAccount.getBalance(), 0.001);
        verify(accountRepository, times(2)).save(any(Account.class));
        verify(transactionRepository).save(transaction);
    }

    @Test
    void add_invalidFromIBAN_throwsBadRequestException() {
        // Arrange
        Transaction transaction = new Transaction();
        transaction.setFromIBAN("invalidIBAN");
        transaction.setToIBAN("toIBAN");
        transaction.setAmount(100.0);
        transaction.setUserPerforming(UUID.randomUUID());

        when(accountRepository.getAccountByIBAN("invalidIBAN")).thenReturn(null);

        // Act & Assert
        assertThrows(ResponseStatusException.class, () -> transactionService.add(transaction));
        verify(accountRepository, never()).save(any(Account.class));
        verify(transactionRepository, never()).save(any(Transaction.class));
    }

    @Test
    void add_invalidToIBAN_throwsBadRequestException() {
        // Arrange
        Transaction transaction = new Transaction();
        transaction.setFromIBAN("fromIBAN");
        transaction.setToIBAN("invalidIBAN");
        transaction.setAmount(100.0);
        transaction.setUserPerforming(UUID.randomUUID());

        Account fromAccount = new Account();
        fromAccount.setUserID(UUID.randomUUID());

        when(accountRepository.getAccountByIBAN("fromIBAN")).thenReturn(fromAccount);
        when(accountRepository.getAccountByIBAN("invalidIBAN")).thenReturn(null);

        // Act & Assert
        assertThrows(ResponseStatusException.class, () -> transactionService.add(transaction));
        verify(accountRepository, never()).save(any(Account.class));
        verify(transactionRepository, never()).save(any(Transaction.class));
    }

    @Test
    void add_invalidUserPerforming_throwsBadRequestException() {
        // Arrange
        Transaction transaction = new Transaction();
        transaction.setFromIBAN("fromIBAN");
        transaction.setToIBAN("toIBAN");
        transaction.setAmount(100.0);
        transaction.setUserPerforming(UUID.fromString("6a54d1d2-b39c-4952-b3a2-af04e9afd76e"));

        Account fromAccount = new Account();
        fromAccount.setUserID(UUID.randomUUID());

        Account toAccount = new Account();
        toAccount.setUserID(UUID.randomUUID());

        when(accountRepository.getAccountByIBAN("fromIBAN")).thenReturn(fromAccount);
        when(accountRepository.getAccountByIBAN("toIBAN")).thenReturn(toAccount);
        when(userRepository.getUserByUserID(UUID.fromString("6a54d1d2-b39c-4952-b3a2-af04e9afd76e"))).thenReturn(null);

        // Act & Assert
        assertThrows(ResponseStatusException.class, () -> transactionService.add(transaction));
        verify(accountRepository, never()).save(any(Account.class));
        verify(transactionRepository, never()).save(any(Transaction.class));
    }

    @Test
    void add_notEnoughBalance_throwsBadRequestException() {
        // Arrange
        Transaction transaction = new Transaction();
        transaction.setFromIBAN("fromIBAN");
        transaction.setToIBAN("toIBAN");
        transaction.setAmount(1000.0);
        transaction.setUserPerforming(UUID.randomUUID());

        Account fromAccount = new Account();
        fromAccount.setUserID(UUID.randomUUID());
        fromAccount.setBalance(500.0);
        fromAccount.setMinBal(100.0);

        Account toAccount = new Account();
        toAccount.setUserID(UUID.randomUUID());

        User userPerforming = new User();
        userPerforming.setUserID(UUID.randomUUID());
        userPerforming.setRoles(Arrays.asList(Role.ROLE_USER));

        when(accountRepository.getAccountByIBAN("fromIBAN")).thenReturn(fromAccount);
        when(accountRepository.getAccountByIBAN("toIBAN")).thenReturn(toAccount);
        when(userRepository.getUserByUserID(UUID.randomUUID())).thenReturn(userPerforming);

        // Act & Assert
        assertThrows(ResponseStatusException.class, () -> transactionService.add(transaction));
        verify(accountRepository, never()).save(any(Account.class));
        verify(transactionRepository, never()).save(any(Transaction.class));
    }

    @Test
    void add_exceedsTransactionLimit_throwsBadRequestException() {
        // Arrange
        Transaction transaction = new Transaction();
        transaction.setFromIBAN("fromIBAN");
        transaction.setToIBAN("toIBAN");
        transaction.setAmount(500.0);
        transaction.setUserPerforming(UUID.randomUUID());

        Account fromAccount = new Account();
        fromAccount.setUserID(UUID.randomUUID());
        fromAccount.setBalance(1000.0);
        fromAccount.setMinBal(100.0);

        Account toAccount = new Account();
        toAccount.setUserID(UUID.randomUUID());

        User userPerforming = new User();
        userPerforming.setUserID(UUID.randomUUID());
        userPerforming.setTransactionLimit(200.0);
        userPerforming.setRoles(Arrays.asList(Role.ROLE_USER));

        when(accountRepository.getAccountByIBAN("fromIBAN")).thenReturn(fromAccount);
        when(accountRepository.getAccountByIBAN("toIBAN")).thenReturn(toAccount);
        when(userRepository.getUserByUserID(UUID.randomUUID())).thenReturn(userPerforming);

        // Act & Assert
        assertThrows(ResponseStatusException.class, () -> transactionService.add(transaction));
        verify(accountRepository, never()).save(any(Account.class));
        verify(transactionRepository, never()).save(any(Transaction.class));
    }

    @Test
    void add_insufficientBalanceForFromAccount_throwsBadRequestException() {
        // Arrange
        Transaction transaction = new Transaction();
        transaction.setFromIBAN("fromIBAN");
        transaction.setToIBAN("toIBAN");
        transaction.setAmount(200.0);
        transaction.setUserPerforming(UUID.fromString("6a54d1d2-b39c-4952-b3a2-af04e9afd76e"));

        Account fromAccount = new Account();
        fromAccount.setUserID(UUID.fromString("6a54d1d2-b39c-4952-b3a2-af04e9afd76e"));
        fromAccount.setBalance(100.0);
        fromAccount.setMinBal(100.0);
        fromAccount.setType(Account.TypeEnum.SAVINGS);

        Account toAccount = new Account();
        toAccount.setUserID(UUID.fromString("6a54d1d2-b39c-4952-b3a2-af04e9afd76e"));
        toAccount.setBalance(100.00);
        toAccount.setType(Account.TypeEnum.SAVINGS);

        User userPerforming = new User();
        userPerforming.setUserID(UUID.fromString("6a54d1d2-b39c-4952-b3a2-af04e9afd76e"));
        userPerforming.setRoles(Arrays.asList(Role.ROLE_USER));
        userPerforming.setTransactionLimit(1000.0);

        when(accountRepository.getAccountByIBAN("fromIBAN")).thenReturn(fromAccount);
        when(accountRepository.getAccountByIBAN("toIBAN")).thenReturn(toAccount);
        when(userRepository.getUserByUserID(UUID.fromString("6a54d1d2-b39c-4952-b3a2-af04e9afd76e"))).thenReturn(userPerforming);

        // Act & Assert
        assertThrows(ResponseStatusException.class, () -> transactionService.add(transaction));
        verify(accountRepository, never()).save(any(Account.class));
        verify(transactionRepository, never()).save(any(Transaction.class));
    }

    @Test
    void getTransactions_filterByFromIBAN_returnsFilteredTransactions() {
        // Arrange
        String fromIBAN = "fromIBAN";
        List<Transaction> expectedTransactions = new ArrayList<>();
        expectedTransactions.add(new Transaction());

        when(transactionRepository.getTransactionsByFromIBAN(eq(fromIBAN), any())).thenReturn(expectedTransactions);

        IBANFilter filter = new IBANFilter();
        filter.setFromIBAN(fromIBAN);
        // Act
        List<Transaction> actualTransactions = transactionService.getTransactions(0, 10, filter, new AmountFilter(), null);

        // Assert
        assertNotNull(actualTransactions);
        assertEquals(expectedTransactions.size(), actualTransactions.size());
        assertEquals(expectedTransactions, actualTransactions);
        verify(transactionRepository).getTransactionsByFromIBAN(eq(fromIBAN), any());
    }

    @Test
    void add_validTransaction_updatesAccountBalances() {
        // Arrange
        Transaction transaction = new Transaction();
        transaction.setFromIBAN("fromIBAN");
        transaction.setToIBAN("toIBAN");
        transaction.setAmount(100.0);
        transaction.setUserPerforming(UUID.fromString("6a54d1d2-b39c-4952-b3a2-af04e9afd76e"));
        transaction.setTimeStamp(OffsetDateTime.now());
        transaction.setTransactionType(Transaction.TransactionTypeEnum.DEPOSIT);
        transaction.setTransactionID(UUID.randomUUID());

        Account fromAccount = new Account();
        fromAccount.setUserID(UUID.fromString("6a54d1d2-b39c-4952-b3a2-af04e9afd76e"));
        fromAccount.setBalance(500.0);
        fromAccount.setMinBal(100.0);
        fromAccount.setIBAN("fromIBAN");

        Account toAccount = new Account();
        toAccount.setUserID(UUID.fromString("23989d7d-1160-411e-b453-318c2bd878ea"));
        toAccount.setBalance(200.0);
        toAccount.setIBAN("toIBAN");

        User userPerforming = new User();
        userPerforming.setUserID(UUID.fromString("6a54d1d2-b39c-4952-b3a2-af04e9afd76e"));
        userPerforming.setRoles(Arrays.asList(Role.ROLE_USER));
        userPerforming.setTransactionLimit(1000.0);

        when(accountRepository.getAccountByIBAN("fromIBAN")).thenReturn(fromAccount);
        when(accountRepository.getAccountByIBAN("toIBAN")).thenReturn(toAccount);
        when(userRepository.getUserByUserID(UUID.fromString("6a54d1d2-b39c-4952-b3a2-af04e9afd76e"))).thenReturn(userPerforming);

        transactionService.add(transaction);

        // Assert
        assertEquals(400.0, fromAccount.getBalance(), 0.001);
        assertEquals(300.0, toAccount.getBalance(), 0.001);
        verify(accountRepository).save(fromAccount);
        verify(accountRepository).save(toAccount);
        verify(transactionRepository).save(transaction);
    }


}
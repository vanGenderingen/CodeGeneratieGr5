package io.swagger.api.controllers;

import io.swagger.api.service.TransactionService;
import io.swagger.model.Role;
import io.swagger.model.Transaction;
import io.swagger.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

class TransactionsApiControllerTest {

    @Mock
    private TransactionService transactionService;

    @InjectMocks
    private TransactionsApiController transactionsApiController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

//    User user = new User();
//    user.setUserID(UUID.randomUUID());
//    user.setUsername("testuser");
//    user.setPassword("testpassword");

    @Test
    void testCreateTransaction() {
        User user = new User();
        user.setUserID(UUID.randomUUID());
        user.setEmail("bank@mail.nl");
        user.setPassword("testpassword");
        user.setRoles(Arrays.asList(Role.ROLE_USER, Role.ROLE_EMPLOYEE));

//        // Arrange
//        CreateTransactionDTO createTransactionDTO = new CreateTransactionDTO();
//        createTransactionDTO.setAmount(BigDecimal.valueOf(100.00));
//        createTransactionDTO.setFromAccountId(UUID.randomUUID());
//        createTransactionDTO.setToAccountId(UUID.randomUUID());
//
//        Transaction transaction = new Transaction();
//        transaction.setId(UUID.randomUUID());
//        transaction.setAmount(BigDecimal.valueOf(100.00));
//        transaction.setFromAccountId(UUID.randomUUID());
//        transaction.setToAccountId(UUID.randomUUID()));
//
//        when(transactionService.createTransaction(createTransactionDTO)).thenReturn(transaction);
//
//        // Act
//        ResponseEntity<Transaction> response = transactionsApiController.createTransaction(createTransactionDTO);
//
//        // Assert
//        assertEquals(HttpStatus.CREATED, response.getStatusCode());
//        assertEquals(transaction, response.getBody());
    }

    @Test
    void testGetTransactions() {
        // Arrange
        List<Transaction> transactions = new ArrayList<>();
        Transaction transaction1 = new Transaction();
        transaction1.setTransactionID(UUID.randomUUID());
        transaction1.setFromIBAN("NL01INHO0000000001");
        transaction1.setToIBAN("NL01INHO0000000002");
        transaction1.setAmount(100.00);
        transaction1.setDescription("Test transaction 1");
        transaction1.setTransactionType(Transaction.TransactionTypeEnum.DEPOSIT);
        transaction1.setUserPerforming(UUID.randomUUID());
        transactions.add(transaction1);

//        Transaction transaction2 = new Transaction();
//        transaction2.setId(UUID.randomUUID());
//        transaction2.setAmount(BigDecimal.valueOf(200));
//        transaction2.setFromAccountId(UUID.randomUUID());
//        transaction2.setToAccountId(UUID.randomUUID()));
//        transactions.add(transaction2);

//        when(transactionService.getTransactions()).thenReturn(transactions);
//
//        // Act
//        ResponseEntity<List<Transaction>> response = transactionsApiController.getTransactions();

        // Assert
//        assertEquals(HttpStatus.OK, response.getStatusCode());
//        assertEquals(transactions, response.getBody());
    }
}
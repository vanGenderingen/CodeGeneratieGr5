package io.swagger.api.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.api.service.TransactionService;
import io.swagger.model.AmountFilter;
import io.swagger.model.DTO.CreateTransactionDTO;
import io.swagger.model.IBANFilter;
import io.swagger.model.Transaction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.threeten.bp.OffsetDateTime;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TransactionsApiControllerTest {

    @Mock
    private TransactionService transactionService;

    @Mock
    private HttpServletRequest request;

    @InjectMocks
    private TransactionsApiController transactionsApiController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        transactionsApiController = new TransactionsApiController(new ObjectMapper(), request, transactionService);
    }

    @Test
    void testPostTransactions() {
        // Arrange
        CreateTransactionDTO createTransactionDTO = new CreateTransactionDTO("NL01INHO0000000001", "NL01INHO0000000002", 100.00, CreateTransactionDTO.TransactionTypeEnum.DEPOSIT, "Test");
        Transaction transaction = new Transaction(UUID.randomUUID(), "NL01INHO0000000001", "NL01INHO0000000002", 100.00, Transaction.TransactionTypeEnum.DEPOSIT, UUID.fromString("6a54d1d2-b39c-4952-b3a2-af04e9afd76e"), OffsetDateTime.now() ,"Test");
        Principal principal = mock(Principal.class);
        when(request.getUserPrincipal()).thenReturn(principal);
        when(principal.getName()).thenReturn("6a54d1d2-b39c-4952-b3a2-af04e9afd76e");

        when(transactionService.add(any(Transaction.class))).thenReturn(transaction);

        // Act
        ResponseEntity<Transaction> response = transactionsApiController.postTransactions(createTransactionDTO);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(transaction, response.getBody());
        verify(transactionService, times(1)).add(any(Transaction.class));
    }


    @Test
    void testGetTransactions() {
        // Arrange
        Integer offset = 0;
        Integer limit = 20;
        String transactionType = "withdraw";
        IBANFilter accountFilter = new IBANFilter();
        AmountFilter amountFilter = new AmountFilter();

        List<Transaction> transactions = new ArrayList<>();
        when(transactionService.getTransactions(offset, limit, accountFilter, amountFilter, transactionType)).thenReturn(transactions);

        // Act
        ResponseEntity<List<Transaction>> response = transactionsApiController.getTransactions(offset, limit, transactionType, accountFilter, amountFilter);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(transactions, response.getBody());
        verify(transactionService, times(1)).getTransactions(offset, limit, accountFilter, amountFilter, transactionType);
    }

    @Test
    void testPostTransactions_InternalServerError() {
        CreateTransactionDTO createTransactionDTO = new CreateTransactionDTO();
        Transaction transaction = new Transaction();
        Principal principal = mock(Principal.class);
        when(request.getUserPrincipal()).thenReturn(principal);
        when(principal.getName()).thenReturn("6a54d1d2-b39c-4952-b3a2-af04e9afd76e");

        when(transactionService.add(any(Transaction.class))).thenThrow(new RuntimeException("Internal Server Error"));
        ResponseEntity<Transaction> response = transactionsApiController.postTransactions(createTransactionDTO);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }
    @Test
    void testGetTransactions_InternalServerError() {
        Integer offset = 0;
        Integer limit = 20;
        IBANFilter accountFilter = new IBANFilter();
        AmountFilter amountFilter = new AmountFilter();
        String transactionType = null;

        // Mock the transactionService to throw an exception
        when(transactionService.getTransactions(offset, limit, accountFilter, amountFilter, transactionType))
                .thenThrow(new RuntimeException("Internal Server Error"));

        ResponseEntity<List<Transaction>> response = transactionsApiController.getTransactions(
                offset,
                limit,
                transactionType,
                accountFilter,
                amountFilter
        );
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }
}

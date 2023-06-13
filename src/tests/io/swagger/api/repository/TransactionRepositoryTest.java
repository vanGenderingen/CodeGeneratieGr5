package io.swagger.api.repository;

import io.swagger.model.transactions.Transaction;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.mockito.Mock;
import org.springframework.data.domain.Pageable;
import org.threeten.bp.OffsetDateTime;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.mockito.Mockito.*;
public class TransactionRepositoryTest {

    @Mock
    private TransactionRepository transactionRepository;

    @Test
    public void testGetTransactionsByAmountGreaterThan() {
        transactionRepository = mock(TransactionRepository.class);
        // Mock data
        List<Transaction> transactions = new ArrayList<>();
        transactions.add(new Transaction(UUID.randomUUID(), "NL01INHO0000000001", "NL01INHO0000000002", 100.0, Transaction.TransactionTypeEnum.DEPOSIT, UUID.randomUUID(), OffsetDateTime.now(),"Test transaction 1"));
        transactions.add(new Transaction(UUID.randomUUID(), "NL01INHO0000000002", "NL01INHO0000000003", 50.0, Transaction.TransactionTypeEnum.DEPOSIT, UUID.randomUUID(), OffsetDateTime.now(),"Test transaction 2"));

        // Mock the repository method
        when(transactionRepository.getTransactionsByAmountGreaterThan(20.00, Pageable.unpaged()))
                .thenReturn(transactions);

        List<Transaction> actualTransactions = transactionRepository.getTransactionsByAmountGreaterThan(20.00, Pageable.unpaged());

        // Verify the result
        Assertions.assertEquals(transactions, actualTransactions);

        // Verify that the repository method was called once
        verify(transactionRepository, times(1)).getTransactionsByAmountGreaterThan(20.00, Pageable.unpaged());
    }

    @Test
    public void testGetTransactionsByAmountEquals() {
        transactionRepository = mock(TransactionRepository.class);
        // Mock data
        List<Transaction> transactions = new ArrayList<>();
        transactions.add(new Transaction(UUID.randomUUID(), "NL01INHO0000000001", "NL01INHO0000000002", 100.0, Transaction.TransactionTypeEnum.DEPOSIT, UUID.randomUUID(), OffsetDateTime.now(),"Test transaction 1"));
        transactions.add(new Transaction(UUID.randomUUID(), "NL01INHO0000000002", "NL01INHO0000000003", 100.0, Transaction.TransactionTypeEnum.DEPOSIT, UUID.randomUUID(), OffsetDateTime.now(),"Test transaction 2"));

        // Mock the repository method
        when(transactionRepository.getTransactionsByAmountEquals(100.00, Pageable.unpaged()))
                .thenReturn(transactions);

        List<Transaction> actualTransactions = transactionRepository.getTransactionsByAmountEquals(100.00, Pageable.unpaged());

        // Verify the result
        Assertions.assertEquals(transactions, actualTransactions);

        // Verify that the repository method was called once
        verify(transactionRepository, times(1)).getTransactionsByAmountEquals(100.00, Pageable.unpaged());
    }

    @Test
    public void testGetTransactionsByFromIBANAndAmountGreaterThan() {
        transactionRepository = mock(TransactionRepository.class);
        // Mock data
        List<Transaction> transactions = new ArrayList<>();
        transactions.add(new Transaction(UUID.randomUUID(), "NL01INHO0000000002", "NL01INHO0000000004", 100.0, Transaction.TransactionTypeEnum.DEPOSIT, UUID.randomUUID(), OffsetDateTime.now(),"Test transaction 1"));
        transactions.add(new Transaction(UUID.randomUUID(), "NL01INHO0000000002", "NL01INHO0000000003", 100.0, Transaction.TransactionTypeEnum.DEPOSIT, UUID.randomUUID(), OffsetDateTime.now(),"Test transaction 2"));

        // Mock the repository method
        when(transactionRepository.getTransactionsByFromIBANAndAmountGreaterThan("NL01INHO0000000002", 20.00, Pageable.unpaged()))
                .thenReturn(transactions);

        List<Transaction> actualTransactions = transactionRepository.getTransactionsByFromIBANAndAmountGreaterThan("NL01INHO0000000002", 20.00, Pageable.unpaged());

        // Verify the result
        Assertions.assertEquals(transactions, actualTransactions);

        // Verify that the repository method was called once
        verify(transactionRepository, times(1)).getTransactionsByFromIBANAndAmountGreaterThan("NL01INHO0000000002", 20.00, Pageable.unpaged());
    }

    @Test
    public void testGetTransactionsByFromIBANAndAmountLessThan() {
        transactionRepository = mock(TransactionRepository.class);
        // Mock data
        List<Transaction> transactions = new ArrayList<>();
        transactions.add(new Transaction(UUID.randomUUID(), "NL01INHO0000000002", "NL01INHO0000000004", 100.0, Transaction.TransactionTypeEnum.DEPOSIT, UUID.randomUUID(), OffsetDateTime.now(),"Test transaction 1"));
        transactions.add(new Transaction(UUID.randomUUID(), "NL01INHO0000000002", "NL01INHO0000000003", 100.0, Transaction.TransactionTypeEnum.DEPOSIT, UUID.randomUUID(), OffsetDateTime.now(),"Test transaction 2"));

        // Mock the repository method
        when(transactionRepository.getTransactionsByFromIBANAndAmountLessThan("NL01INHO0000000002", 120.00, Pageable.unpaged()))
                .thenReturn(transactions);

        List<Transaction> actualTransactions = transactionRepository.getTransactionsByFromIBANAndAmountLessThan("NL01INHO0000000002", 120.00, Pageable.unpaged());

        // Verify the result
        Assertions.assertEquals(transactions, actualTransactions);

        // Verify that the repository method was called once
        verify(transactionRepository, times(1)).getTransactionsByFromIBANAndAmountLessThan("NL01INHO0000000002", 120.00, Pageable.unpaged());

    }
    @Test
    public void testGetTransactionsByFromIBANAndAmountEquals() {
        transactionRepository = mock(TransactionRepository.class);
        // Mock data
        List<Transaction> transactions = new ArrayList<>();
        transactions.add(new Transaction(UUID.randomUUID(), "NL01INHO0000000002", "NL01INHO0000000004", 100.0, Transaction.TransactionTypeEnum.DEPOSIT, UUID.randomUUID(), OffsetDateTime.now(),"Test transaction 1"));
        transactions.add(new Transaction(UUID.randomUUID(), "NL01INHO0000000002", "NL01INHO0000000003", 100.0, Transaction.TransactionTypeEnum.DEPOSIT, UUID.randomUUID(), OffsetDateTime.now(),"Test transaction 2"));

        // Mock the repository method
        when(transactionRepository.getTransactionsByFromIBANAndAmountEquals("NL01INHO0000000002", 100.00, Pageable.unpaged()))
                .thenReturn(transactions);

        List<Transaction> actualTransactions = transactionRepository.getTransactionsByFromIBANAndAmountEquals("NL01INHO0000000002", 100.00, Pageable.unpaged());

        // Verify the result
        Assertions.assertEquals(transactions, actualTransactions);

        // Verify that the repository method was called once
        verify(transactionRepository, times(1)).getTransactionsByFromIBANAndAmountEquals("NL01INHO0000000002", 100.00, Pageable.unpaged());

    }
    @Test
    public void testGetTransactionsByToIBANAndAmountLessThan() {
        transactionRepository = mock(TransactionRepository.class);
        // Mock data
        List<Transaction> transactions = new ArrayList<>();
        transactions.add(new Transaction(UUID.randomUUID(), "NL01INHO0000000002", "NL01INHO0000000003", 100.0, Transaction.TransactionTypeEnum.DEPOSIT, UUID.randomUUID(), OffsetDateTime.now(),"Test transaction 1"));
        transactions.add(new Transaction(UUID.randomUUID(), "NL01INHO0000000002", "NL01INHO0000000003", 100.0, Transaction.TransactionTypeEnum.DEPOSIT, UUID.randomUUID(), OffsetDateTime.now(),"Test transaction 2"));

        // Mock the repository method
        when(transactionRepository.getTransactionsByToIBANAndAmountLessThan("NL01INHO0000000003", 120.00, Pageable.unpaged()))
                .thenReturn(transactions);

        List<Transaction> actualTransactions = transactionRepository.getTransactionsByToIBANAndAmountLessThan("NL01INHO0000000003", 120.00, Pageable.unpaged());

        // Verify the result
        Assertions.assertEquals(transactions, actualTransactions);

        // Verify that the repository method was called once
        verify(transactionRepository, times(1)).getTransactionsByToIBANAndAmountLessThan("NL01INHO0000000003", 120.00, Pageable.unpaged());

    }
    @Test
    public void testGetTransactionsByToIBANAndAmountEquals() {
        transactionRepository = mock(TransactionRepository.class);
        // Mock data
        List<Transaction> transactions = new ArrayList<>();
        transactions.add(new Transaction(UUID.randomUUID(), "NL01INHO0000000002", "NL01INHO0000000003", 100.0, Transaction.TransactionTypeEnum.DEPOSIT, UUID.randomUUID(), OffsetDateTime.now(),"Test transaction 1"));
        transactions.add(new Transaction(UUID.randomUUID(), "NL01INHO0000000002", "NL01INHO0000000003", 100.0, Transaction.TransactionTypeEnum.DEPOSIT, UUID.randomUUID(), OffsetDateTime.now(),"Test transaction 2"));

        // Mock the repository method
        when(transactionRepository.getTransactionsByToIBANAndAmountEquals("NL01INHO0000000003", 100.00, Pageable.unpaged()))
                .thenReturn(transactions);

        List<Transaction> actualTransactions = transactionRepository.getTransactionsByToIBANAndAmountEquals("NL01INHO0000000003", 100.00, Pageable.unpaged());

        // Verify the result
        Assertions.assertEquals(transactions, actualTransactions);

        // Verify that the repository method was called once
        verify(transactionRepository, times(1)).getTransactionsByToIBANAndAmountEquals("NL01INHO0000000003", 100.00, Pageable.unpaged());

    }
    @Test
    public void testTestGetTransactionsByToIBAN() {
        transactionRepository = mock(TransactionRepository.class);
        // Mock data
        List<Transaction> transactions = new ArrayList<>();
        transactions.add(new Transaction(UUID.randomUUID(), "NL01INHO0000000002", "NL01INHO0000000003", 100.0, Transaction.TransactionTypeEnum.DEPOSIT, UUID.randomUUID(), OffsetDateTime.now(),"Test transaction 1"));
        transactions.add(new Transaction(UUID.randomUUID(), "NL01INHO0000000002", "NL01INHO0000000003", 100.0, Transaction.TransactionTypeEnum.DEPOSIT, UUID.randomUUID(), OffsetDateTime.now(),"Test transaction 2"));

        // Mock the repository method
        when(transactionRepository.getTransactionsByToIBANAndAmountEquals("NL01INHO0000000003", 100.00, Pageable.unpaged()))
                .thenReturn(transactions);

        List<Transaction> actualTransactions = transactionRepository.getTransactionsByToIBANAndAmountEquals("NL01INHO0000000003", 100.00, Pageable.unpaged());

        // Verify the result
        Assertions.assertEquals(transactions, actualTransactions);

        // Verify that the repository method was called once
        verify(transactionRepository, times(1)).getTransactionsByToIBANAndAmountEquals("NL01INHO0000000003", 100.00, Pageable.unpaged());

    }
    @Test
    public void testGetTransactionsByToIBANAndFromIBAN() {
        transactionRepository = mock(TransactionRepository.class);
        // Mock data
        List<Transaction> transactions = new ArrayList<>();
        transactions.add(new Transaction(UUID.randomUUID(), "NL01INHO0000000002", "NL01INHO0000000003", 100.0, Transaction.TransactionTypeEnum.DEPOSIT, UUID.randomUUID(), OffsetDateTime.now(),"Test transaction 1"));
        transactions.add(new Transaction(UUID.randomUUID(), "NL01INHO0000000002", "NL01INHO0000000003", 100.0, Transaction.TransactionTypeEnum.DEPOSIT, UUID.randomUUID(), OffsetDateTime.now(),"Test transaction 2"));

        // Mock the repository method
        when(transactionRepository.getTransactionsByToIBANAndFromIBAN("NL01INHO0000000003", "NL01INHO0000000002", Pageable.unpaged()))
                .thenReturn(transactions);

        List<Transaction> actualTransactions = transactionRepository.getTransactionsByToIBANAndFromIBAN("NL01INHO0000000003", "NL01INHO0000000002", Pageable.unpaged());

        // Verify the result
        Assertions.assertEquals(transactions, actualTransactions);

        // Verify that the repository method was called once
        verify(transactionRepository, times(1)).getTransactionsByToIBANAndFromIBAN("NL01INHO0000000003", "NL01INHO0000000002", Pageable.unpaged());

    }
    @Test
    public void testGetTransactionsByToIBANAndAmountGreaterThan() {
        transactionRepository = mock(TransactionRepository.class);
        // Mock data
        List<Transaction> transactions = new ArrayList<>();
        transactions.add(new Transaction(UUID.randomUUID(), "NL01INHO0000000002", "NL01INHO0000000003", 100.0, Transaction.TransactionTypeEnum.DEPOSIT, UUID.randomUUID(), OffsetDateTime.now(),"Test transaction 1"));
        transactions.add(new Transaction(UUID.randomUUID(), "NL01INHO0000000002", "NL01INHO0000000003", 100.0, Transaction.TransactionTypeEnum.DEPOSIT, UUID.randomUUID(), OffsetDateTime.now(),"Test transaction 2"));

        // Mock the repository method
        when(transactionRepository.getTransactionsByToIBANAndAmountGreaterThan("NL01INHO0000000003", 1.00, Pageable.unpaged()))
                .thenReturn(transactions);

        List<Transaction> actualTransactions = transactionRepository.getTransactionsByToIBANAndAmountGreaterThan("NL01INHO0000000003", 1.00, Pageable.unpaged());

        // Verify the result
        Assertions.assertEquals(transactions, actualTransactions);

        // Verify that the repository method was called once
        verify(transactionRepository, times(1)).getTransactionsByToIBANAndAmountGreaterThan("NL01INHO0000000003", 1.00, Pageable.unpaged());

    }

    @Test
    public void testGetTransactionsByToIBANAndFromIBANAndAmountGreaterThan() {
        transactionRepository = mock(TransactionRepository.class);
        // Mock data
        List<Transaction> transactions = new ArrayList<>();
        transactions.add(new Transaction(UUID.randomUUID(), "NL01INHO0000000002", "NL01INHO0000000003", 100.0, Transaction.TransactionTypeEnum.DEPOSIT, UUID.randomUUID(), OffsetDateTime.now(),"Test transaction 1"));
        transactions.add(new Transaction(UUID.randomUUID(), "NL01INHO0000000002", "NL01INHO0000000003", 100.0, Transaction.TransactionTypeEnum.DEPOSIT, UUID.randomUUID(), OffsetDateTime.now(),"Test transaction 2"));

        // Mock the repository method
        when(transactionRepository.getTransactionsByToIBANAndFromIBANAndAmountGreaterThan("NL01INHO0000000003", "NL01INHO0000000002",  1.00, Pageable.unpaged()))
                .thenReturn(transactions);

        List<Transaction> actualTransactions = transactionRepository.getTransactionsByToIBANAndFromIBANAndAmountGreaterThan("NL01INHO0000000003", "NL01INHO0000000002",  1.00, Pageable.unpaged());

        // Verify the result
        Assertions.assertEquals(transactions, actualTransactions);

        // Verify that the repository method was called once
        verify(transactionRepository, times(1)).getTransactionsByToIBANAndFromIBANAndAmountGreaterThan("NL01INHO0000000003", "NL01INHO0000000002",  1.00, Pageable.unpaged());

    }
    @Test
    public void testGetTransactionsByToIBANAndFromIBANAndAmountLessThan() {
        transactionRepository = mock(TransactionRepository.class);
        // Mock data
        List<Transaction> transactions = new ArrayList<>();
        transactions.add(new Transaction(UUID.randomUUID(), "NL01INHO0000000002", "NL01INHO0000000003", 100.0, Transaction.TransactionTypeEnum.DEPOSIT, UUID.randomUUID(), OffsetDateTime.now(),"Test transaction 1"));
        transactions.add(new Transaction(UUID.randomUUID(), "NL01INHO0000000002", "NL01INHO0000000003", 100.0, Transaction.TransactionTypeEnum.DEPOSIT, UUID.randomUUID(), OffsetDateTime.now(),"Test transaction 2"));

        // Mock the repository method
        when(transactionRepository.getTransactionsByToIBANAndFromIBANAndAmountLessThan("NL01INHO0000000003", "NL01INHO0000000002",  120.00, Pageable.unpaged()))
                .thenReturn(transactions);

        List<Transaction> actualTransactions = transactionRepository.getTransactionsByToIBANAndFromIBANAndAmountLessThan("NL01INHO0000000003", "NL01INHO0000000002",  120.00, Pageable.unpaged());

        // Verify the result
        Assertions.assertEquals(transactions, actualTransactions);

        // Verify that the repository method was called once
        verify(transactionRepository, times(1)).getTransactionsByToIBANAndFromIBANAndAmountLessThan("NL01INHO0000000003", "NL01INHO0000000002",  120.00, Pageable.unpaged());

    }
    @Test
    public void testGetTransactionsByToIBANAndFromIBANAndAmountEquals() {
        transactionRepository = mock(TransactionRepository.class);
        // Mock data
        List<Transaction> transactions = new ArrayList<>();
        transactions.add(new Transaction(UUID.randomUUID(), "NL01INHO0000000002", "NL01INHO0000000003", 100.0, Transaction.TransactionTypeEnum.DEPOSIT, UUID.randomUUID(), OffsetDateTime.now(),"Test transaction 1"));
        transactions.add(new Transaction(UUID.randomUUID(), "NL01INHO0000000002", "NL01INHO0000000003", 100.0, Transaction.TransactionTypeEnum.DEPOSIT, UUID.randomUUID(), OffsetDateTime.now(),"Test transaction 2"));

        // Mock the repository method
        when(transactionRepository.getTransactionsByToIBANAndFromIBANAndAmountEquals("NL01INHO0000000003", "NL01INHO0000000002",  100.00, Pageable.unpaged()))
                .thenReturn(transactions);

        List<Transaction> actualTransactions = transactionRepository.getTransactionsByToIBANAndFromIBANAndAmountEquals("NL01INHO0000000003", "NL01INHO0000000002",  100.00, Pageable.unpaged());

        // Verify the result
        Assertions.assertEquals(transactions, actualTransactions);

        // Verify that the repository method was called once
        verify(transactionRepository, times(1)).getTransactionsByToIBANAndFromIBANAndAmountEquals("NL01INHO0000000003", "NL01INHO0000000002",  100.00, Pageable.unpaged());

    }
}
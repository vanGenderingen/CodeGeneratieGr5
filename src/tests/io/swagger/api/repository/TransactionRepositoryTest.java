package io.swagger.api.repository;

import io.swagger.model.Account;
import io.swagger.model.Role;
import io.swagger.model.Transaction;
import io.swagger.model.User;
import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;
import org.threeten.bp.OffsetDateTime;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
public class TransactionRepositoryTest extends TestCase {

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AccountRepository accountRepository;

    private List<Transaction> transactions;

    private List<User> users;

    private List<Account> accounts;

    @Before
    public void setup() {
        users = new ArrayList<>();
        // Initialize the users
        users.add(new User(UUID.fromString("03259d6f-4c93-40f0-b18d-be04f42537fc"), "bank", "bank", "debank@mail.nl", "password", Arrays.asList(Role.ROLE_USER, Role.ROLE_EMPLOYEE), true, new ArrayList<>(), 99999999999.00, 999999999999.00));
        users.add(new User(UUID.fromString("34d6a6e4-b52b-474e-8807-8d0479f099a4"), "Harry", "Potter", "harrypotter@mail.nl", "password", Arrays.asList(Role.ROLE_USER, Role.ROLE_EMPLOYEE), true, new ArrayList<>(), 1000.00, 10000.00));
        users.add(new User(UUID.fromString("347b0281-2d8e-4a29-bfc9-033b993bdcc8"), "Ronald", "Weasley", "ronaldweasley@mail.nl", "password", Arrays.asList(Role.ROLE_USER, Role.ROLE_EMPLOYEE), true, new ArrayList<>(), 1000.00, 10000.00));
        users.add(new User(UUID.fromString("347b0281-2d8e-4a29-bfc9-033b993bdcc8"), "Ronald", "Weasley", "ronaldweasley@mail.nl", "password", Arrays.asList(Role.ROLE_USER), true, new ArrayList<>(), 1000.00, 10000.00));
        users.add(new User(UUID.fromString("407d4df7-73b1-44b7-b193-73a35a1403c8"), "Hermione", "Granger", "hermionegranger@mail.nl", "password", Arrays.asList(Role.ROLE_USER), true, new ArrayList<>(), 1000.00, 10000.00));
        userRepository.saveAll(users);



        // Initialize transactions
        transactions = new ArrayList<>();
        transactions.add(new Transaction(UUID.randomUUID(), "NL01INHO0000000001", "NL01INHO0000000002", 100.0, Transaction.TransactionTypeEnum.DEPOSIT, OffsetDateTime.now(),"Test transaction 1"));
//        transactions.add(new Transaction("NL01INHO0000000001", "NL01INHO0000000002", 50.0));
//        transactionRepository.saveAll(transactions);
    }

    @Test
    public void testGetTransactionsByToIBAN() {
        // Create test data
        Transaction transaction1 = new Transaction();
        transaction1.setFromIBAN("NL01FROM");
        transaction1.setToIBAN("NL02TO");
        transactionRepository.save(transaction1);

        Transaction transaction2 = new Transaction();
        transaction2.setFromIBAN("NL03FROM");
        transaction2.setToIBAN("NL04TO");
        transactionRepository.save(transaction2);

        Transaction transaction3 = new Transaction();
        transaction3.setFromIBAN("NL05FROM");
        transaction3.setToIBAN("NL02TO");
        transactionRepository.save(transaction3);

        Transaction transaction4 = new Transaction();
        transaction4.setFromIBAN("NL06FROM");
        transaction4.setToIBAN("NL07TO");
        transactionRepository.save(transaction4);

        // Perform the repository method call
        List<Transaction> result = transactionRepository.getTransactionsByToIBAN("NL02TO", PageRequest.of(0, 10));

        // Assert the result
        assertThat(result).containsExactly(transaction1, transaction3);
    }

    @Test
    public void testGetTransactionsByFromIBAN() {
        // Create test data
        Transaction transaction1 = new Transaction();
        transaction1.setFromIBAN("NL01FROM");
        transaction1.setToIBAN("NL02TO");
        transactionRepository.save(transaction1);

        Transaction transaction2 = new Transaction();
        transaction2.setFromIBAN("NL01FROM");
        transaction2.setToIBAN("NL03TO");
        transactionRepository.save(transaction2);

        Transaction transaction3 = new Transaction();
        transaction3.setFromIBAN("NL04FROM");
        transaction3.setToIBAN("NL02TO");
        transactionRepository.save(transaction3);

        Transaction transaction4 = new Transaction();
        transaction4.setFromIBAN("NL05FROM");
        transaction4.setToIBAN("NL06TO");
        transactionRepository.save(transaction4);

        // Perform the repository method call
        List<Transaction> result = transactionRepository.getTransactionsByFromIBAN("NL01FROM", PageRequest.of(0, 10));

        // Assert the result
        assertThat(result).containsExactly(transaction1, transaction2);
    }

    @Test
    public void testGetTransactionsByAmountLessThan() {
        // Create test data
        Transaction transaction1 = new Transaction();
        transaction1.setFromIBAN("NL01FROM");
        transaction1.setToIBAN("NL02TO");
        transaction1.setAmount(50.0);
        transactionRepository.save(transaction1);

        Transaction transaction2 = new Transaction();
        transaction2.setFromIBAN("NL03FROM");
        transaction2.setToIBAN("NL04TO");
        transaction2.setAmount(75.0);
        transactionRepository.save(transaction2);

        Transaction transaction3 = new Transaction();
        transaction3.setFromIBAN("NL05FROM");
        transaction3.setToIBAN("NL06TO");
        transaction3.setAmount(100.0);
        transactionRepository.save(transaction3);

        Transaction transaction4 = new Transaction();
        transaction4.setFromIBAN("NL07FROM");
        transaction4.setToIBAN("NL08TO");
        transaction4.setAmount(30.0);
        transactionRepository.save(transaction4);

        Transaction transaction5 = new Transaction();
        transaction5.setFromIBAN("NL09FROM");
        transaction5.setToIBAN("NL10TO");
        transaction5.setAmount(90.0);
        transactionRepository.save(transaction5);

        // Perform the repository method call
        List<Transaction> result = transactionRepository.getTransactionsByAmountLessThan(80.0, PageRequest.of(0, 10));

        // Assert the result
        assertThat(result).containsExactly(transaction1, transaction4);
    }

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
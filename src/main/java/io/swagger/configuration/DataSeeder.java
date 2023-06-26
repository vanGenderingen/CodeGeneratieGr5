package io.swagger.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.api.repository.AccountRepository;
import io.swagger.api.repository.UserRepository;
import io.swagger.api.service.AccountService;
import io.swagger.api.service.TransactionService;
import io.swagger.api.service.UserService;
import io.swagger.model.*;
import io.swagger.model.DTO.CreateTransactionDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.UUID;

@Component
public class DataSeeder implements ApplicationRunner {

    @Autowired
    UserService userService;
    @Autowired
    UserRepository userRepository;

    @Autowired
    AccountService accountService;
    @Autowired
    AccountRepository accountRepository;
    @Autowired
    TransactionService transactionService;

    @Autowired
    PasswordEncoder passwordEncoder;

    private ObjectMapper objectMapper;

    @Override
    public void run(ApplicationArguments args) {
        objectMapper = new ObjectMapper();
        //create users
        //create employee users
        User bank = new User(UUID.fromString("03259d6f-4c93-40f0-b18d-be04f42537fc"), "bank", "bank", "debank@mail.nl", encodePassword("password"), Arrays.asList(Role.ROLE_USER, Role.ROLE_EMPLOYEE), true, new ArrayList<>(), 99999999999.00, 999999999999.00);
        userRepository.save(bank);
        User bank2 = userService.getUserByEmail("debank@mail.nl");
        userRepository.save(bank2);
        User bank3 = new User(UUID.fromString("34d6a6e4-b52b-474e-8807-8d0479f099a4"), "Harry", "Potter", "harrypotter@mail.nl", encodePassword("password"), Arrays.asList(Role.ROLE_USER, Role.ROLE_EMPLOYEE), true, new ArrayList<>(), 1000.00, 10000.00);
        userRepository.save(bank3);
        User bank4 = userService.getUserByEmail("harrypotter@mail.nl");
        userRepository.save(bank4);
        User bank5 = new User(UUID.fromString("347b0281-2d8e-4a29-bfc9-033b993bdcc8"), "Ronald", "Weasley", "ronaldweasley@mail.nl", encodePassword("password"), Arrays.asList(Role.ROLE_USER, Role.ROLE_EMPLOYEE), true, new ArrayList<>(), 1000.00, 10000.00);
        userRepository.save(bank5);
        User bank6 = userService.getUserByEmail("ronaldweasley@mail.nl");
        userRepository.save(bank6);
        User bank7 = new User(UUID.fromString("407d4df7-73b1-44b7-b193-73a35a1403c8"), "Hermione", "Granger", "hermionegranger@mail.nl", encodePassword("password"), Arrays.asList(Role.ROLE_USER, Role.ROLE_EMPLOYEE), true, new ArrayList<>(), 1000.00, 10000.00);
        userRepository.save(bank7);
        User bank8 = userService.getUserByEmail("hermionegranger@mail.nl");

//create user users
        User user = new User(UUID.fromString("bb0cc36d-69a7-471e-a665-3609bc14c27a"), "John", "Doe", "john.doe@mail.nl", encodePassword("password"), Arrays.asList(Role.ROLE_USER), true, new ArrayList<>(), 1000.00, 1000.00);
        userRepository.save(user);
        User user2 = userService.getUserByEmail("john.doe@mail.nl");

        User user3 = new User(UUID.fromString("bb0cc36d-69a7-471e-a665-3609bc14c27b"), "Severus", "Snape", "severus.snape@mail.nl", encodePassword("password"), Arrays.asList(Role.ROLE_USER), true, new ArrayList<>(), 1000.00, 10000.00);
        userRepository.save(user3);
        User user4 = userService.getUserByEmail("severus.snape@mail.nl");

        User user5 = new User(UUID.fromString("bb0cc36d-69a7-471e-a665-3609bc14c27c"), "Albus", "Dumbledore", "albus.dumbledore@mail.nl", encodePassword("password"), Arrays.asList(Role.ROLE_USER), true, new ArrayList<>(), 1000.00, 10000.00);
        userRepository.save(user5);
        User user6 = userService.getUserByEmail("albus.dumbledore@mail.nl");

        User user7 = new User(UUID.fromString("bb0cc36d-69a7-471e-a665-3609bc14c27d"), "Sir", "Lancelot", "sir.lancelot@mail.nl", encodePassword("password"), Arrays.asList(Role.ROLE_USER), true, new ArrayList<>(), 1000.00, 10000.00);
        userRepository.save(user7);
        User user8 = userService.getUserByEmail("sir.lancelot@mail.nl");

        User user9 = new User(UUID.fromString("3d892b84-1377-11ee-be56-0242ac120002"), "Jelle", "Koomen", "jelle_koomen@outlook.com", encodePassword("password"), Arrays.asList(Role.ROLE_USER), true, new ArrayList<>(), 1000.00, 10000.00);
        userRepository.save(user9);
        User user10 = userService.getUserByEmail("jelle_koomen@outlook.com");


        //create accounts
        //create one account for bank
        Account bankAccount = new Account(UUID.randomUUID(), bank2, bank2.getUserID(), "Bank account", "NL01INHO0000000001" ,9999999999999999.00, AccountType.CURRENT, -9999999999999999.00, true);
        accountRepository.save(bankAccount);

        //create a current account and savings account for every user
        Account bankAccountEmployee1 = new Account(UUID.randomUUID(), bank4, bank4.getUserID(), "Current account Harry Potter", "NL01INHO0000000002" ,100.00, AccountType.CURRENT, -100.00, true);
        accountRepository.save(bankAccountEmployee1);

        Account savingsAccountEmployee1 = new Account(UUID.randomUUID(), bank4, bank4.getUserID(), "Savings account Harry Potter", "NL01INHO0000000003" ,100.00, AccountType.SAVINGS, -100.00, true);
        accountRepository.save(savingsAccountEmployee1);

        Account bankAccountEmployee2 = new Account(UUID.randomUUID(), bank6, bank6.getUserID(), "Current account Ronald Weasly", "NL01INHO0000000004" ,100.00, AccountType.CURRENT, -100.00, true);
        accountRepository.save(bankAccountEmployee2);

        Account bankAccountEmployee3 = new Account(UUID.randomUUID(), bank8, bank8.getUserID(), "Current account Hermione Granger", "NL01INHO0000000006" ,100.00, AccountType.CURRENT, -100.00, true);
        accountRepository.save(bankAccountEmployee3);

        Account savingsAccountEmployee3 = new Account(UUID.randomUUID(), bank8, bank8.getUserID(), "Savings account Hermione Granger", "NL01INHO0000000007" ,100.00, AccountType.SAVINGS, -100.00, true);
        accountRepository.save(savingsAccountEmployee3);

        //create user accounts
        Account currentAccountUser2 = new Account(UUID.randomUUID(), user2, user2.getUserID(), "Current account John Doe", "NL01INHO0000000010", 100.00, AccountType.CURRENT, -100.00, true);
        accountRepository.save(currentAccountUser2);

        Account savingsAccountUser2 = new Account(UUID.randomUUID(), user2, user2.getUserID(), "Savings account John Doe", "NL01INHO0000000011", 100.00, AccountType.SAVINGS, -100.00, true);
        accountRepository.save(savingsAccountUser2);

        Account currentAccountUser4 = new Account(UUID.randomUUID(), user4, user4.getUserID(), "Current account Severus Snape", "NL01INHO0000000012", 100.00, AccountType.CURRENT, -100.00, true);
        accountRepository.save(currentAccountUser4);

        Account savingsAccountUser4 = new Account(UUID.randomUUID(), user4, user4.getUserID(), "Savings account Severus Snape", "NL01INHO0000000013", 100.00, AccountType.SAVINGS, -100.00, true);
        accountRepository.save(savingsAccountUser4);

        Account currentAccountUser6 = new Account(UUID.randomUUID(), user6, user6.getUserID(), "Current account Albus Dumbledore", "NL01INHO0000000014", 100.00, AccountType.CURRENT, -100.00, true);
        accountRepository.save(currentAccountUser6);

        Account savingsAccountUser6 = new Account(UUID.randomUUID(), user6, user6.getUserID(), "Savings account Albus Dumbledore", "NL01INHO0000000015", 100.00, AccountType.SAVINGS, -100.00, true);
        accountRepository.save(savingsAccountUser6);

        Account currentAccountUser8 = new Account(UUID.randomUUID(), user8, user8.getUserID(), "Current account Sir Lancelot", "NL01INHO0000000016", 100.00, AccountType.CURRENT, -100.00, true);
        accountRepository.save(currentAccountUser8);

        Account savingsAccountUser8 = new Account(UUID.randomUUID(), user8, user8.getUserID(), "Savings account Sir Lancelot", "NL01INHO0000000017", 100.00, AccountType.SAVINGS, -100.00, true);
        accountRepository.save(savingsAccountUser8);

        //create transactions, 4 for every account

//        //transactions for account 2
        CreateTransactionDTO transactionAccount2toAccount3 = new CreateTransactionDTO("NL01INHO0000000002", "NL01INHO0000000003", 50.00, TransactionType.WITHDRAWAL, "Test transaction 1");
        Transaction transaction1 = objectMapper.convertValue(transactionAccount2toAccount3, Transaction.class);
        transaction1.setUserPerforming(bank2.getUserID());
        transactionService.add(transaction1);

        CreateTransactionDTO transactionAccount2toAccount4 = new CreateTransactionDTO("NL01INHO0000000002", "NL01INHO0000000004", 150.00, TransactionType.WITHDRAWAL, "Test transaction 2");
        Transaction transaction2 = objectMapper.convertValue(transactionAccount2toAccount4, Transaction.class);
        transaction2.setUserPerforming(bank2.getUserID());
        transactionService.add(transaction2);

        CreateTransactionDTO transactionAccount2fromAccount5 = new CreateTransactionDTO("NL01INHO0000000006", "NL01INHO0000000002", 50.00, TransactionType.WITHDRAWAL, "Test transaction 3");
        Transaction transaction3 = objectMapper.convertValue(transactionAccount2fromAccount5, Transaction.class);
        transaction3.setUserPerforming(bank2.getUserID());
        transactionService.add(transaction3);

        CreateTransactionDTO transactionAccount2fromAccount6 = new CreateTransactionDTO("NL01INHO0000000006", "NL01INHO0000000002", 150.00, TransactionType.WITHDRAWAL, "Test transaction 4");
        Transaction transaction4 = objectMapper.convertValue(transactionAccount2fromAccount6, Transaction.class);
        transaction4.setUserPerforming(bank2.getUserID());
        transactionService.add(transaction4);

        //transactions for account 3
        CreateTransactionDTO transactionAccount3toAccount4 = new CreateTransactionDTO("NL01INHO0000000003", "NL01INHO0000000004", 50.00, TransactionType.WITHDRAWAL, "Test transaction 5");
        Transaction transaction5 = objectMapper.convertValue(transactionAccount3toAccount4, Transaction.class);
        transaction5.setUserPerforming(bank2.getUserID());
        transactionService.add(transaction5);

        CreateTransactionDTO transactionAccount3toAccount5 = new CreateTransactionDTO("NL01INHO0000000003", "NL01INHO0000000006", 150.00, TransactionType.WITHDRAWAL, "Test transaction 6");
        Transaction transaction6 = objectMapper.convertValue(transactionAccount3toAccount5, Transaction.class);
        transaction6.setUserPerforming(bank2.getUserID());
        transactionService.add(transaction6);

        CreateTransactionDTO transactionAccount3fromAccount6 = new CreateTransactionDTO("NL01INHO0000000006", "NL01INHO0000000003", 50.00, TransactionType.WITHDRAWAL, "Test transaction 7");
        Transaction transaction7 = objectMapper.convertValue(transactionAccount3fromAccount6, Transaction.class);
        transaction7.setUserPerforming(bank2.getUserID());
        transactionService.add(transaction7);

        CreateTransactionDTO transactionAccount3fromAccount7 = new CreateTransactionDTO("NL01INHO0000000007", "NL01INHO0000000003", 150.00, TransactionType.WITHDRAWAL, "Test transaction 8");
        Transaction transaction8 = objectMapper.convertValue(transactionAccount3fromAccount7, Transaction.class);
        transaction8.setUserPerforming(bank2.getUserID());
        transactionService.add(transaction8);

        CreateTransactionDTO transactionJD = new CreateTransactionDTO("NL01INHO0000000010", "NL01INHO0000000011", 10.00, TransactionType.TRANSFER, "Test transactions");
        Transaction transaction9 = objectMapper.convertValue(transactionJD, Transaction.class);
        transaction9.setUserPerforming(bank2.getUserID());
        transactionService.add(transaction9);

        CreateTransactionDTO transactionJD2 = new CreateTransactionDTO("NL01INHO0000000010", "NL01INHO0000000011", 10.00, TransactionType.TRANSFER, "Test transactions 2");
        Transaction transaction10 = objectMapper.convertValue(transactionJD2, Transaction.class);
        transaction10.setUserPerforming(bank2.getUserID());
        transactionService.add(transaction10);
    }

    private String encodePassword(String password) {
        return passwordEncoder.encode(password);
    }
}

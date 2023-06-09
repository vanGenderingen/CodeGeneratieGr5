package io.swagger.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.api.service.AccountService;
import io.swagger.api.service.TransactionService;
import io.swagger.api.service.UserService;
import io.swagger.model.Account;
import io.swagger.model.DTO.CreateAccountDTO;
import io.swagger.model.DTO.CreateTransactionDTO;
import io.swagger.model.Role;
import io.swagger.model.Transaction;
import io.swagger.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.UUID;

@Component
public class DataSeeder implements ApplicationRunner {

    @Autowired
    UserService userService;

    @Autowired
    AccountService accountService;

    @Autowired
    TransactionService transactionService;

    private ObjectMapper objectMapper;

    @Override
    public void run(ApplicationArguments args) {
        objectMapper = new ObjectMapper();

        User bank = new User(UUID.fromString("03259d6f-4c93-40f0-b18d-be04f42537fc"), "bank", "bank", "debank@mail.nl", "password", Arrays.asList(Role.ROLE_USER, Role.ROLE_EMPLOYEE), true, new ArrayList<>(), 99999999999.00, 999999999999.00);
        userService.add(bank);

        User johnDoeUser = new User(UUID.fromString("bb0cc36d-69a7-471e-a665-3609bc14c27a"), "john", "doe", "john.doe@mail.nl", "password", Arrays.asList(Role.ROLE_USER), true, new ArrayList<>(), 1000.00, 10000.00);
        userService.add(johnDoeUser);

        //User johnDoeUser = new User(UUID.randomUUID(), "john", "doe", "john.doe@mail.nl", "password", User.RoleEnum.USER, true, new ArrayList<>(), 1000.00, 10000.00);
        //userService.add(johnDoeUser);

        CreateAccountDTO createAccountDTO1 = new CreateAccountDTO("Bank", 9999999999999999.00, CreateAccountDTO.TypeEnum.CURRENT, -9999999999999999.00, bank.getUserID());
        Account bankAccount = objectMapper.convertValue(createAccountDTO1, Account.class);
        bankAccount.setIBAN("NL01INHO0000000001");
        bankAccount.setUser(bank);
        accountService.add(bankAccount);

        CreateAccountDTO createAccountJohnDoe = new CreateAccountDTO("saving account John Doe", 100.00, CreateAccountDTO.TypeEnum.SAVINGS, 0.00, johnDoeUser.getUserID());
        Account accountJohnDoe = objectMapper.convertValue(createAccountJohnDoe, Account.class);
        accountJohnDoe.setIBAN("NL01INHO0000000002");
        accountJohnDoe.setUser(johnDoeUser);
        accountService.add(accountJohnDoe);

        //Account bankAccount = new Account(UUID.randomUUID(), bank2, bank2.getUserID(), "Bank account", "NL01INHO0000000001" ,9999999999999999.00, Account.TypeEnum.CURRENT, -9999999999999999.00, true);
        //accountService.add(bankAccount);

        //needs looking at
        CreateTransactionDTO createTransactionDTO1 = new CreateTransactionDTO("NL01INHO0000000001", "NL01INHO0000000002", 100.00, CreateTransactionDTO.TransactionTypeEnum.DEPOSIT, "test transaction");
        transactionService.add(objectMapper.convertValue(createTransactionDTO1, Transaction.class));
        CreateTransactionDTO createTransactionDTO2 = new CreateTransactionDTO("NL01INHO0000000001", "NL01INHO0000000002", 100.00, CreateTransactionDTO.TransactionTypeEnum.WITHDRAWAL, "test transaction");
        transactionService.add(objectMapper.convertValue(createTransactionDTO2, Transaction.class));
    }
}

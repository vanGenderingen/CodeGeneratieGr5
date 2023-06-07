package io.swagger.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.api.service.AccountService;
import io.swagger.api.service.TransactionService;
import io.swagger.api.service.UserService;
import io.swagger.model.Account;
import io.swagger.model.DTO.CreateAccountDTO;
import io.swagger.model.DTO.CreateTransactionDTO;
import io.swagger.model.Transaction;
import io.swagger.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
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

        User bank = new User(UUID.randomUUID(), "bank", "bank", "debank@mail.nl", "password", User.RoleEnum.EMPLOYEE, true, new ArrayList<>(), 99999999999.00, 999999999999.00);
        userService.add(bank);

        CreateAccountDTO createAccountDTO1 = new CreateAccountDTO(bank.getUserID(), "Bank", 9999999999999999.00, CreateAccountDTO.TypeEnum.CURRENT, -9999999999999999.00);
        Account bankAccount = objectMapper.convertValue(createAccountDTO1, Account.class);
        bankAccount.setIBAN("NL01INHO0000000001");
        accountService.add(bankAccount);

        User johnDoeUser = new User(UUID.randomUUID(), "john", "doe", "john.doe@mail.nl", "password", User.RoleEnum.USER, true, new ArrayList<>(), 1000.00, 10000.00);
        userService.add(bank);

        CreateAccountDTO createAccountJohnDoe = new CreateAccountDTO(johnDoeUser.getUserID(), "saving account John Doe", 100.00, CreateAccountDTO.TypeEnum.SAVINGS, 0.00);
        Account accountJohnDoe = objectMapper.convertValue(createAccountJohnDoe, Account.class);
        accountService.add(accountJohnDoe);

        CreateTransactionDTO createTransactionDTO = new CreateTransactionDTO(bankAccount.getIBAN(),accountJohnDoe.getIBAN(), 100.00, CreateTransactionDTO.TransactionTypeEnum.DEPOSIT, "from bank to john doe");
        Transaction transaction = objectMapper.convertValue(createTransactionDTO, Transaction.class);
        transaction.setUserPerforming(bankAccount.getUserID());
        transactionService.add(transaction);

    }
}

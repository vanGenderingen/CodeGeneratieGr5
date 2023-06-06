package io.swagger.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.api.service.AccountService;
import io.swagger.api.service.TransactionService;
import io.swagger.api.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

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

//        CreateTransactionDTO createTransactionDTO = new CreateTransactionDTO("NL01INHO0000000001","NL01INHO0000000001", 100.00, CreateTransactionDTO.TransactionTypeEnum.DEPOSIT, "test");
//        Transaction transaction = objectMapper.convertValue(createTransactionDTO, Transaction.class);
//        transactionService.add(transaction);
//        User user1 = new User(UUID.randomUUID(), "john", "doe", "mail@mail.nl", "password", User.RoleEnum.USER, true, new ArrayList<>(), 100.00, 100.00);
//        userService.add(user1);
    }
}

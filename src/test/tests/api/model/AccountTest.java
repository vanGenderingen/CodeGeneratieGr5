package tests.api.model;

import io.swagger.model.Account;
import org.junit.Before;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;

@SpringBootTest
public class AccountTest{
    private Account createdAccount;

    @Before
    public void setup() {
        createdAccount = new Account();
    }

    @Test
    public void createUserShouldNotBeNull() {
        assertNotNull(createdAccount);
    }
}
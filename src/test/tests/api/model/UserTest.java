package tests.api.model;

import io.swagger.model.User;
import org.junit.Before;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;

@SpringBootTest
public class UserTest {

    private User createdUser;

    @Before
    public void setup() {
        createdUser = new User();
    }

    @Test
    public void createUserShouldNotBeNull() {
        assertNotNull(createdUser);
    }
}

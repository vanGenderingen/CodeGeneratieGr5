package io.swagger.model;

import io.cucumber.java.BeforeAll;
import junit.framework.TestCase;

import java.util.UUID;

public class UserTest extends TestCase {

    private final User user = new User();
    @BeforeAll
    public void setUp() throws Exception {
        super.setUp();
        User user = new User();
    }

    public void testSetAndGetUserID() {
        UUID uuid = UUID.randomUUID();
        user.setUserID(uuid);

        assertEquals(uuid, user.getUserID());
    }

    public void testSetAndGetFirstName() {
        String firstName = "John";
        user.setFirstName(firstName);

        assertEquals(firstName, user.getFirstName());
    }

    public void testSetAndGetLastName() {
        String lastName = "Doe";
        user.setLastName(lastName);

        assertEquals(lastName, user.getLastName());
    }

    public void testSetAndGetEmail() {
        String email = "JohnDoe@Example.com";
        user.setEmail(email);

        assertEquals(email, user.getEmail());
    }

    public void testSetAndGetPassword() {
        String password = "Password123";
        user.setPassword(password);

        assertEquals(password, user.getPassword());
    }

    public void testSetAndGetRole() {
        User.RoleEnum role = User.RoleEnum.USER;
        user.setRole(role);

        assertEquals(role, user.getRole());
    }

    public void testSetAndGetActive() {
        Boolean active = true;
        user.setActive(active);

        assertEquals(active, user.getActive());
    }

    public void testSetAndGetTransactionLimit() {
        Double transactionLimit = 1000.0;
        user.setTransactionLimit(transactionLimit);

        assertEquals(transactionLimit, user.getTransactionLimit());
    }

    public void testSetAndGetDailyLimit() {
        Double dailyLimit = 100.0;
        user.setDailyLimit(dailyLimit);

        assertEquals(dailyLimit, user.getDailyLimit());
    }


}
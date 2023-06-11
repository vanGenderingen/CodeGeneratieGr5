package io.swagger.model;

import io.cucumber.java.BeforeAll;
import org.junit.Before;
import org.junit.Test;

import java.util.UUID;

import static junit.framework.TestCase.*;

public class AccountTest {
    private Account account;
    @Before
    @BeforeAll
    public void setUp() throws Exception {
        account = new Account();
    }

    @Test
    public void testSetAndGetAccountID() {
        UUID accountId = UUID.randomUUID();
        account.setAccountID(accountId);

        assertEquals(accountId, account.getAccountID());
    }

    @Test
    public void testSetAndGetUser() {
        User user = new User();
        account.setUser(user);

        assertEquals(user, account.getUser());
    }

    @Test
    public void testSetAndGetUserID() {
        UUID userId = UUID.randomUUID();
        account.setUserID(userId);

        assertEquals(userId, account.getUserID());
    }

    @Test
    public void testSetAndGetName() {
        String name = "John Doe";
        account.setName(name);

        assertEquals(name, account.getName());
    }

    @Test
    public void testSetAndGetIBAN() {
        String iban = "ABC123XYZ";
        account.setIBAN(iban);

        assertEquals(iban, account.getIBAN());
    }

    @Test
    public void testSetAndGetBalance() {
        Double balance = 1000.0;
        account.setBalance(balance);

        assertEquals(balance, account.getBalance());
    }

    @Test
    public void testSetAndGetType() {
        Account.TypeEnum type = Account.TypeEnum.CURRENT;
        account.setType(type);

        assertEquals(type, account.getType());
    }

    @Test
    public void testSetAndGetMinBal() {
        Double minBal = 100.0;
        account.setMinBal(minBal);

        assertEquals(minBal, account.getMinBal());
    }

    @Test
    public void testSetAndGetActive() {
        Boolean active = true;
        account.setActive(active);

        assertEquals(active, account.getActive());
    }

    @Test
    public void testTypeEnumToString() {
        String expected = "Current";
        String actual = Account.TypeEnum.CURRENT.toString();

        assertEquals(expected, actual);
    }

    @Test
    public void testTypeEnumFromValue() {
        Account.TypeEnum expected = Account.TypeEnum.SAVINGS;
        Account.TypeEnum actual = Account.TypeEnum.fromValue("Savings");

        assertEquals(expected, actual);
    }

    @Test
    public void testAccountConstructor() {
        Account account = new Account();
        assertNotNull(account);
        assertTrue(account.getActive());
    }
}
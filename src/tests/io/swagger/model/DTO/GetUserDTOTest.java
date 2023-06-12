package io.swagger.model.DTO;

import io.swagger.model.Account;
import io.swagger.model.Role;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public class GetUserDTOTest {

    @Test
    public void testGetUserDTO() {
        // Create test data
        UUID userID = UUID.randomUUID();
        String firstName = "John";
        String lastName = "Doe";
        String email = "john.doe@example.com";
        List<Role> roles = Arrays.asList(Role.ROLE_EMPLOYEE, Role.ROLE_USER);
        Boolean active = true;
        List<Account> accounts = Arrays.asList(new Account(), new Account());
        Double transactionLimit = 1000.0;
        Double dailyLimit = 500.0;

        // Create a GetUserDTO instance
        GetUserDTO getUserDTO = new GetUserDTO();
        getUserDTO.setUserID(userID);
        getUserDTO.setFirstName(firstName);
        getUserDTO.setLastName(lastName);
        getUserDTO.setEmail(email);
        getUserDTO.setRoles(roles);
        getUserDTO.setActive(active);
        getUserDTO.setAccounts(accounts);
        getUserDTO.setTransactionLimit(transactionLimit);
        getUserDTO.setDailyLimit(dailyLimit);

        // Verify the values
        Assertions.assertEquals(userID, getUserDTO.getUserID());
        Assertions.assertEquals(firstName, getUserDTO.getFirstName());
        Assertions.assertEquals(lastName, getUserDTO.getLastName());
        Assertions.assertEquals(email, getUserDTO.getEmail());
        Assertions.assertEquals(roles, getUserDTO.getRoles());
        Assertions.assertEquals(active, getUserDTO.getActive());
        Assertions.assertEquals(accounts, getUserDTO.getAccounts());
        Assertions.assertEquals(transactionLimit, getUserDTO.getTransactionLimit());
        Assertions.assertEquals(dailyLimit, getUserDTO.getDailyLimit());
    }
}

package io.swagger.model.DTO;

import io.swagger.model.Role;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

public class UpdateUserDTOTest {

    @Test
    public void testUpdateUserDTO() {
        // Create test data
        String firstName = "John";
        String lastName = "Doe";
        String email = "john.doe@example.com";
        String password = "password123";
        List<Role> roles = Arrays.asList(Role.ROLE_EMPLOYEE, Role.ROLE_USER);
        Boolean active = true;
        Double transactionLimit = 1000.0;
        Double dailyLimit = 500.0;

        // Create an UpdateUserDTO instance
        UpdateUserDTO updateUserDTO = new UpdateUserDTO();
        updateUserDTO.setFirstName(firstName);
        updateUserDTO.setLastName(lastName);
        updateUserDTO.setEmail(email);
        updateUserDTO.setPassword(password);
        updateUserDTO.setRoles(roles);
        updateUserDTO.setActive(active);
        updateUserDTO.setTransactionLimit(transactionLimit);
        updateUserDTO.setDailyLimit(dailyLimit);

        // Verify the values
        Assertions.assertEquals(firstName, updateUserDTO.getFirstName());
        Assertions.assertEquals(lastName, updateUserDTO.getLastName());
        Assertions.assertEquals(email, updateUserDTO.getEmail());
        Assertions.assertEquals(password, updateUserDTO.getPassword());
        Assertions.assertEquals(roles, updateUserDTO.getRoles());
        Assertions.assertEquals(active, updateUserDTO.getActive());
        Assertions.assertEquals(transactionLimit, updateUserDTO.getTransactionLimit());
        Assertions.assertEquals(dailyLimit, updateUserDTO.getDailyLimit());
    }
}

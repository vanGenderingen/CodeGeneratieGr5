package io.swagger.model.DTO;

import io.swagger.model.Role;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

public class CreateUserDTOTest {

    @Test
    public void testCreateUserDTO() {
        // Create test data
        String firstName = "John";
        String lastName = "Doe";
        String email = "john.doe@example.com";
        String password = "password123";
        List<Role> roles = Arrays.asList(Role.ROLE_EMPLOYEE, Role.ROLE_USER);
        Double transactionLimit = 1000.0;
        Double dailyLimit = 500.0;

        // Create a CreateUserDTO instance
        CreateUserDTO createUserDTO = new CreateUserDTO(firstName, lastName, email, password, roles, transactionLimit, dailyLimit);

        // Verify the values
        Assertions.assertEquals(firstName, createUserDTO.getFirstName());
        Assertions.assertEquals(lastName, createUserDTO.getLastName());
        Assertions.assertEquals(email, createUserDTO.getEmail());
        Assertions.assertEquals(password, createUserDTO.getPassword());
        Assertions.assertEquals(roles, createUserDTO.getRoles());
        Assertions.assertEquals(transactionLimit, createUserDTO.getTransactionLimit());
        Assertions.assertEquals(dailyLimit, createUserDTO.getDailyLimit());
    }
}

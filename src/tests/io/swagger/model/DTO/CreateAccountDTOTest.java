package io.swagger.model.DTO;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.UUID;

public class CreateAccountDTOTest {

    @Test
    public void testSetAndGetProperties() {
        // Create test data
        String name = "John Doe";
        Double balance = 1000.0;
        CreateAccountDTO.TypeEnum type = CreateAccountDTO.TypeEnum.CURRENT;
        Double minBal = 100.0;
        UUID userId = UUID.randomUUID();

        // Create a CreateAccountDTO instance
        CreateAccountDTO createAccountDTO = new CreateAccountDTO();
        createAccountDTO.setName(name);
        createAccountDTO.setBalance(balance);
        createAccountDTO.setType(type);
        createAccountDTO.setMinBal(minBal);
        createAccountDTO.setUserId(userId);

        // Verify the properties
        Assertions.assertEquals(name, createAccountDTO.getName());
        Assertions.assertEquals(balance, createAccountDTO.getBalance());
        Assertions.assertEquals(type, createAccountDTO.getType());
        Assertions.assertEquals(minBal, createAccountDTO.getMinBal());
        Assertions.assertEquals(userId, createAccountDTO.getUserId());
    }

    @Test
    public void testTypeEnumToString() {
        String expected = "Current";
        String actual = CreateAccountDTO.TypeEnum.CURRENT.toString();

        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void testTypeEnumFromValue() {
        CreateAccountDTO.TypeEnum expected = CreateAccountDTO.TypeEnum.SAVINGS;
        CreateAccountDTO.TypeEnum actual = CreateAccountDTO.TypeEnum.fromValue("Savings");

        Assertions.assertEquals(expected, actual);
    }
}
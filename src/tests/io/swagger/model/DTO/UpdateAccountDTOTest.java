package io.swagger.model.DTO;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class UpdateAccountDTOTest {

    @Test
    public void testSetAndGetProperties() {
        // Create test data
        String name = "John Doe";
        Double balance = 1000.0;
        Double minBal = 100.0;
        Boolean active = true;

        // Create an UpdateAccountDTO instance
        UpdateAccountDTO updateAccountDTO = new UpdateAccountDTO();
        updateAccountDTO.setName(name);
        updateAccountDTO.setBalance(balance);
        updateAccountDTO.setMinBal(minBal);
        updateAccountDTO.setActive(active);

        // Verify the properties
        Assertions.assertEquals(name, updateAccountDTO.getName());
        Assertions.assertEquals(balance, updateAccountDTO.getBalance());
        Assertions.assertEquals(minBal, updateAccountDTO.getMinBal());
        Assertions.assertEquals(active, updateAccountDTO.isActive());
    }

    @Test
    public void testEqualsAndHashCode() {
        // Create two UpdateAccountDTO instances with the same property values
        UpdateAccountDTO dto1 = new UpdateAccountDTO();
        dto1.setName("John Doe");
        dto1.setBalance(1000.0);
        dto1.setMinBal(100.0);
        dto1.setActive(true);

        UpdateAccountDTO dto2 = new UpdateAccountDTO();
        dto2.setName("John Doe");
        dto2.setBalance(1000.0);
        dto2.setMinBal(100.0);
        dto2.setActive(true);

        // Verify that the two instances are equal and have the same hash code
        Assertions.assertEquals(dto1, dto2);
        Assertions.assertEquals(dto1.hashCode(), dto2.hashCode());
    }

    @Test
    public void testToString() {
        // Create an UpdateAccountDTO instance
        UpdateAccountDTO updateAccountDTO = new UpdateAccountDTO();
        updateAccountDTO.setName("John Doe");
        updateAccountDTO.setBalance(1000.0);
        updateAccountDTO.setMinBal(100.0);
        updateAccountDTO.setActive(true);

        // Verify the string representation
        String expected = "class UpdateAccountDTO {\n" +
                "    name: John Doe\n" +
                "    balance: 1000.0\n" +
                "    minBal: 100.0\n" +
                "    active: true\n" +
                "}";
        Assertions.assertEquals(expected, updateAccountDTO.toString());
    }
}
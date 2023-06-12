package io.swagger.model.DTO;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.UUID;

public class GetAccountDTOTest {

    @Test
    public void testSetAndGetProperties() {
        // Create test data
        UUID accountID = UUID.randomUUID();
        UUID userID = UUID.randomUUID();
        String name = "John Doe";
        Double balance = 1000.0;
        GetAccountDTO.TypeEnum type = GetAccountDTO.TypeEnum.CURRENT;
        Double minBal = 100.0;
        Boolean active = true;
        String iban = "ABC123XYZ";

        // Create a GetAccountDTO instance
        GetAccountDTO getAccountDTO = new GetAccountDTO();
        getAccountDTO.setAccountID(accountID);
        getAccountDTO.setUserID(userID);
        getAccountDTO.setName(name);
        getAccountDTO.setBalance(balance);
        getAccountDTO.setType(type);
        getAccountDTO.setMinBal(minBal);
        getAccountDTO.setActive(active);
        getAccountDTO.setIban(iban);

        // Verify the properties
        Assertions.assertEquals(accountID, getAccountDTO.getAccountID());
        Assertions.assertEquals(userID, getAccountDTO.getUserID());
        Assertions.assertEquals(name, getAccountDTO.getName());
        Assertions.assertEquals(balance, getAccountDTO.getBalance());
        Assertions.assertEquals(type, getAccountDTO.getType());
        Assertions.assertEquals(minBal, getAccountDTO.getMinBal());
        Assertions.assertEquals(active, getAccountDTO.getActive());
        Assertions.assertEquals(iban, getAccountDTO.getIban());
    }

    @Test
    public void testTypeEnumToString() {
        String expected = "Current";
        String actual = GetAccountDTO.TypeEnum.CURRENT.toString();

        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void testTypeEnumFromValue() {
        GetAccountDTO.TypeEnum expected = GetAccountDTO.TypeEnum.SAVINGS;
        GetAccountDTO.TypeEnum actual = GetAccountDTO.TypeEnum.fromValue("Savings");

        Assertions.assertEquals(expected, actual);
    }
}
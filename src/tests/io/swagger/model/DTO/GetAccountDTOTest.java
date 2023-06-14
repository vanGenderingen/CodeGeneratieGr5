package io.swagger.model.DTO;

import io.swagger.model.AccountType;
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
        AccountType.TypeEnum type = AccountType.TypeEnum.CURRENT;
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
        String actual = AccountType.TypeEnum.CURRENT.toString();

        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void testTypeEnumFromValue() {
        AccountType.TypeEnum expected = AccountType.TypeEnum.SAVINGS;
        AccountType.TypeEnum actual = AccountType.TypeEnum.fromValue("Savings");

        Assertions.assertEquals(expected, actual);
    }
}
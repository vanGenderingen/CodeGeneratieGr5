package io.swagger.api.service;
import org.junit.Test;

import static org.junit.Assert.*;

public class IBANServiceTest {
    @Test
    public void testGenerateIBAN() {
        String iban = IBANService.generateIBAN();

        assertNotNull(iban);
        assertEquals(18, iban.length());
        assertTrue(iban.startsWith("NL"));
        assertTrue(iban.startsWith("INHO", 4)); // BANKCODE should be "INHO"
    }

    @Test
    public void testGenerateAccountNumber() {
        String accountNumber = IBANService.generateAccountNumber();

        assertNotNull(accountNumber);
        assertEquals(10, accountNumber.length());
        assertTrue(accountNumber.matches("\\d+")); // Should only contain digits
    }

    @Test
    public void testGenerateControlNumber() {
        String accountNumber = "0001234567";
        String controlNumber = IBANService.generateControlNumber(accountNumber);

        assertNotNull(controlNumber);
        assertTrue(accountNumber.matches("\\d+")); // Should only contain digits
        assertEquals("21", controlNumber);
    }
}
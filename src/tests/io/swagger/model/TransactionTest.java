package io.swagger.model;

import junit.framework.TestCase;

import java.time.LocalDateTime;
import java.util.UUID;

public class TransactionTest extends TestCase {

    public void setUp() throws Exception {
        super.setUp();
    }

    public void testOnCreate() {
        Transaction transaction = new Transaction();
        transaction.onCreate();
        assertNotNull(transaction);
    }

    public void testTestToString() {
        Transaction transaction = new Transaction();
        transaction.onCreate();
        assertNotNull(transaction.toString());
    }

    public void testGetTransactionID() {
        Transaction transaction = new Transaction();
        transaction.onCreate();
        assertNotNull(transaction.getTransactionID());
    }
    public void testGetFromIBAN() {
        Transaction transaction = new Transaction();
        transaction.onCreate();
        transaction.setFromIBAN("Test");
        assertNotNull(transaction.getFromIBAN());
    }
    public void testGetToIBAN() {
        Transaction transaction = new Transaction();
        transaction.onCreate();
        transaction.setToIBAN("Test");
        assertNotNull(transaction.getToIBAN());
    }
    public void testGetAmount() {
        Transaction transaction = new Transaction();
        transaction.onCreate();
        transaction.setAmount(1.0);
        assertNotNull(transaction.getAmount());
    }
    public void testGetTransactionType() {
        Transaction transaction = new Transaction();
        transaction.onCreate();
        transaction.setTransactionType(TransactionType.DEPOSIT);
        assertNotNull(transaction.getTransactionType());
    }
    public void testGetUserPerforming() {
        Transaction transaction = new Transaction();
        transaction.onCreate();
        transaction.setUserPerforming(UUID.randomUUID());
        assertNotNull(transaction.getUserPerforming());
    }
    public void testGetTimeStamp() {
        Transaction transaction = new Transaction();
        transaction.onCreate();
        assertNotNull(transaction.getTimeStamp());
    }
    public void testGetDescription() {
        Transaction transaction = new Transaction();
        transaction.onCreate();
        transaction.setDescription("Test");
        assertNotNull(transaction.getDescription());
    }
    public void testSetTransactionID() {
        Transaction transaction = new Transaction();
        transaction.onCreate();
        UUID transactionId = UUID.randomUUID();
        transaction.setTransactionID(transactionId);
        assertEquals(transactionId, transaction.getTransactionID());
    }
    public void testSetFromIBAN() {
        Transaction transaction = new Transaction();
        transaction.onCreate();
        String fromIBAN = "NL01INHO0000000001";
        transaction.setFromIBAN(fromIBAN);
        assertEquals(fromIBAN, transaction.getFromIBAN());
    }
    public void testSetToIBAN() {
        Transaction transaction = new Transaction();
        transaction.onCreate();
        String toIBAN = "NL01INHO0000000002";
        transaction.setToIBAN(toIBAN);
        assertEquals(toIBAN, transaction.getToIBAN());
    }
    public void testSetAmount() {
        Transaction transaction = new Transaction();
        transaction.onCreate();
        double amount = 100.00;
        transaction.setAmount(amount);
        assertEquals(amount, transaction.getAmount());
    }
    public void testSetTransactionType() {
        Transaction transaction = new Transaction();
        transaction.onCreate();
        TransactionType transactionType = TransactionType.DEPOSIT;
        transaction.setTransactionType(transactionType);
        assertEquals(transactionType, transaction.getTransactionType());
    }
    public void testSetUserPerforming() {
        Transaction transaction = new Transaction();
        transaction.onCreate();
        UUID id = UUID.randomUUID();
        transaction.setUserPerforming(id);
        assertEquals(id, transaction.getUserPerforming());
    }
    public void testSetTimeStamp() {
        Transaction transaction = new Transaction();
        transaction.onCreate();
        LocalDateTime timeStamp = LocalDateTime.now();
        transaction.setTimeStamp(timeStamp);
        assertEquals(timeStamp, transaction.getTimeStamp());
    }
    public void testSetDescription() {
        Transaction transaction = new Transaction();
        transaction.onCreate();
        String description = "This is a description";
        transaction.setDescription(description);
        assertEquals(description, transaction.getDescription());
    }
}
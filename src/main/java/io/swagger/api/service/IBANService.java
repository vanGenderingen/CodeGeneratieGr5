package io.swagger.api.service;
import java.math.BigInteger;
import java.time.LocalDateTime;

public class IBANService {
    static String LANDCODE = "NL";
    static String BANKCODE = "INHO";

    //generates complete IBAN up to standards
    public static String generateIBAN(){
        String accountNumber = generateAccountNumber();
        return LANDCODE + generateControlNumber(accountNumber) + BANKCODE + accountNumber;
    }

    //generates account number based on current date and time, so it does not generate duplicates
    public static String generateAccountNumber() {
        LocalDateTime now = LocalDateTime.now();
        int year = now.getYear() % 100; // Get the last two digits of the year
        int month = now.getMonthValue() % 10; // Get the last digit of the month
        int day = now.getDayOfMonth() % 10; // Get the last digit of the day
        int seconds = now.getSecond() % 10; // Get the last digit of the seconds
        int milliseconds = (int) (System.currentTimeMillis() % 1000) % 100; // Get the last 2 digits of the milliseconds

        return "000" + year + month + day + seconds + String.format("%02d", milliseconds);
    }

    //generates control number based on the Statics LANDCODE and BANKCODE and the previously generated account number
    public static String generateControlNumber(String accountNumber) {
        String completeNumber = BANKCODE + accountNumber + LANDCODE;
        StringBuilder numericValue = new StringBuilder();

        // Replace each letter with two digits based on the order in the Latin alphabet
        for (char c : completeNumber.toCharArray()) {
            if (Character.isLetter(c)) {
                int value = Character.toUpperCase(c) - 'A' + 10;
                numericValue.append(value);
            } else {
                numericValue.append(c);
            }
        }

        String numericPart = numericValue.toString();
        BigInteger intValue = new BigInteger(numericPart.substring(0, numericPart.length() - 2) + "00");
        int controlNumber = BigInteger.valueOf(98).subtract(intValue.mod(BigInteger.valueOf(97))).intValue();

        if (controlNumber < 10) {
            return String.format("0" + controlNumber);
        } else {
            return String.valueOf(controlNumber);
        }
    }
}

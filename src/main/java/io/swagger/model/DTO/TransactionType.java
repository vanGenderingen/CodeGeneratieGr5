package io.swagger.model.DTO;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum TransactionType {
    DEPOSIT("Deposit"),
    WITHDRAWAL("Withdrawal"),
    TRANSFER("Transfer");

    private String value;

    TransactionType(String value) {
        this.value = value;
    }

    @Override
    @JsonValue
    public String toString() {
        return String.valueOf(value);
    }

    @JsonCreator
    public static TransactionType fromValue(String text) {
        for (TransactionType b : TransactionType.values()) {
            if (String.valueOf(b.value).equals(text)) {
                return b;
            }
        }
        return null;
    }
}

package io.swagger.api.specification;

import io.swagger.model.Transaction;

public class TransactionSpecification {
    public static Specifiaction<Transaction> getTransactionsByToIBAN(String iban) {
        return (root, query, cb) -> cb.equal(root.get("toIBAN"), iban);
    }

    public static Specifiaction<Transaction> getTransactionsByFromIBAN(String iban) {
        return (root, query, cb) -> cb.equal(root.get("fromIBAN"), iban);
    }


}

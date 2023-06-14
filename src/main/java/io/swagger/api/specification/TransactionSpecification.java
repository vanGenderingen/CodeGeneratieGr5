package io.swagger.api.specification;

import io.swagger.model.transactions.Transaction;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class TransactionSpecification implements Specification<Transaction> {
    private SearchCriteria criteria;

    public TransactionSpecification(SearchCriteria criteria) {
        this.criteria = criteria;
    }

    @Override
    public Predicate toPredicate(Root<Transaction> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        List<Predicate> predicates = new ArrayList<>();
        Optional.ofNullable(criteria.getFromIBAN())
                .ifPresent(fromIBAN -> predicates.add(criteriaBuilder.equal(root.get("fromIBAN"), fromIBAN)));

        Optional.ofNullable(criteria.getToIBAN())
                .ifPresent(toIBAN -> predicates.add(criteriaBuilder.equal(root.get("toIBAN"), toIBAN)));

        Optional.ofNullable(criteria.getTransactionType())
                .ifPresent(transactionType -> predicates.add(criteriaBuilder.equal(root.get("transactionType"), transactionType)));

        Optional.ofNullable(criteria.getUserPerforming())
                .ifPresent(userPerforming -> predicates.add(criteriaBuilder.equal(root.get("userPerforming"), userPerforming)));

        Optional.ofNullable(criteria.getLower())
                .ifPresent(lower -> predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("amount"), lower)));

        Optional.ofNullable(criteria.getHigher())
                .ifPresent(higher -> predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("amount"), higher)));

        Optional.ofNullable(criteria.getEqual())
                .ifPresent(equal -> predicates.add(criteriaBuilder.equal(root.get("amount"), equal)));


        return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
    }
}

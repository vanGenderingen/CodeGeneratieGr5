package io.swagger.api.specification;

import io.swagger.model.Transaction;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalTime;
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
        if(criteria.getFromIBAN() != null && criteria.getToIBAN() != null && criteria.getAccountID() != null){
            predicates.add(criteriaBuilder.or(
                    criteriaBuilder.equal(root.get("toIBAN"), criteria.getToIBAN()),
                    criteriaBuilder.equal(root.get("fromIBAN"), criteria.getFromIBAN())
            ));
        }else {
            Optional.ofNullable(criteria.getFromIBAN())
                .ifPresent(fromIBAN -> predicates.add(criteriaBuilder.equal(root.get("fromIBAN"), fromIBAN)));

            Optional.ofNullable(criteria.getToIBAN())
                    .ifPresent(toIBAN -> predicates.add(criteriaBuilder.equal(root.get("toIBAN"), toIBAN)));
        }



        Optional.ofNullable(criteria.getTransactionType())
                .ifPresent(transactionType -> predicates.add(criteriaBuilder.equal(root.get("transactionType"), transactionType)));

        Optional.ofNullable(criteria.getLower())
                .ifPresent(lower -> predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("amount"), lower)));

        Optional.ofNullable(criteria.getHigher())
                .ifPresent(higher -> predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("amount"), higher)));

        Optional.ofNullable(criteria.getEqual())
                .ifPresent(equal -> predicates.add(criteriaBuilder.equal(root.get("amount"), equal)));

        Optional.ofNullable(criteria.getDate())
                .ifPresent(date -> predicates.add(criteriaBuilder.between(root.get("timeStamp"), date.toLocalDate().atStartOfDay(), date.toLocalDate().atTime(LocalTime.MAX))));

        return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
    }
}

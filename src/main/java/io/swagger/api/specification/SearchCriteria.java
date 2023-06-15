package io.swagger.api.specification;

import io.swagger.model.TransactionType;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
public class SearchCriteria {
    private String fromIBAN;
    private String toIBAN;
    private TransactionType transactionType;
    private UUID userPerforming;
    private Double lower;
    private Double higher;
    private Double equal;
    private LocalDate date;
}

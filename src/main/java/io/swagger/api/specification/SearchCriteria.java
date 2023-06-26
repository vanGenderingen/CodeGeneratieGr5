package io.swagger.api.specification;

import io.swagger.model.TransactionType;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
public class SearchCriteria {
    private String fromIBAN;
    private String toIBAN;
    private TransactionType transactionType;
    private UUID accountID;
    private Double lower;
    private Double higher;
    private Double equal;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime date;
}

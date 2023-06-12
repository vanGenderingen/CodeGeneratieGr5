package io.swagger.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AmountFilter {
    private Double lower;
    private Double higher;
    private Double equal;
}

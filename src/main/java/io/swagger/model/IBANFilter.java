package io.swagger.model;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class IBANFilter {
    private String fromIBAN;
    private String toIBAN;
    private UUID accountID;
}

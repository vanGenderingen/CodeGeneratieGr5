package io.swagger.model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;
@Getter
@Setter
@Data
public class IBANFilter {
    private String fromIBAN;
    private String toIBAN;
    private UUID accountID;
}

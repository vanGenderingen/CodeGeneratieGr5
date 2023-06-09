package io.swagger.model.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.model.AccountType;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Getter;
import lombok.Setter;
import org.springframework.validation.annotation.Validated;

import java.util.UUID;

/**
 * GetAccountDTO
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2023-05-16T13:11:00.686570329Z[GMT]")

@Getter
@Setter
public class GetAccountDTO   {
  @JsonProperty("AccountID")
  private UUID accountID = null;

  @JsonProperty("UserID")
  private UUID userID = null;

  @JsonProperty("Name")
  private String name = null;

  @JsonProperty("Balance")
  private Double balance = null;

  @JsonProperty("Type")
  @Enumerated(EnumType.STRING)
  private AccountType type = null;

  @JsonProperty("MinBal")
  private Double minBal = null;

  @JsonProperty("Active")
  private Boolean active = null;

  @JsonProperty("IBAN")
  private String iban = null;
}
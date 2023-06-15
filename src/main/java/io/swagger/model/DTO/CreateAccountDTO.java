package io.swagger.model.DTO;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;
import io.swagger.model.AccountType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.validation.annotation.Validated;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotNull;
import java.util.UUID;

/**
 * CreateAccountDTO
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2023-05-16T13:11:00.686570329Z[GMT]")

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CreateAccountDTO   {
  @JsonProperty("Name")
  @NotNull
  private String name = null;

  @JsonProperty("Balance")
  @NotNull
  private Double balance = 0.0;

  @JsonProperty("Type")
  @Enumerated(EnumType.STRING)
  private AccountType.TypeEnum type = null;

  @JsonProperty("MinBal")
  @NotNull
  private Double minBal = 0.0;

  @JsonProperty("UserID")
  @NotNull
  private UUID userId;
}
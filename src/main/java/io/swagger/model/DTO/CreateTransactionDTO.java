package io.swagger.model.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.model.transactions.TransactionType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;

/**
 * CreateTransactionDTO
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2023-05-16T13:11:00.686570329Z[GMT]")

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CreateTransactionDTO   {
  @JsonProperty("fromIBAN")
  @NotNull
  private String fromIBAN = null;

  @JsonProperty("toIBAN")
  @NotNull
  private String toIBAN = null;

  @JsonProperty("amount")
  @NotNull
  private Double amount = null;

  @JsonProperty("transactionType")
  @NotNull
  private TransactionType transactionType = null;

  @NotNull
  @JsonProperty("description")
  private String description = null;

}

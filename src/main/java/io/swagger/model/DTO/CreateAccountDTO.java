package io.swagger.model.DTO;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;
import io.swagger.model.User;
import lombok.Getter;
import lombok.Setter;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;
import java.util.UUID;

/**
 * CreateAccountDTO
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2023-05-16T13:11:00.686570329Z[GMT]")

@Getter
@Setter
public class CreateAccountDTO   {

  @JsonProperty("UserID")
  @NotNull
  private UUID userId;

  @JsonProperty("Name")
  @NotNull
  private String name = null;

  @JsonProperty("Balance")
  private Double balance;
  public void setBalance(Double balance) {
    if (balance == null) {
      this.balance = 0.0;
    } else {
      this.balance = balance;
    }
  }

  /**
   * Type of account.
   */
  public enum TypeEnum {
    CURRENT("Current"),

    SAVINGS("Savings");

    private String value;

    TypeEnum(String value) {
      this.value = value;
    }

    @Override
    @JsonValue
    public String toString() {
      return String.valueOf(value);
    }

    @JsonCreator
    public static TypeEnum fromValue(String text) {
      for (TypeEnum b : TypeEnum.values()) {
        if (String.valueOf(b.value).equals(text)) {
          return b;
        }
      }
      return null;
    }
  }
  @JsonProperty("Type")
  @NotNull
  private TypeEnum type = null;

  @JsonProperty("MinBal")
  @NotNull
  private Double minBal = null;
}
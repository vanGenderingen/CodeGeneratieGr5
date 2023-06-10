package io.swagger.model.DTO;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;
import io.swagger.model.Role;
import lombok.Getter;
import lombok.Setter;
import org.springframework.validation.annotation.Validated;

import java.util.List;
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
  private TypeEnum type = null;

  //@JsonProperty("Roles")
  //private List<Role> roles;

  @JsonProperty("MinBal")
  private Double minBal = null;

  @JsonProperty("Active")
  private Boolean active = null;

  @JsonProperty("IBAN")
  private String iban = null;
}
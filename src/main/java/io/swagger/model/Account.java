package io.swagger.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;
import lombok.Setter;
import org.springframework.validation.annotation.Validated;

import javax.persistence.*;
import java.util.UUID;

/**
 * Account
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2023-05-16T13:11:00.686570329Z[GMT]")

@Getter
@Setter
@Entity
public class Account   {
  @Id
  @GeneratedValue
  @JsonProperty("AccountID")
  private UUID accountID = null;

  @ManyToOne
  @JsonProperty("User")
  private User user = null;

  @JsonProperty("UserID")
  private UUID userID;

  @JsonProperty("Name")
  private String name = null;

  @JsonProperty("IBAN")
  private String IBAN = null;

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

  @JsonProperty("MinBal")
  private Double minBal = null;

  @JsonProperty("Active")
  private Boolean active = null;

  public Account() {
    // Initialize userID if user is not null
    if (user != null) {
      this.userID = user.getUserID();
    }
  }
}
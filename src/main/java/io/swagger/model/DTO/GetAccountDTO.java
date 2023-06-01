package io.swagger.model.DTO;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.UUID;
import org.springframework.validation.annotation.Validated;
import javax.validation.Valid;

/**
 * GetAccountDTO
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2023-05-16T13:11:00.686570329Z[GMT]")


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

  @JsonProperty("MinBal")
  private Double minBal = null;

  @JsonProperty("Active")
  private Boolean active = null;

  @JsonProperty("IBAN")
  private String iban = null;

  public GetAccountDTO accountID(UUID accountID) {
    this.accountID = accountID;
    return this;
  }

  /**
   * Unique identifier for the account (primary key).
   * @return accountID
   **/
  @Schema(description = "Unique identifier for the account (primary key).")

  @Valid
  public UUID getAccountID() {
    return accountID;
  }

  public void setAccountID(UUID accountID) {
    this.accountID = accountID;
  }

  public GetAccountDTO userID(UUID userID) {
    this.userID = userID;
    return this;
  }

  /**
   * Identifier of the user who owns the account (foreign key).
   * @return userID
   **/
  @Schema(description = "Identifier of the user who owns the account (foreign key).")

  @Valid
  public UUID getUserID() {
    return userID;
  }

  public void setUserID(UUID userID) {
    this.userID = userID;
  }

  public GetAccountDTO name(String name) {
    this.name = name;
    return this;
  }

  /**
   * Name of the account.
   * @return name
   **/
  @Schema(description = "Name of the account.")

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public GetAccountDTO balance(Double balance) {
    this.balance = balance;
    return this;
  }

  /**
   * Current balance in the account.
   * @return balance
   **/
  @Schema(description = "Current balance in the account.")

  public Double getBalance() {
    return balance;
  }

  public void setBalance(Double balance) {
    this.balance = balance;
  }

  /**
   * Type of account.
   * @return type
   **/
  @Schema(description = "Type of account.")

  public TypeEnum getType() {
    return type;
  }

  public void setType(TypeEnum type) {
    this.type = type;
  }

  public GetAccountDTO minBal(Double minBal) {
    this.minBal = minBal;
    return this;
  }

  /**
   * Minimum required balance for the account.
   * @return minBal
   **/
  @Schema(description = "Minimum required balance for the account.")

  public Double getMinBal() {
    return minBal;
  }

  public void setMinBal(Double minBal) {
    this.minBal = minBal;
  }

  public GetAccountDTO active(Boolean active) {
    this.active = active;
    return this;
  }

  /**
   * Specifies if the account is active or not.
   * @return active
   **/
  @Schema(description = "Specifies if the account is active or not.")

  public Boolean isActive() {
    return active;
  }

  public void setActive(Boolean active) {
    this.active = active;
  }

  /**
   * IBAN (International Bank Account Number) of the account.
   * @return iban
   **/
  @Schema(description = "IBAN (International Bank Account Number) of the account.")

  public String getIBAN() {
    return iban;
  }

  public void setIBAN(String iban) {
    this.iban = iban;
  }
}
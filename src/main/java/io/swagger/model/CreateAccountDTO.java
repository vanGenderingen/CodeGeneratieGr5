package io.swagger.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.UUID;
import org.springframework.validation.annotation.Validated;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * CreateAccountDTO
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2023-05-09T16:01:34.487688617Z[GMT]")


public class CreateAccountDTO   {
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

  public CreateAccountDTO userID(UUID userID) {
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

  public CreateAccountDTO name(String name) {
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

  public CreateAccountDTO balance(Double balance) {
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

  public CreateAccountDTO type(TypeEnum type) {
    this.type = type;
    return this;
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

  public CreateAccountDTO minBal(Double minBal) {
    this.minBal = minBal;
    return this;
  }

  /**
   * Minimum balance required for the account.
   * @return minBal
   **/
  @Schema(description = "Minimum balance required for the account.")
  
    public Double getMinBal() {
    return minBal;
  }

  public void setMinBal(Double minBal) {
    this.minBal = minBal;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    CreateAccountDTO createAccountDTO = (CreateAccountDTO) o;
    return Objects.equals(this.userID, createAccountDTO.userID) &&
        Objects.equals(this.name, createAccountDTO.name) &&
        Objects.equals(this.balance, createAccountDTO.balance) &&
        Objects.equals(this.type, createAccountDTO.type) &&
        Objects.equals(this.minBal, createAccountDTO.minBal);
  }

  @Override
  public int hashCode() {
    return Objects.hash(userID, name, balance, type, minBal);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class CreateAccountDTO {\n");
    
    sb.append("    userID: ").append(toIndentedString(userID)).append("\n");
    sb.append("    name: ").append(toIndentedString(name)).append("\n");
    sb.append("    balance: ").append(toIndentedString(balance)).append("\n");
    sb.append("    type: ").append(toIndentedString(type)).append("\n");
    sb.append("    minBal: ").append(toIndentedString(minBal)).append("\n");
    sb.append("}");
    return sb.toString();
  }

  /**
   * Convert the given object to string with each line indented by 4 spaces
   * (except the first line).
   */
  private String toIndentedString(java.lang.Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }
}

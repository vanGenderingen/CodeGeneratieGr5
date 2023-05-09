package io.swagger.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.swagger.model.Account;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import org.springframework.validation.annotation.Validated;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * GetUserDTO
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2023-05-09T16:01:34.487688617Z[GMT]")


public class GetUserDTO   {
  @JsonProperty("UserID")
  private UUID userID = null;

  @JsonProperty("FirstName")
  private String firstName = null;

  @JsonProperty("LastName")
  private String lastName = null;

  @JsonProperty("Email")
  private String email = null;

  /**
   * The role of the user.
   */
  public enum RoleEnum {
    USER("User"),
    
    EMPLOYEE("Employee");

    private String value;

    RoleEnum(String value) {
      this.value = value;
    }

    @Override
    @JsonValue
    public String toString() {
      return String.valueOf(value);
    }

    @JsonCreator
    public static RoleEnum fromValue(String text) {
      for (RoleEnum b : RoleEnum.values()) {
        if (String.valueOf(b.value).equals(text)) {
          return b;
        }
      }
      return null;
    }
  }
  @JsonProperty("Role")
  private RoleEnum role = null;

  @JsonProperty("Active")
  private Boolean active = null;

  @JsonProperty("Accounts")
  @Valid
  private List<Account> accounts = null;

  @JsonProperty("TransactionLimit")
  private Double transactionLimit = null;

  @JsonProperty("DailyLimit")
  private Double dailyLimit = null;

  public GetUserDTO userID(UUID userID) {
    this.userID = userID;
    return this;
  }

  /**
   * The unique identifier of the user.
   * @return userID
   **/
  @Schema(description = "The unique identifier of the user.")
  
    @Valid
    public UUID getUserID() {
    return userID;
  }

  public void setUserID(UUID userID) {
    this.userID = userID;
  }

  public GetUserDTO firstName(String firstName) {
    this.firstName = firstName;
    return this;
  }

  /**
   * The first name of the user.
   * @return firstName
   **/
  @Schema(description = "The first name of the user.")
  
    public String getFirstName() {
    return firstName;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public GetUserDTO lastName(String lastName) {
    this.lastName = lastName;
    return this;
  }

  /**
   * The last name of the user.
   * @return lastName
   **/
  @Schema(description = "The last name of the user.")
  
    public String getLastName() {
    return lastName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  public GetUserDTO email(String email) {
    this.email = email;
    return this;
  }

  /**
   * The email address of the user.
   * @return email
   **/
  @Schema(description = "The email address of the user.")
  
    public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public GetUserDTO role(RoleEnum role) {
    this.role = role;
    return this;
  }

  /**
   * The role of the user.
   * @return role
   **/
  @Schema(description = "The role of the user.")
  
    public RoleEnum getRole() {
    return role;
  }

  public void setRole(RoleEnum role) {
    this.role = role;
  }

  public GetUserDTO active(Boolean active) {
    this.active = active;
    return this;
  }

  /**
   * Indicates if the user is currently active.
   * @return active
   **/
  @Schema(description = "Indicates if the user is currently active.")
  
    public Boolean isActive() {
    return active;
  }

  public void setActive(Boolean active) {
    this.active = active;
  }

  public GetUserDTO accounts(List<Account> accounts) {
    this.accounts = accounts;
    return this;
  }

  public GetUserDTO addAccountsItem(Account accountsItem) {
    if (this.accounts == null) {
      this.accounts = new ArrayList<Account>();
    }
    this.accounts.add(accountsItem);
    return this;
  }

  /**
   * A list of accounts associated with the user.
   * @return accounts
   **/
  @Schema(description = "A list of accounts associated with the user.")
      @Valid
    public List<Account> getAccounts() {
    return accounts;
  }

  public void setAccounts(List<Account> accounts) {
    this.accounts = accounts;
  }

  public GetUserDTO transactionLimit(Double transactionLimit) {
    this.transactionLimit = transactionLimit;
    return this;
  }

  /**
   * The transaction limit for the user.
   * @return transactionLimit
   **/
  @Schema(description = "The transaction limit for the user.")
  
    public Double getTransactionLimit() {
    return transactionLimit;
  }

  public void setTransactionLimit(Double transactionLimit) {
    this.transactionLimit = transactionLimit;
  }

  public GetUserDTO dailyLimit(Double dailyLimit) {
    this.dailyLimit = dailyLimit;
    return this;
  }

  /**
   * The daily limit for the user.
   * @return dailyLimit
   **/
  @Schema(description = "The daily limit for the user.")
  
    public Double getDailyLimit() {
    return dailyLimit;
  }

  public void setDailyLimit(Double dailyLimit) {
    this.dailyLimit = dailyLimit;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    GetUserDTO getUserDTO = (GetUserDTO) o;
    return Objects.equals(this.userID, getUserDTO.userID) &&
        Objects.equals(this.firstName, getUserDTO.firstName) &&
        Objects.equals(this.lastName, getUserDTO.lastName) &&
        Objects.equals(this.email, getUserDTO.email) &&
        Objects.equals(this.role, getUserDTO.role) &&
        Objects.equals(this.active, getUserDTO.active) &&
        Objects.equals(this.accounts, getUserDTO.accounts) &&
        Objects.equals(this.transactionLimit, getUserDTO.transactionLimit) &&
        Objects.equals(this.dailyLimit, getUserDTO.dailyLimit);
  }

  @Override
  public int hashCode() {
    return Objects.hash(userID, firstName, lastName, email, role, active, accounts, transactionLimit, dailyLimit);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class GetUserDTO {\n");
    
    sb.append("    userID: ").append(toIndentedString(userID)).append("\n");
    sb.append("    firstName: ").append(toIndentedString(firstName)).append("\n");
    sb.append("    lastName: ").append(toIndentedString(lastName)).append("\n");
    sb.append("    email: ").append(toIndentedString(email)).append("\n");
    sb.append("    role: ").append(toIndentedString(role)).append("\n");
    sb.append("    active: ").append(toIndentedString(active)).append("\n");
    sb.append("    accounts: ").append(toIndentedString(accounts)).append("\n");
    sb.append("    transactionLimit: ").append(toIndentedString(transactionLimit)).append("\n");
    sb.append("    dailyLimit: ").append(toIndentedString(dailyLimit)).append("\n");
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

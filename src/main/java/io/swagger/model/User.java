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
 * User
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2023-05-16T13:11:00.686570329Z[GMT]")


public class User   {
  @JsonProperty("UserID")
  private UUID userID = null;

  @JsonProperty("FirstName")
  private String firstName = null;

  @JsonProperty("LastName")
  private String lastName = null;

  @JsonProperty("Email")
  private String email = null;

  @JsonProperty("Password")
  private String password = null;

  /**
   * Role of the user.
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

  public User userID(UUID userID) {
    this.userID = userID;
    return this;
  }

  /**
   * Unique identifier for the user.
   * @return userID
   **/
  @Schema(description = "Unique identifier for the user.")
  
    @Valid
    public UUID getUserID() {
    return userID;
  }

  public void setUserID(UUID userID) {
    this.userID = userID;
  }

  public User firstName(String firstName) {
    this.firstName = firstName;
    return this;
  }

  /**
   * First name of the user.
   * @return firstName
   **/
  @Schema(description = "First name of the user.")
  
    public String getFirstName() {
    return firstName;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public User lastName(String lastName) {
    this.lastName = lastName;
    return this;
  }

  /**
   * Last name of the user.
   * @return lastName
   **/
  @Schema(description = "Last name of the user.")
  
    public String getLastName() {
    return lastName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  public User email(String email) {
    this.email = email;
    return this;
  }

  /**
   * Email address of the user.
   * @return email
   **/
  @Schema(description = "Email address of the user.")
  
    public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public User password(String password) {
    this.password = password;
    return this;
  }

  /**
   * Password of the user.
   * @return password
   **/
  @Schema(description = "Password of the user.")
  
    public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public User role(RoleEnum role) {
    this.role = role;
    return this;
  }

  /**
   * Role of the user.
   * @return role
   **/
  @Schema(description = "Role of the user.")
  
    public RoleEnum getRole() {
    return role;
  }

  public void setRole(RoleEnum role) {
    this.role = role;
  }

  public User active(Boolean active) {
    this.active = active;
    return this;
  }

  /**
   * Indicates whether the user account is active or not.
   * @return active
   **/
  @Schema(description = "Indicates whether the user account is active or not.")
  
    public Boolean isActive() {
    return active;
  }

  public void setActive(Boolean active) {
    this.active = active;
  }

  public User accounts(List<Account> accounts) {
    this.accounts = accounts;
    return this;
  }

  public User addAccountsItem(Account accountsItem) {
    if (this.accounts == null) {
      this.accounts = new ArrayList<Account>();
    }
    this.accounts.add(accountsItem);
    return this;
  }

  /**
   * List of accounts associated with the user.
   * @return accounts
   **/
  @Schema(description = "List of accounts associated with the user.")
      @Valid
    public List<Account> getAccounts() {
    return accounts;
  }

  public void setAccounts(List<Account> accounts) {
    this.accounts = accounts;
  }

  public User transactionLimit(Double transactionLimit) {
    this.transactionLimit = transactionLimit;
    return this;
  }

  /**
   * Transaction limit for the user.
   * @return transactionLimit
   **/
  @Schema(description = "Transaction limit for the user.")
  
    public Double getTransactionLimit() {
    return transactionLimit;
  }

  public void setTransactionLimit(Double transactionLimit) {
    this.transactionLimit = transactionLimit;
  }

  public User dailyLimit(Double dailyLimit) {
    this.dailyLimit = dailyLimit;
    return this;
  }

  /**
   * Daily limit for the user.
   * @return dailyLimit
   **/
  @Schema(description = "Daily limit for the user.")
  
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
    User user = (User) o;
    return Objects.equals(this.userID, user.userID) &&
        Objects.equals(this.firstName, user.firstName) &&
        Objects.equals(this.lastName, user.lastName) &&
        Objects.equals(this.email, user.email) &&
        Objects.equals(this.password, user.password) &&
        Objects.equals(this.role, user.role) &&
        Objects.equals(this.active, user.active) &&
        Objects.equals(this.accounts, user.accounts) &&
        Objects.equals(this.transactionLimit, user.transactionLimit) &&
        Objects.equals(this.dailyLimit, user.dailyLimit);
  }

  @Override
  public int hashCode() {
    return Objects.hash(userID, firstName, lastName, email, password, role, active, accounts, transactionLimit, dailyLimit);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class User {\n");
    
    sb.append("    userID: ").append(toIndentedString(userID)).append("\n");
    sb.append("    firstName: ").append(toIndentedString(firstName)).append("\n");
    sb.append("    lastName: ").append(toIndentedString(lastName)).append("\n");
    sb.append("    email: ").append(toIndentedString(email)).append("\n");
    sb.append("    password: ").append(toIndentedString(password)).append("\n");
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

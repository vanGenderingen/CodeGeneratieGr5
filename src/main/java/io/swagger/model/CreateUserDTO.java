package io.swagger.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.swagger.v3.oas.annotations.media.Schema;
import java.math.BigDecimal;
import org.springframework.validation.annotation.Validated;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * CreateUserDTO
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2023-05-09T16:01:34.487688617Z[GMT]")


public class CreateUserDTO   {
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

  @JsonProperty("TransactionLimit")
  private BigDecimal transactionLimit = null;

  @JsonProperty("DailyLimit")
  private Double dailyLimit = null;

  public CreateUserDTO firstName(String firstName) {
    this.firstName = firstName;
    return this;
  }

  /**
   * First name of the user
   * @return firstName
   **/
  @Schema(description = "First name of the user")
  
    public String getFirstName() {
    return firstName;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public CreateUserDTO lastName(String lastName) {
    this.lastName = lastName;
    return this;
  }

  /**
   * Last name of the user
   * @return lastName
   **/
  @Schema(description = "Last name of the user")
  
    public String getLastName() {
    return lastName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  public CreateUserDTO email(String email) {
    this.email = email;
    return this;
  }

  /**
   * Email of the user
   * @return email
   **/
  @Schema(description = "Email of the user")
  
    public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public CreateUserDTO password(String password) {
    this.password = password;
    return this;
  }

  /**
   * Password of the user
   * @return password
   **/
  @Schema(description = "Password of the user")
  
    public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public CreateUserDTO role(RoleEnum role) {
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

  public CreateUserDTO transactionLimit(BigDecimal transactionLimit) {
    this.transactionLimit = transactionLimit;
    return this;
  }

  /**
   * Transaction limit for the user.
   * @return transactionLimit
   **/
  @Schema(description = "Transaction limit for the user.")
  
    @Valid
    public BigDecimal getTransactionLimit() {
    return transactionLimit;
  }

  public void setTransactionLimit(BigDecimal transactionLimit) {
    this.transactionLimit = transactionLimit;
  }

  public CreateUserDTO dailyLimit(Double dailyLimit) {
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
    CreateUserDTO createUserDTO = (CreateUserDTO) o;
    return Objects.equals(this.firstName, createUserDTO.firstName) &&
        Objects.equals(this.lastName, createUserDTO.lastName) &&
        Objects.equals(this.email, createUserDTO.email) &&
        Objects.equals(this.password, createUserDTO.password) &&
        Objects.equals(this.role, createUserDTO.role) &&
        Objects.equals(this.transactionLimit, createUserDTO.transactionLimit) &&
        Objects.equals(this.dailyLimit, createUserDTO.dailyLimit);
  }

  @Override
  public int hashCode() {
    return Objects.hash(firstName, lastName, email, password, role, transactionLimit, dailyLimit);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class CreateUserDTO {\n");
    
    sb.append("    firstName: ").append(toIndentedString(firstName)).append("\n");
    sb.append("    lastName: ").append(toIndentedString(lastName)).append("\n");
    sb.append("    email: ").append(toIndentedString(email)).append("\n");
    sb.append("    password: ").append(toIndentedString(password)).append("\n");
    sb.append("    role: ").append(toIndentedString(role)).append("\n");
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

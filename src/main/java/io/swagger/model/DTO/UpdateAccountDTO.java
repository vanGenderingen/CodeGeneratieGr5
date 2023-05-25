package io.swagger.model.DTO;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.validation.annotation.Validated;

/**
 * UpdateAccountDTO
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2023-05-16T13:11:00.686570329Z[GMT]")


public class UpdateAccountDTO   {
  @JsonProperty("Name")
  private String name = null;

  @JsonProperty("Balance")
  private Double balance = null;

  @JsonProperty("MinBal")
  private Double minBal = null;

  @JsonProperty("Active")
  private Boolean active = null;

  public UpdateAccountDTO name(String name) {
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

  public UpdateAccountDTO balance(Double balance) {
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

  public UpdateAccountDTO minBal(Double minBal) {
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

  public UpdateAccountDTO active(Boolean active) {
    this.active = active;
    return this;
  }

  /**
   * Indicates whether the account is active or not.
   * @return active
   **/
  @Schema(description = "Indicates whether the account is active or not.")
  
    public Boolean isActive() {
    return active;
  }

  public void setActive(Boolean active) {
    this.active = active;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    UpdateAccountDTO updateAccountDTO = (UpdateAccountDTO) o;
    return Objects.equals(this.name, updateAccountDTO.name) &&
        Objects.equals(this.balance, updateAccountDTO.balance) &&
        Objects.equals(this.minBal, updateAccountDTO.minBal) &&
        Objects.equals(this.active, updateAccountDTO.active);
  }

  @Override
  public int hashCode() {
    return Objects.hash(name, balance, minBal, active);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class UpdateAccountDTO {\n");
    
    sb.append("    name: ").append(toIndentedString(name)).append("\n");
    sb.append("    balance: ").append(toIndentedString(balance)).append("\n");
    sb.append("    minBal: ").append(toIndentedString(minBal)).append("\n");
    sb.append("    active: ").append(toIndentedString(active)).append("\n");
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

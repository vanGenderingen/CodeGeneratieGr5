package io.swagger.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.UUID;
import org.threeten.bp.OffsetDateTime;
import org.springframework.validation.annotation.Validated;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * CreateTransactionDTO
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2023-05-16T13:11:00.686570329Z[GMT]")


public class CreateTransactionDTO   {
  @JsonProperty("from")
  private String from = null;

  @JsonProperty("to")
  private String to = null;

  @JsonProperty("amount")
  private Double amount = null;

  /**
   * Gets or Sets transactionType
   */
  public enum TransactionTypeEnum {
    DEPOSIT("Deposit"),
    
    WITHDRAW("Withdraw");

    private String value;

    TransactionTypeEnum(String value) {
      this.value = value;
    }

    @Override
    @JsonValue
    public String toString() {
      return String.valueOf(value);
    }

    @JsonCreator
    public static TransactionTypeEnum fromValue(String text) {
      for (TransactionTypeEnum b : TransactionTypeEnum.values()) {
        if (String.valueOf(b.value).equals(text)) {
          return b;
        }
      }
      return null;
    }
  }
  @JsonProperty("transactionType")
  private TransactionTypeEnum transactionType = null;

  @JsonProperty("userPerforming")
  private UUID userPerforming = null;

  @JsonProperty("timeStamp")
  private OffsetDateTime timeStamp = null;

  @JsonProperty("description")
  private String description = null;

  public CreateTransactionDTO from(String from) {
    this.from = from;
    return this;
  }

  /**
   * International Bank Account Number (IBAN) associated with the account.
   * @return from
   **/
  @Schema(example = "NL01INHO0000000001", description = "International Bank Account Number (IBAN) associated with the account.")
  
    public String getFrom() {
    return from;
  }

  public void setFrom(String from) {
    this.from = from;
  }

  public CreateTransactionDTO to(String to) {
    this.to = to;
    return this;
  }

  /**
   * International Bank Account Number (IBAN) associated with the account.
   * @return to
   **/
  @Schema(example = "NL01INHO0000000001", description = "International Bank Account Number (IBAN) associated with the account.")
  
    public String getTo() {
    return to;
  }

  public void setTo(String to) {
    this.to = to;
  }

  public CreateTransactionDTO amount(Double amount) {
    this.amount = amount;
    return this;
  }

  /**
   * Get amount
   * @return amount
   **/
  @Schema(description = "")
  
    public Double getAmount() {
    return amount;
  }

  public void setAmount(Double amount) {
    this.amount = amount;
  }

  public CreateTransactionDTO transactionType(TransactionTypeEnum transactionType) {
    this.transactionType = transactionType;
    return this;
  }

  /**
   * Get transactionType
   * @return transactionType
   **/
  @Schema(description = "")
  
    public TransactionTypeEnum getTransactionType() {
    return transactionType;
  }

  public void setTransactionType(TransactionTypeEnum transactionType) {
    this.transactionType = transactionType;
  }

  public CreateTransactionDTO userPerforming(UUID userPerforming) {
    this.userPerforming = userPerforming;
    return this;
  }

  /**
   * Get userPerforming
   * @return userPerforming
   **/
  @Schema(description = "")
  
    @Valid
    public UUID getUserPerforming() {
    return userPerforming;
  }

  public void setUserPerforming(UUID userPerforming) {
    this.userPerforming = userPerforming;
  }

  public CreateTransactionDTO timeStamp(OffsetDateTime timeStamp) {
    this.timeStamp = timeStamp;
    return this;
  }

  /**
   * Get timeStamp
   * @return timeStamp
   **/
  @Schema(description = "")
  
    @Valid
    public OffsetDateTime getTimeStamp() {
    return timeStamp;
  }

  public void setTimeStamp(OffsetDateTime timeStamp) {
    this.timeStamp = timeStamp;
  }

  public CreateTransactionDTO description(String description) {
    this.description = description;
    return this;
  }

  /**
   * Get description
   * @return description
   **/
  @Schema(description = "")
  
    public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    CreateTransactionDTO createTransactionDTO = (CreateTransactionDTO) o;
    return Objects.equals(this.from, createTransactionDTO.from) &&
        Objects.equals(this.to, createTransactionDTO.to) &&
        Objects.equals(this.amount, createTransactionDTO.amount) &&
        Objects.equals(this.transactionType, createTransactionDTO.transactionType) &&
        Objects.equals(this.userPerforming, createTransactionDTO.userPerforming) &&
        Objects.equals(this.timeStamp, createTransactionDTO.timeStamp) &&
        Objects.equals(this.description, createTransactionDTO.description);
  }

  @Override
  public int hashCode() {
    return Objects.hash(from, to, amount, transactionType, userPerforming, timeStamp, description);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class CreateTransactionDTO {\n");
    
    sb.append("    from: ").append(toIndentedString(from)).append("\n");
    sb.append("    to: ").append(toIndentedString(to)).append("\n");
    sb.append("    amount: ").append(toIndentedString(amount)).append("\n");
    sb.append("    transactionType: ").append(toIndentedString(transactionType)).append("\n");
    sb.append("    userPerforming: ").append(toIndentedString(userPerforming)).append("\n");
    sb.append("    timeStamp: ").append(toIndentedString(timeStamp)).append("\n");
    sb.append("    description: ").append(toIndentedString(description)).append("\n");
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

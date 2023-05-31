package io.swagger.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springframework.validation.annotation.Validated;
import org.threeten.bp.OffsetDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.Valid;
import java.util.Objects;
import java.util.UUID;

/**
 * Transaction
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2023-05-16T13:11:00.686570329Z[GMT]")

@Data
@Entity
@Table(name = "TRANSACTION")
public class Transaction   {

  @Id
  @GeneratedValue
  @JsonProperty("transactionID")
  private UUID transactionID = null;

  @JsonProperty("fromIBAN")
  private String fromIBAN = null;

  @JsonProperty("toIBAN")
  private String toIBAN = null;

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

  public Transaction transactionID(UUID transactionID) {
    this.transactionID = transactionID;
    return this;
  }

  /**
   * Get transactionID
   * @return transactionID
   **/
  @Schema(description = "")
  
    @Valid
    public UUID getTransactionID() {
    return transactionID;
  }

  public void setTransactionID(UUID transactionID) {
    this.transactionID = transactionID;
  }

  public Transaction fromIBAN(String from) {
    this.fromIBAN = from;
    return this;
  }

  /**
   * International Bank Account Number (IBAN) associated with the account.
   * @return from
   **/
  @Schema(example = "NL01INHO0000000001", description = "International Bank Account Number (IBAN) associated with the account.")
  
    public String getfromIBAN() {
    return fromIBAN;
  }

  public void setfromIBAN(String from) {
    this.fromIBAN = from;
  }

  public Transaction toIBAN(String to) {
    this.toIBAN = to;
    return this;
  }

  /**
   * International Bank Account Number (IBAN) associated with the account.
   * @return to
   **/
  @Schema(example = "NL01INHO0000000001", description = "International Bank Account Number (IBAN) associated with the account.")
  
    public String getTo() {
    return toIBAN;
  }

  public void setTo(String to) {
    this.toIBAN = to;
  }

  public Transaction amount(Double amount) {
    this.amount = amount;
    return this;
  }

  /**
   * Get amount
   * @return amount
   **/
  @Schema(example = "123.12", description = "")
  
    public Double getAmount() {
    return amount;
  }

  public void setAmount(Double amount) {
    this.amount = amount;
  }

  public Transaction transactionType(TransactionTypeEnum transactionType) {
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

  public Transaction userPerforming(UUID userPerforming) {
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

  public Transaction timeStamp(OffsetDateTime timeStamp) {
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

  public Transaction description(String description) {
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
    Transaction transaction = (Transaction) o;
    return Objects.equals(this.transactionID, transaction.transactionID) &&
        Objects.equals(this.fromIBAN, transaction.fromIBAN) &&
        Objects.equals(this.toIBAN, transaction.toIBAN) &&
        Objects.equals(this.amount, transaction.amount) &&
        Objects.equals(this.transactionType, transaction.transactionType) &&
        Objects.equals(this.userPerforming, transaction.userPerforming) &&
        Objects.equals(this.timeStamp, transaction.timeStamp) &&
        Objects.equals(this.description, transaction.description);
  }

  @Override
  public int hashCode() {
    return Objects.hash(transactionID, fromIBAN, toIBAN, amount, transactionType, userPerforming, timeStamp, description);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Transaction {\n");
    
    sb.append("    transactionID: ").append(toIndentedString(transactionID)).append("\n");
    sb.append("    from: ").append(toIndentedString(fromIBAN)).append("\n");
    sb.append("    to: ").append(toIndentedString(toIBAN)).append("\n");
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

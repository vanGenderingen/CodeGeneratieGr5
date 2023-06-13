package io.swagger.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.*;
import org.springframework.validation.annotation.Validated;
import org.threeten.bp.OffsetDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import java.util.UUID;

/**
 * Transaction
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2023-05-16T13:11:00.686570329Z[GMT]")

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Transaction   {

  @Id
  @GeneratedValue
  @JsonProperty("transactionID")
  private UUID transactionID = UUID.randomUUID();

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
    
    WITHDRAWAL("Withdrawal");

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

  @PrePersist
  public void onCreate() {
    this.timeStamp = OffsetDateTime.now();
  }
}

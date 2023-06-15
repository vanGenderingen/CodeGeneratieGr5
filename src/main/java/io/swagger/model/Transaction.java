package io.swagger.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.validation.annotation.Validated;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.LocalDateTime;
import java.util.UUID;

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

  @JsonProperty("transactionType")
  private TransactionType transactionType = null;

  @JsonProperty("userPerforming")
  private UUID userPerforming = null;

  @JsonProperty("timeStamp")
  private LocalDateTime timeStamp = null;

  @JsonProperty("description")
  private String description = null;
/*
  @PrePersist
  public void onCreate() {this.timeStamp = LocalDateTime.now();}
  */
}

package io.swagger.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.validation.annotation.Validated;

import java.util.UUID;

/**
 * Account
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2023-05-16T13:11:00.686570329Z[GMT]")

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Account   {
  @Id
  @GeneratedValue
  @JsonProperty("AccountID")
  private UUID accountID = null;

  @ManyToOne
  @JsonProperty("User")
  private User user = null;

  @JsonProperty("UserID")
  private UUID userID;

  @JsonProperty("Name")
  private String name = null;

  @JsonProperty("IBAN")
  private String IBAN = null;

  @JsonProperty("Balance")
  private Double balance = null;

  @JsonProperty("Type")
  @Enumerated(EnumType.STRING)
  private AccountType type = null;

  @JsonProperty("MinBal")
  private Double minBal = null;

  @JsonProperty("Active")
  private Boolean active = true;

  @PrePersist
  public void onCreate() {
    this.active = true;
    // Initialize userID if user is not null
    if (user != null) {
      this.userID = user.getUserID();
    }
  }
}
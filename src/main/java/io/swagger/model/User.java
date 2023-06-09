package io.swagger.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.validation.annotation.Validated;

import java.util.List;
import java.util.UUID;

@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2023-05-16T13:11:00.686570329Z[GMT]")

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name="users")
public class User{
  @Id
  @GeneratedValue
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

  @ElementCollection(fetch = FetchType.EAGER)
  private List<Role> roles = null;

  @JsonProperty("Active")
  private Boolean active = null;

  @OneToMany(mappedBy = "user")
  @JsonIgnore
  private List<Account> accounts;

  @JsonProperty("TransactionLimit")
  private Double transactionLimit = null;

  @JsonProperty("DailyLimit")
  private Double dailyLimit = null;
}
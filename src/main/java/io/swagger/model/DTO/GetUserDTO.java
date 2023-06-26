package io.swagger.model.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.model.Account;
import io.swagger.model.Role;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.FetchType;
import lombok.Getter;
import lombok.Setter;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

/**
 * GetUserDTO
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2023-05-16T13:11:00.686570329Z[GMT]")

@Getter
@Setter
public class GetUserDTO   {
  @JsonProperty("UserID")
  private UUID userID = null;

  @JsonProperty("FirstName")
  private String firstName = null;

  @JsonProperty("LastName")
  private String lastName = null;

  @JsonProperty("Email")
  private String email = null;

  @ElementCollection(fetch = FetchType.EAGER)
  private List<Role> roles = null;

  @JsonProperty("Active")
  private Boolean active = null;

  @JsonProperty("Accounts")
  @Valid
  private List<Account> accounts = null;

  @JsonProperty("TransactionLimit")
  private Double transactionLimit = null;

  @JsonProperty("DailyLimit")
  private Double dailyLimit = null;

  @JsonProperty("LeftOverDailyLimit")
  private Double leftOverDailyLimit = null;
}

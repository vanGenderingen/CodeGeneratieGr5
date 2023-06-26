package io.swagger.model.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.model.Role;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.FetchType;
import lombok.Getter;
import lombok.Setter;
import org.springframework.validation.annotation.Validated;

import java.util.List;

/**
 * UpdateUserDTO
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2023-05-16T13:11:00.686570329Z[GMT]")

@Getter
@Setter
public class UpdateUserDTO   {
  @JsonProperty("firstName")
  private String firstName = null;

  @JsonProperty("lastName")
  private String lastName = null;

  @JsonProperty("email")
  private String email = null;

  @JsonProperty("password")
  private String password = null;

  @ElementCollection(fetch = FetchType.EAGER)
  private List<Role> roles = null;

  @JsonProperty("active")
  private Boolean active = null;

  @JsonProperty("transactionLimit")
  private Double transactionLimit = null;

  @JsonProperty("dailyLimit")
  private Double dailyLimit = null;

}

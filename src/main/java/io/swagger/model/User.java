package io.swagger.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.validation.annotation.Validated;

import javax.persistence.*;
import java.util.List;
import java.util.UUID;

/**
 * User
 */
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

  /**
   * Role of the user.
   */
//  public enum RoleEnum {
//    USER("User"),
//
//    EMPLOYEE("Employee");
//
//    private String value;
//
//    RoleEnum(String value) {
//      this.value = value;
//    }
//
//    @Override
//    @JsonValue
//    public String toString() {
//      return String.valueOf(value);
//    }
//
//    @JsonCreator
//    public static RoleEnum fromValue(String text) {
//      for (RoleEnum b : RoleEnum.values()) {
//        if (String.valueOf(b.value).equals(text)) {
//          return b;
//        }
//      }
//      return null;
//    }
//  }
//  @JsonProperty("Role")
//  private RoleEnum role = null;

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
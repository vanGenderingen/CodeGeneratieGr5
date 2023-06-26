package io.swagger.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;





@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "reset_tokens")
public class Token {
    @Id
    @JsonProperty("Email")
    private String email = null;

    @JsonProperty("Token")
    private String token = null;

}
package io.swagger.api.controllers;

//import com.sun.org.apache.xml.internal.security.algorithms.SignatureAlgorithm;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.SignatureAlgorithm;

import io.jsonwebtoken.Jwts;
import io.swagger.api.LoginApi;
import io.swagger.model.DTO.GetUserDTO;
import io.swagger.model.DTO.LoginDTO;
import io.swagger.model.DTO.LoginResponseDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Value;
import lombok.var;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Base64;
import java.util.Date;
import java.util.UUID;

import static javax.crypto.Cipher.SECRET_KEY;

@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2023-05-16T13:11:00.686570329Z[GMT]")
@RestController
public class LoginApiController implements LoginApi {

    private static final Logger log = LoggerFactory.getLogger(LoginApiController.class);

    private final ObjectMapper objectMapper;

    private final HttpServletRequest request;

    private static String secretKey = "yoursecretkey";

    @org.springframework.beans.factory.annotation.Autowired
    public LoginApiController(ObjectMapper objectMapper, HttpServletRequest request) {
        this.objectMapper = objectMapper;
        this.request = request;
    }

//    public ResponseEntity<LoginResponseDTO> loginPost(@Parameter(in = ParameterIn.DEFAULT, description = "", required=true, schema=@Schema()) @Valid @RequestBody LoginDTO body) {
//        String accept = request.getHeader("Accept");
//        if (accept != null && accept.contains("application/json")) {
//            try {
//                return new ResponseEntity<LoginResponseDTO>(objectMapper.readValue("{\n  \"token\" : \"eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiaWF0IjoxNTE2MjM5MDIyfQ.SflKxwRJSMeKKF2QT4fwpMeJf36POk6yJV_adQssw5c\"\n}", LoginResponseDTO.class), HttpStatus.NOT_IMPLEMENTED);
//            } catch (IOException e) {
//                log.error("Couldn't serialize response for content type application/json", e);
//                return new ResponseEntity<LoginResponseDTO>(HttpStatus.INTERNAL_SERVER_ERROR);
//            }
//        }
//
//        return new ResponseEntity<LoginResponseDTO>(HttpStatus.NOT_IMPLEMENTED);
//    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> loginPost(@Parameter(in = ParameterIn.DEFAULT, description = "", required=true, schema=@Schema()) @Valid @RequestBody LoginDTO body) {
        String accept = request.getHeader("Accept");
        if (accept != null && accept.contains("application/json")) {
            try {
                // Build JWT token
                String token = generateJwtToken(LoginDTO.getEmail(), GetUserDTO.RoleEnum.USER);
                LoginResponseDTO responseDTO = new LoginResponseDTO();
                responseDTO.setToken(token);

                return new ResponseEntity<>(responseDTO, HttpStatus.OK);
            } catch (Exception e) {
                log.error("Failed to generate JWT token", e);
                return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }

        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);

    }
    protected void init() {
        secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
    }
    private static String generateJwtToken(String userId, GetUserDTO.RoleEnum role) {
        long currentTimeMillis = System.currentTimeMillis();
        long expirationTimeMillis = currentTimeMillis + 3600000; // Token expiration time: 1 hour
        Date expirationDate = new Date(expirationTimeMillis);

        return Jwts.builder()
                .setSubject(userId.toString()) // User ID
                .claim("role", role) // Custom claim: user role
                .setExpiration(expirationDate) // Expiration time
                .signWith(SignatureAlgorithm.HS256, secretKey) // Sign the JWT with the secret key
                .compact();

    }
    public boolean ValidateJwtToken(String jwtToken) {
        try {
            Jwts.parser().setSigningKey(secretKey).parseClaimsJws(jwtToken);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Expired or invalid JWT token");
        }
    }
}

package io.swagger.api.controllers;

//import com.sun.org.apache.xml.internal.security.algorithms.SignatureAlgorithm;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.swagger.api.LoginApi;
import io.swagger.api.service.UserService;
import io.swagger.model.DTO.LoginDTO;
import io.swagger.model.DTO.LoginResponseDTO;
import io.swagger.model.User;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Schema;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Base64;
import java.util.Date;

@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2023-05-16T13:11:00.686570329Z[GMT]")
@RestController
public class LoginApiController implements LoginApi {

    private static final Logger log = LoggerFactory.getLogger(LoginApiController.class);

    private final ObjectMapper objectMapper;

    private final HttpServletRequest request;

    private static String secretKey = "ditihsdiuhiashiudphusaihudhaspdihasudhipuahdhasudisadahspiu";

    @Autowired
    private UserService userService;

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

    @RequestMapping(value = "/login", produces = {"application/json"}, method = RequestMethod.POST)
    public ResponseEntity<LoginResponseDTO> loginPost(@Parameter(in = ParameterIn.DEFAULT, description = "", required=true, schema=@Schema()) @Valid @RequestBody LoginDTO body) {
            try {

                // login
                String email = body.getEmail();
                String password = body.getPassword();

                User user = userService.login(email, password);
                if (user != null) {
                    String token = generateJwtToken(user);
                    LoginResponseDTO responseDTO = new LoginResponseDTO(token);
                    return ResponseEntity.ok(responseDTO);
                }
                else{
                    return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
                }
//                 String token = userService.login(body.getEmail(), body.getPassword());
//                 LoginResponseDTO loginResponse = new LoginResponseDTO();
//                 loginResponse.setToken(token);
//                return new ResponseEntity<>(HttpStatus.OK);

            } catch (Exception e) {
                log.error("Failed to generate JWT token", e);
                return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
    }
    protected void init() {
        secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
    }
    public static String generateJwtToken(User user) {
        long currentTimeMillis = System.currentTimeMillis();
        long expirationTimeMillis = currentTimeMillis + 3600000; // Token expiration time: 1 hour
        Date expirationDate = new Date(expirationTimeMillis);

        return Jwts.builder()
                .setSubject(user.getUserID().toString()) // User ID
                .claim("auth", user.getRole()) // Custom claim: user role
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

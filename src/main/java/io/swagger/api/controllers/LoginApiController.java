package io.swagger.api.controllers;

//import com.sun.org.apache.xml.internal.security.algorithms.SignatureAlgorithm;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.api.LoginApi;
import io.swagger.api.service.EmailService;
import io.swagger.api.service.LoginService;
import io.swagger.api.service.TokenService;
import io.swagger.api.service.UserService;
import io.swagger.model.DTO.LoginDTO;
import io.swagger.model.DTO.LoginResponseDTO;
import io.swagger.model.DTO.UpdateUserDTO;
import io.swagger.model.Token;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.MailException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@CrossOrigin(origins = "*")
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2023-05-16T13:11:00.686570329Z[GMT]")
@RestController
public class LoginApiController implements LoginApi {

    private static final Logger log = LoggerFactory.getLogger(LoginApiController.class);//Logger to display messages on the console

    private final ObjectMapper objectMapper;//ObjectMapper to map objects

    private String email;//String to store the email

    private final HttpServletRequest request;//HttpServletRequest to get the request

    private final Map<String, String> tokenEmailMap = new HashMap<>();//Map to store the token and the email

    @Autowired
    private LoginService loginService;//LoginService to use the methods of the service

    @Autowired
    private UserService userService;//LoginService to use the methods of the service
    @Autowired
    private EmailService emailService;//LoginService to use the methods of the service
    @Autowired
    private TokenService tokenService;//LoginService to use the methods of the service
//    @Autowired
//    private TokenRepository tokenRepository;

    @org.springframework.beans.factory.annotation.Autowired
    public LoginApiController(ObjectMapper objectMapper, HttpServletRequest request, LoginService loginService){
        this.loginService = loginService;//Initialize the LoginService
        this.objectMapper = objectMapper;//Initialize the ObjectMapper and the HttpServletRequest
        this.request = request;
    }

    @RequestMapping(value = "/login", produces = {"application/json"}, method = RequestMethod.POST)
    public ResponseEntity<LoginResponseDTO> loginPost(@Parameter(in = ParameterIn.DEFAULT, description = "", required=true, schema=@Schema()) @Valid @RequestBody LoginDTO body) {
        String token = loginService.login(body.getEmail(), body.getPassword());//Get the Email and the Password from the body and call the login method of the LoginService
        LoginResponseDTO loginResponse = new LoginResponseDTO();
        loginResponse.setToken(token);//Set the token in the LoginResponseDTO

        return new ResponseEntity<LoginResponseDTO>(loginResponse, HttpStatus.OK);
    }


    //@PostMapping("/forgot-password")
    @RequestMapping(value = "/forgot-password", produces = {"application/json"}, method = RequestMethod.POST)
    public ResponseEntity<?> forgotPassword(@RequestBody Map<String, String> request) {
        String email = request.get("email");
        // Generate a unique token for the user and store it in the database
        String token = UUID.randomUUID().toString();
        //final Map<String, String> tokenEmailMap = new HashMap<>();
        //tokenEmailMap.put(token, email);
        //Timestamp timestamp = new Timestamp(Date.now();
        Token token1 = new Token();
        token1.setEmail(email);
        token1.setToken(token);
        tokenService.saveToken(token1);
        // Save the token, email, and timestamp in the database
        String resetUrl = "http://localhost:5173/reset-password?token=" + token;
        String subject = "Reset Your Password";
        String body = "Click the following link to reset your password: " + resetUrl;
        try {
            emailService.sendEmail(email, subject, body);
            //return ResponseEntity.ok(Map.of("message", "Email sent successfully"));
            return new ResponseEntity<LoginResponseDTO>(HttpStatus.OK);
        } catch (MailException e) {
           // return ResponseEntity.badRequest().body(Map.of("message", "Failed to send email"));

            return new ResponseEntity<LoginResponseDTO>(HttpStatus.INTERNAL_SERVER_ERROR);
        } /*catch (MessagingException e) {
            throw new RuntimeException(e);
        }*/ catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }
    @PostMapping("/reset-password")
    public ResponseEntity<?> resetPassword(@RequestBody Map<String, String> request) {
        String newPassword = request.get("newPassword");
        String confirmPassword = request.get("confirmPassword");
        String Email = request.get("email");
        Token token = tokenService.findByEmail(Email);
        String tokenfromDB = token.getToken();
        String tokenFromRequest = request.get("token");
        if (!newPassword.equals(confirmPassword) || !tokenfromDB.equals(tokenFromRequest)){
            //return ResponseEntity.badRequest().body(Map.of("message", "Passwords do not match"));
            return new ResponseEntity<LoginResponseDTO>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        //TODO Fix this so user passwords can be updated
        //Problem is in the service, no Method for it

        //userService.updateUser(Email, newPassword);

        //return ResponseEntity.ok(Map.of("message", "Password reset successfully"));
        return new ResponseEntity<LoginResponseDTO>(HttpStatus.OK);
    }
}

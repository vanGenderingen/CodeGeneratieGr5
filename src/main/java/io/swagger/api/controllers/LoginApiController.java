package io.swagger.api.controllers;

//import com.sun.org.apache.xml.internal.security.algorithms.SignatureAlgorithm;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.api.interfaces.LoginApi;
import io.swagger.api.service.LoginService;
import io.swagger.model.DTO.LoginDTO;
import io.swagger.model.DTO.LoginResponseDTO;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Schema;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@CrossOrigin(origins = "*")
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2023-05-16T13:11:00.686570329Z[GMT]")
@RestController
public class LoginApiController implements LoginApi {

    private static final Logger log = LoggerFactory.getLogger(LoginApiController.class);//Logger to display messages on the console

    private final ObjectMapper objectMapper;//ObjectMapper to map objects

    private final HttpServletRequest request;//HttpServletRequest to get the request

    @Autowired
    private LoginService loginService;//LoginService to use the methods of the service

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
}

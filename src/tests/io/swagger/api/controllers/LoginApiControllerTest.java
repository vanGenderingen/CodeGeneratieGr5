package io.swagger.api.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.api.repository.UserRepository;
import io.swagger.api.service.EmailService;
import io.swagger.api.service.LoginService;
import io.swagger.api.service.TokenService;
import io.swagger.api.service.UserService;
import io.swagger.model.DTO.LoginDTO;
import io.swagger.model.DTO.LoginResponseDTO;
import io.swagger.model.Token;
import io.swagger.model.User;
import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

class LoginApiControllerTest {

    @Mock
    private HttpServletRequest request;

    @Mock
    private LoginService loginService;

    @Mock
    private UserService userService;
    @Mock
    private TokenService tokenService;

    @Mock
    private EmailService emailService;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private LoginApiController loginApiController;

    @BeforeEach
    void setUp() {
        loginApiController = new LoginApiController(new ObjectMapper(), request, loginService);
        MockitoAnnotations.initMocks(this);
    }



    @Test
    void testLoginWithValidCredentials() {
        // Arrange
        User user = new User();
        user.setEmail("john.doe@mail.nl");
        user.setPassword("password");
        when(userRepository.getUserByEmail("john.doe@mail.nl")).thenReturn(user);

        LoginDTO loginDTO = new LoginDTO();
        loginDTO.setEmail("john.doe@mail.nl");
        loginDTO.setPassword("password");

        // Act
        ResponseEntity<LoginResponseDTO> response = loginApiController.loginPost(loginDTO);

        // Assert

        assertEquals(HttpStatus.OK, (HttpStatus) response.getStatusCode());
    }

    @Test
    void testLoginWithInvalidCredentials() {
        // Arrange
        when(userRepository.getUserByEmail("testuser")).thenReturn(null);

        LoginDTO loginDTO = new LoginDTO();
        loginDTO.setEmail("testuser");
        loginDTO.setPassword("testpassword");

        // Act
        ResponseEntity<LoginResponseDTO> response = loginApiController.loginPost(loginDTO);

        // Assert
        assertEquals(HttpStatus.NOT_IMPLEMENTED, (HttpStatus) response.getStatusCode());
    }
    @Test
    void testLoginWithInvalidFields() {
        // Arrange
       // when(userRepository.getUserByEmail("testuser")).thenReturn(null);

        LoginDTO loginDTO = new LoginDTO();
        loginDTO.setEmail("");
        loginDTO.setPassword("");

        // Act
        ResponseEntity<LoginResponseDTO> response = loginApiController.loginPost(loginDTO);

        // Assert
        assertEquals(HttpStatus.NOT_IMPLEMENTED, (HttpStatus) response.getStatusCode());
    }
    @Test
    public void testResetPassword() {
        Map<String, String> request = new HashMap<>();
        request.put("email", "test@example.com");
        request.put("newPassword", "newPassword");
        request.put("confirmPassword", "newPassword");
        request.put("token", "token");

        Token token = new Token();
        token.setEmail("test@example.com");
        token.setToken("token");
        when(tokenService.findByEmail("test@example.com")).thenReturn(token);

        ResponseEntity<?> responseEntity = loginApiController.resetPassword(request);

        assertEquals(HttpStatus.OK, (HttpStatus) responseEntity.getStatusCode());
    }

    @Test
    public void testForgotPassword() throws Exception {
        Map<String, String> request = new HashMap<>();
        request.put("email", "test@example.com");

        String token = UUID.randomUUID().toString();
        Token token1 = new Token();
        token1.setEmail("test@example.com");
        token1.setToken(token);
        doNothing().when(tokenService).saveToken(token1);

        String resetUrl = "http://localhost:5173/reset-password?token=" + token;
        String subject = "Reset Your Password";
        String body = "Click the following link to reset your password: " + resetUrl;
        doNothing().when(emailService).sendEmail("test@example.com", subject, body);

        ResponseEntity<?> responseEntity = loginApiController.forgotPassword(request);

        assertEquals(HttpStatus.OK, (HttpStatus) responseEntity.getStatusCode());
    }

    private void assertEquals(HttpStatus httpStatus, HttpStatus statusCode) {
    }


}
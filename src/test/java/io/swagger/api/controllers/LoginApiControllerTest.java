package io.swagger.api.controllers;

import io.swagger.model.DTO.LoginDTO;
import io.swagger.model.DTO.LoginResponseDTO;
import io.swagger.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

class LoginApiControllerTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private LoginApiController loginApiController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testLoginWithValidCredentials() {
        // Arrange
        User user = new User();
        user.setUsername("testuser");
        user.setPassword("testpassword");
        when(userRepository.findByUsername("testuser")).thenReturn(user);

        LoginDTO loginDTO = new LoginDTO();
        loginDTO.setUsername("testuser");
        loginDTO.setPassword("testpassword");

        // Act
        ResponseEntity<LoginResponseDTO> response = loginApiController.loginPost(loginDTO);

        // Assert
        assertEquals(HttpStatus.NOT_IMPLEMENTED, response.getStatusCode());
    }

    @Test
    void testLoginWithInvalidCredentials() {
        // Arrange
        when(userRepository.findByUsername("testuser")).thenReturn(null);

        LoginDTO loginDTO = new LoginDTO();
        loginDTO.setUsername("testuser");
        loginDTO.setPassword("testpassword");

        // Act
        ResponseEntity<LoginResponseDTO> response = loginApiController.loginPost(loginDTO);

        // Assert
        assertEquals(HttpStatus.NOT_IMPLEMENTED, response.getStatusCode());
    }


    @Test
    void testGenerateJwtToken() {
        User user = TestDataUtil.createTestUser();

        String jwtToken = TestJwtUtil.generateJwtToken(user);

        boolean isValid = TestJwtUtil.validateJwtToken(jwtToken);

        assertEquals(true, isValid);
    }
}
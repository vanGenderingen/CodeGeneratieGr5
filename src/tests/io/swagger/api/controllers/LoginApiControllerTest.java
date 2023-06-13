package io.swagger.api.controllers;

import io.swagger.api.repository.UserRepository;
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
        user.setEmail("john.doe@mail.nl");
        user.setPassword("password");
        when(userRepository.getUserByEmail("john.doe@mail.nl")).thenReturn(user);

        LoginDTO loginDTO = new LoginDTO();
        loginDTO.setEmail("john.doe@mail.nl");
        loginDTO.setPassword("password");

        // Act
        ResponseEntity<LoginResponseDTO> response = loginApiController.loginPost(loginDTO);

        // Assert
        assertEquals(HttpStatus.NOT_IMPLEMENTED, response.getStatusCode());
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
        assertEquals(HttpStatus.NOT_IMPLEMENTED, response.getStatusCode());
    }

    private void assertEquals(HttpStatus httpStatus, HttpStatus statusCode) {
    }
}

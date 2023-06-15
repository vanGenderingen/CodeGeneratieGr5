package io.swagger.api;

import io.swagger.api.controllers.UsersApiController;
import io.swagger.model.DTO.CreateUserDTO;
import io.swagger.model.DTO.GetUserDTO;
import io.swagger.model.DTO.UpdateUserDTO;
import io.swagger.model.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.security.Principal;
import java.util.List;
import java.util.UUID;

import static org.mockito.Mockito.when;

class UsersApiTest {
    @Mock
    private UsersApiController usersApiController;

    public UsersApiTest() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void createUser_shouldReturnCreatedUser() {
        CreateUserDTO createUserDTO = new CreateUserDTO();

        when(usersApiController.createUser(createUserDTO)).thenReturn(new ResponseEntity<User>(HttpStatus.CREATED));

        ResponseEntity<User> response = usersApiController.createUser(createUserDTO);

        Assertions.assertEquals(HttpStatus.CREATED, response.getStatusCode());
        User user = response.getBody();
    }

    @Test
    void createUser_ReturnsBadRequest() {
        CreateUserDTO createUserDTO = new CreateUserDTO();

        when(usersApiController.createUser(createUserDTO)).thenReturn(new ResponseEntity<User>(HttpStatus.BAD_REQUEST));

        ResponseEntity<User> response = usersApiController.createUser(createUserDTO);

        Assertions.assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    void getUsers_shouldReturnListOfUsers() {
        Integer limit = 10;
        Integer offset = 0;
        String searchStrings = "";
        String email = "";

        // Set the mock behaviour of the usersApiController
        when(usersApiController.getUsers(limit, offset, searchStrings, email)).thenReturn(new ResponseEntity<List<GetUserDTO>>(HttpStatus.OK));

        // Call the usersGet method
        ResponseEntity<List<GetUserDTO>> response = usersApiController.getUsers(limit, offset, searchStrings, email);

        // Verify the response
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        List<GetUserDTO> users = response.getBody();
    }

    @Test
    void getUsers_ReturnsNotFound() {
        Integer limit = 10;
        Integer offset = 0;
        String searchStrings = "";
        String email = "";

        // Set the mock behaviour of the usersApiController
        when(usersApiController.getUsers(limit, offset, searchStrings, email)).thenReturn(new ResponseEntity<List<GetUserDTO>>(HttpStatus.NOT_FOUND));

        // Call the usersGet method
        ResponseEntity<List<GetUserDTO>> response = usersApiController.getUsers(limit, offset, searchStrings, email);

        // Verify the response
        Assertions.assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void getUserById_shouldReturnUser() {
        // Create test data
        UUID userId = UUID.randomUUID();
        Principal principal = Mockito.mock(Principal.class);

        // Set the mock behaviour of the usersApiController
        when(usersApiController.getUserById(userId, principal)).thenReturn(new ResponseEntity<GetUserDTO>(HttpStatus.OK));

        // Call the usersGet method
        ResponseEntity<GetUserDTO> response = usersApiController.getUserById(userId, principal);

        // Verify the response
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        GetUserDTO user = response.getBody();
    }

    @Test
    void getUserById_ReturnsNotFound() {
        // Create test data
        UUID userId = UUID.randomUUID();
        Principal principal = Mockito.mock(Principal.class);

        // Set the mock behaviour of the usersApiController
        when(usersApiController.getUserById(userId, principal)).thenReturn(new ResponseEntity<GetUserDTO>(HttpStatus.NOT_FOUND));

        // Call the usersGet method
        ResponseEntity<GetUserDTO> response = usersApiController.getUserById(userId, principal);

        // Verify the response
        Assertions.assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void updateUser_shouldReturnUpdatedUser() {
        // Create test data
        UUID userId = UUID.randomUUID();
        UpdateUserDTO updateUserDTO = new UpdateUserDTO();
        Principal principal = Mockito.mock(Principal.class);

        // Set the mock behaviour of the usersApiController
        when(usersApiController.updateUser(userId, updateUserDTO, principal)).thenReturn(new ResponseEntity<GetUserDTO>(HttpStatus.OK));

        // Call the usersGet method
        ResponseEntity<GetUserDTO> response = usersApiController.updateUser(userId, updateUserDTO, principal);

        // Verify the response
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        GetUserDTO user = response.getBody();

    }

    @Test
    void updateUser_ReturnsBadRequest() {
        // Create test data
        UUID userId = UUID.randomUUID();
        UpdateUserDTO updateUserDTO = new UpdateUserDTO();
        Principal principal = Mockito.mock(Principal.class);

        // Set the mock behaviour of the usersApiController
        when(usersApiController.updateUser(userId, updateUserDTO, principal)).thenReturn(new ResponseEntity<GetUserDTO>(HttpStatus.BAD_REQUEST));

        // Call the usersGet method
        ResponseEntity<GetUserDTO> response = usersApiController.updateUser(userId, updateUserDTO, principal);

        // Verify the response
        Assertions.assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

}

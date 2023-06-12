package io.swagger.api;

import io.swagger.api.controllers.UsersApiController;
import io.swagger.api.service.UserService;
import io.swagger.model.DTO.CreateUserDTO;
import io.swagger.model.DTO.GetUserDTO;
import io.swagger.model.DTO.UpdateUserDTO;
import io.swagger.model.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@ExtendWith(MockitoExtension.class)
public class UsersApiTest {

    @Mock
    private UserService userService;

    @InjectMocks
    private UsersApiController usersApiController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testUsersGet() {
        // Create test data
        List<GetUserDTO> users = new ArrayList<>();
        users.add(new GetUserDTO());
        users.add(new GetUserDTO());

        // Mock the behavior of the usersGet() method
        Mockito.when(userService.getAllUsers(null, null, null, null))
                .thenReturn(ResponseEntity.ok(users));

        // Perform the usersGet() method invocation
        ResponseEntity<List<GetUserDTO>> response = usersApiController.usersGet(null, null, null, null);

        // Verify the results
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assertions.assertEquals(users, response.getBody());
    }

    @Test
    public void testUsersPost() {
        // Create test data
        CreateUserDTO createUserDTO = new CreateUserDTO();
        User user = new User();

        // Mock the behavior of the usersPost() method
        Mockito.when(userService.add(createUserDTO))
                .thenReturn(ResponseEntity.status(HttpStatus.CREATED).body(user));

        // Perform the usersPost() method invocation
        ResponseEntity<User> response = usersApiController.usersPost(createUserDTO);

        // Verify the results
        Assertions.assertEquals(HttpStatus.CREATED, response.getStatusCode());
        Assertions.assertEquals(user, response.getBody());
    }

    @Test
    public void testUsersUserIDGet() {
        // Create test data
        UUID userID = UUID.randomUUID();
        GetUserDTO getUserDTO = new GetUserDTO();

        // Mock the behavior of the usersUserIDGet() method
        Mockito.when(userService.getUserByUserID(userID))
                .thenReturn(ResponseEntity.ok(getUserDTO));

        // Perform the usersUserIDGet() method invocation
        ResponseEntity<GetUserDTO> response = usersApiController.usersUserIDGet(userID);

        // Verify the results
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assertions.assertEquals(getUserDTO, response.getBody());
    }

    @Test
    public void testUsersUserIDPut() {
        // Create test data
        UUID userID = UUID.randomUUID();
        UpdateUserDTO updateUserDTO = new UpdateUserDTO();
        GetUserDTO getUserDTO = new GetUserDTO();

        // Mock the behavior of the usersUserIDPut() method
        Mockito.when(userService.updateUser(userID, updateUserDTO))
                .thenReturn(ResponseEntity.ok(getUserDTO));

        // Perform the usersUserIDPut() method invocation
        ResponseEntity<GetUserDTO> response = usersApiController.usersUserIDPut(userID, updateUserDTO);

        // Verify the results
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assertions.assertEquals(getUserDTO, response.getBody());
    }
}

package io.swagger.api;

import io.swagger.api.service.UserService;
import io.swagger.model.DTO.CreateUserDTO;
import io.swagger.model.DTO.GetUserDTO;
import io.swagger.model.DTO.UpdateUserDTO;
import io.swagger.model.User;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.security.Principal;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

class UsersApiTest {

    @Mock
    private UserService userService;

    @InjectMocks
    private UsersApiImpl usersApi;

    public UsersApiTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createUser_shouldReturnCreatedUser() {
        CreateUserDTO createUserDTO = new CreateUserDTO();
        User createdUser = new User();
        ResponseEntity<User> expectedResponse = ResponseEntity.status(HttpStatus.CREATED).body(createdUser);

        when(userService.add(createUserDTO)).thenReturn(createdUser);

        ResponseEntity<User> response = usersApi.createUser(createUserDTO);

        assertEquals(expectedResponse, response);
    }

    @Test
    void getUsers_shouldReturnListOfUsers() {
        Integer limit = 10;
        Integer offset = 0;
        String searchStrings = "search";
        String email = "example@example.com";
        List<GetUserDTO> users = Collections.singletonList(new GetUserDTO());
        ResponseEntity<List<GetUserDTO>> expectedResponse = ResponseEntity.ok(users);

        when(userService.getAllUsers(limit, offset, searchStrings, email)).thenReturn(users);

        ResponseEntity<List<GetUserDTO>> response = usersApi.getUsers(limit, offset, searchStrings, email);

        assertEquals(expectedResponse, response);
    }

    @Test
    void getUserById_shouldReturnUser() {
        UUID userId = UUID.randomUUID();
        GetUserDTO user = new GetUserDTO();
        Principal principal = () -> "username";
        ResponseEntity<GetUserDTO> expectedResponse = ResponseEntity.ok(user);

        when(userService.getUserByUserID(userId)).thenReturn(user);

        ResponseEntity<GetUserDTO> response = usersApi.getUserById(userId, principal);

        assertEquals(expectedResponse, response);
    }

    @Test
    void updateUser_shouldReturnUpdatedUser() {
        UUID userId = UUID.randomUUID();
        UpdateUserDTO updateUserDTO = new UpdateUserDTO();
        GetUserDTO updatedUser = new GetUserDTO();
        Principal principal = () -> "username";
        ResponseEntity<GetUserDTO> expectedResponse = ResponseEntity.ok(updatedUser);

        when(userService.updateUser(userId, updateUserDTO)).thenReturn(updatedUser);

        ResponseEntity<GetUserDTO> response = usersApi.(userId, updateUserDTO, principal);

        assertEquals(expectedResponse, response);
    }
}

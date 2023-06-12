package io.swagger.api.controllers;


import io.swagger.api.service.UserService;
import io.swagger.model.DTO.CreateUserDTO;
import io.swagger.model.DTO.GetUserDTO;
import io.swagger.model.DTO.UpdateUserDTO;
import io.swagger.model.Role;
import io.swagger.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UsersApiControllerTest {

    @Mock
    private UserService userService;

    @InjectMocks
    private UsersApiController usersApiController;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);

    }

    @Test
    void testUsersPost() {


        User user = new User(UUID.fromString("bb0cc36d-69a7-471e-a665-3609bc14c27a"), "Test", "Tester", "mail@example.com", "password", Arrays.asList(Role.ROLE_USER), true, new ArrayList<>(), 1000.00, 10000.00);

        CreateUserDTO createUserDTO = new CreateUserDTO("Test", "Tester", "mail@example.com", "password", Arrays.asList(Role.ROLE_USER), 1000.00, 10000.00);

        when(userService.add(any(CreateUserDTO.class))).thenReturn(new ResponseEntity<>(user, HttpStatus.OK));

        ResponseEntity<User> response = usersApiController.usersPost(createUserDTO);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(user);

        verify(userService).add(any(CreateUserDTO.class));
    }
    @Test
    void testUsersGet() {
        int limit = 10;
        int offset = 0;
        String searchstrings = null;
        String Email = null;

        UUID userID = UUID.randomUUID();
        GetUserDTO userDTO = new GetUserDTO();
        userDTO.setUserID(userID);

        List<GetUserDTO> userDTOS = new ArrayList<>();
        userDTOS.add(userDTO);

        when(userService.getAllUsers(limit, offset, searchstrings, Email)).thenReturn(new ResponseEntity<>(userDTOS, HttpStatus.OK));

        ResponseEntity<List<GetUserDTO>> responseEntity = usersApiController.usersGet(limit, offset,
                searchstrings, Email);

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody()).hasSize(1);
        assertThat(responseEntity.getBody().get(0).getUserID()).isEqualTo(userDTO.getUserID());

        verify(userService, times(1)).getAllUsers(limit, offset, searchstrings, Email);
    }

    @Test
    void testUsersUserIdGet() {
        UUID userID = UUID.randomUUID();
        GetUserDTO userDTO = new GetUserDTO();
        userDTO.setUserID(userID);

        when(userService.getUserByUserID(userID)).thenReturn(new ResponseEntity<>(userDTO, HttpStatus.OK));

        ResponseEntity<GetUserDTO> responseEntity = usersApiController.usersUserIDGet(userID);

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody()).isEqualTo(userDTO);

        verify(userService, times(1)).getUserByUserID(userID);
    }

    @Test
    void testUsersUserIdPut() {
        UUID userID = UUID.randomUUID();
        UpdateUserDTO userDTO = new UpdateUserDTO();
        userDTO.setFirstName("test");

        GetUserDTO getUserDTO = new GetUserDTO();
        getUserDTO.setUserID(userID);

        when(userService.updateUser(userID, userDTO)).thenReturn(new ResponseEntity<GetUserDTO>(getUserDTO, HttpStatus.OK));

        ResponseEntity<GetUserDTO> responseEntity = usersApiController.usersUserIDPut(userID, userDTO);

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody()).isInstanceOf(GetUserDTO.class);

        verify(userService, times(1)).updateUser(userID, userDTO);
    }
}

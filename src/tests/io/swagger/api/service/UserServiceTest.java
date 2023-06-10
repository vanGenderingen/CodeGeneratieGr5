package io.swagger.api.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.api.repository.UserRepository;
import io.swagger.model.DTO.CreateUserDTO;
import io.swagger.model.DTO.GetUserDTO;
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
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private ObjectMapper objectMapper;

    @InjectMocks
    private UserService userService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testUserAdd() {
        User user = new User(UUID.fromString("bb0cc36d-69a7-471e-a665-3609bc14c27a"), "Test", "Tester", "mail@example.com", "password", Arrays.asList(Role.ROLE_USER), true, new ArrayList<>(), 1000.00, 10000.00);

        CreateUserDTO createUserDTO = new CreateUserDTO("Test", "Tester", "mail@example.com", "password", Arrays.asList(Role.ROLE_USER), 1000.00, 10000.00);

        when(objectMapper.convertValue(any(CreateUserDTO.class), eq(User.class))).thenReturn(user);
        when(userRepository.save(any(User.class))).thenReturn(user);

        ResponseEntity<User> response = userService.add(createUserDTO);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(user);

        verify(userRepository).save(any(User.class));
    }

    @Test
    void testGetAllUsers() {
        int limit = 10;
        int offset = 0;
        String searchStrings = null;
        String Email = null;

        UUID userId = UUID.randomUUID();
        GetUserDTO userDTO = new GetUserDTO();
        userDTO.setUserID(userId);

        List<User> users = new ArrayList<>();
        User user = new User();
        user.setUserID(userId);
        users.add(user);

        when(userRepository.getAll(anyString(), anyString(), any())).thenReturn(users);
        when(objectMapper.convertValue(any(User.class), eq(GetUserDTO.class))).thenReturn(userDTO);

        ResponseEntity<List<GetUserDTO>> responseEntity = userService.getAllUsers(limit, offset, searchStrings, Email);

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody()).hasSize(1);
        assertThat(responseEntity.getBody().get(0).getUserID()).isEqualTo(userId);
        assertThat(responseEntity.getHeaders().get("X-Total-Users").get(0)).isEqualTo("1");

        verify(userRepository, times(1)).getAll(anyString(), anyString(), any());
    }
}

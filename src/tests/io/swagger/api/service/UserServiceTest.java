package io.swagger.api.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.api.repository.UserRepository;
import io.swagger.model.DTO.CreateUserDTO;
import io.swagger.model.DTO.GetUserDTO;
import io.swagger.model.DTO.UpdateUserDTO;
import io.swagger.model.Role;
import io.swagger.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {
    @Mock
    private PasswordEncoder passwordEncoder;

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
    void testPasswordEncoder() {
        String userPassword = "password";


        when(passwordEncoder.encode(anyString())).thenReturn(userPassword);

        String response = passwordEncoder.encode(userPassword);

        assertThat(response).isEqualTo(userPassword);

        verify(passwordEncoder).encode(anyString());
    }
    @Test
    void testUserAdd() {
        String userPassword = "password";

        when(passwordEncoder.encode(anyString())).thenReturn(userPassword);

        User user = new User(UUID.fromString("bb0cc36d-69a7-471e-a665-3609bc14c27a"), "Test", "Tester", "mail@example.com", "password", Arrays.asList(Role.ROLE_USER), true, new ArrayList<>(), 1000.00, 10000.00);

        CreateUserDTO createUserDTO = new CreateUserDTO("Test", "Tester", "mail@example.com", "password", Arrays.asList(Role.ROLE_USER), 1000.00, 10000.00);

        when(objectMapper.convertValue(any(CreateUserDTO.class), eq(User.class))).thenReturn(user);
        when(userRepository.save(any(User.class))).thenReturn(user);

        User response = userService.add(createUserDTO);

        assertThat(response).isEqualTo(user);

        verify(userRepository).save(any(User.class));
    }

    @Test
    void testUsersGet() {
        Integer limit = 10;
        Integer offset = 0;
        String searchStrings = null;
        String Email = null;

        UUID userId = UUID.randomUUID();
        GetUserDTO userDTO = new GetUserDTO();
        userDTO.setUserID(userId);

        List<User> users = new ArrayList<>();
        User user = new User();
        user.setUserID(userId);
        users.add(user);

        when(userRepository.getAll(
                ArgumentMatchers.<String>any(),
                ArgumentMatchers.<String>any(),
                ArgumentMatchers.<Pageable>any()
        )).thenReturn(users);

        when(objectMapper.convertValue(
                Mockito.any(User.class),
                Mockito.eq(GetUserDTO.class)
        )).thenReturn(userDTO);

        List<GetUserDTO> response = userService.getAllUsers(limit, offset, searchStrings, Email);


        assertThat(response).hasSize(1);
        assertThat(response.get(0).getUserID()).isEqualTo(userId);

        verify(userRepository, times(1)).getAll(
                ArgumentMatchers.<String>any(),
                ArgumentMatchers.<String>any(),
                ArgumentMatchers.<Pageable>any()
        );
    }

    @Test
    public void testUsersUserIdGet() {
        UUID userID = UUID.randomUUID();
        GetUserDTO userDTO = new GetUserDTO();
        userDTO.setUserID(userID);
        User user = new User();
        user.setUserID(userID);

        when(userRepository.getUserByUserID(userID)).thenReturn(user);
        when(objectMapper.convertValue(any(User.class), eq(GetUserDTO.class))).thenReturn(userDTO);

        GetUserDTO response = userService.getUserByUserID(userID);

        assertThat(response).isEqualTo(userDTO);
        assertThat(response.getUserID()).isEqualTo(userID);

        verify(userRepository, times(1)).getUserByUserID(userID);
    }


    @Test
    void testUpdateUser() {
        UUID userID = UUID.randomUUID();
        UpdateUserDTO updateUserDTO = new UpdateUserDTO();
        updateUserDTO.setFirstName("test");

        GetUserDTO userDTO = new GetUserDTO();
        userDTO.setUserID(userID);

        User existingUser = new User();
        existingUser.setUserID(userID);
        when(userRepository.getUserByUserID(userID)).thenReturn(existingUser);
        when(userRepository.save(any(User.class))).thenReturn(existingUser);
        when(objectMapper.convertValue(any(User.class), eq(GetUserDTO.class))).thenReturn(userDTO);

        GetUserDTO response = userService.updateUser(userID, updateUserDTO);

        assertThat(response.getUserID()).isEqualTo(userID);

        verify(userRepository, times(1)).getUserByUserID(userID);
        verify(userRepository, times(1)).save(existingUser);
    }
}

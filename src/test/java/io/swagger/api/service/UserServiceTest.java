// BEGIN: 9f8d7c6b1a2e
package io.swagger.api.service;

import io.swagger.api.exceptions.ValidationException;
import io.swagger.api.repository.UserRepository;
import io.swagger.model.DTO.UpdateUserDTO;
import io.swagger.model.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private AuthenticationManager authenticationManager;

    @InjectMocks
    private UserService userService;

    @BeforeEach
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testAddUser() {
        User user = new User();
        user.setUserID(UUID.randomUUID());
        user.setFirstName("John");
        user.setLastName("Doe");
        user.setEmail("john.doe@example.com");
        user.setPassword("password");
        user.setRole(User.RoleEnum.USER);
        user.setActive(true);

        when(userRepository.save(any(User.class))).thenReturn(user);

        User savedUser = userService.add(user);

        Assertions.assertEquals(user, savedUser);
    }

    @Test
    public void testGetAllUsers() {
        List<User> users = new ArrayList<>();
        User user1 = new User();
        user1.setUserID(UUID.randomUUID());
        user1.setFirstName("John");
        user1.setLastName("Doe");
        user1.setEmail("john.doe@example.com");
        user1.setPassword("password");
        user1.setRole(User.RoleEnum.USER);
        user1.setActive(true);
        users.add(user1);

        User user2 = new User();
        user2.setUserID(UUID.randomUUID());
        user2.setFirstName("Jane");
        user2.setLastName("Doe");
        user2.setEmail("jane.doe@example.com");
        user2.setPassword("password");
        user2.setRole(User.RoleEnum.USER);
        user2.setActive(true);
        users.add(user2);

        when(userRepository.findAll()).thenReturn(users);

        List<User> allUsers = userService.getAllUsers();

        Assertions.assertEquals(users, allUsers);
    }

    @Test
    public void testGetUserByUserID() {
        UUID userID = UUID.randomUUID();
        User user = new User();
        user.setUserID(userID);
        user.setFirstName("John");
        user.setLastName("Doe");
        user.setEmail("john.doe@example.com");
        user.setPassword("password");
        user.setRole(User.RoleEnum.USER);
        user.setActive(true);

        when(userRepository.getUserByUserID(userID)).thenReturn(user);

        User foundUser = userService.getUserByUserID(userID);

        Assertions.assertEquals(user, foundUser);
    }

    @Test
    public void testUpdateUser() throws ValidationException {
        UUID userID = UUID.randomUUID();
        UpdateUserDTO updateUserDTO = new UpdateUserDTO();
        updateUserDTO.setFirstName("John");
        updateUserDTO.setLastName("Doe");
        updateUserDTO.setEmail("john.doe@example.com");
        updateUserDTO.setPassword("password");
        updateUserDTO.setRole(User.RoleEnum.USER);
        updateUserDTO.setActive(true);
        updateUserDTO.setTransactionLimit(100.0);
        updateUserDTO.setDailyLimit(10.0);

        User existingUser = new User();
        existingUser.setUserID(userID);
        existingUser.setFirstName("Jane");
        existingUser.setLastName("Doe");
        existingUser.setEmail("jane.doe@example.com");
        existingUser.setPassword("password");
        existingUser.setRole(User.RoleEnum.USER);
        existingUser.setActive(true);

        when(userRepository.getUserByUserID(userID)).thenReturn(existingUser);
        when(userRepository.save(any(User.class))).thenReturn(existingUser);

        User updatedUser = userService.update(updateUserDTO, userID);

        Assertions.assertEquals(updateUserDTO.getFirstName(), updatedUser.getFirstName());
        Assertions.assertEquals(updateUserDTO.getLastName(), updatedUser.getLastName());
        Assertions.assertEquals(updateUserDTO.getEmail(), updatedUser.getEmail());
        Assertions.assertEquals(updateUserDTO.getPassword(), updatedUser.getPassword());
        Assertions.assertEquals(updateUserDTO.getRole(), updatedUser.getRole());
        Assertions.assertEquals(updateUserDTO.getActive(), updatedUser.getActive());
        Assertions.assertEquals(updateUserDTO.getTransactionLimit(), updatedUser.getTransactionLimit());
        Assertions.assertEquals(updateUserDTO.getDailyLimit(), updatedUser.getDailyLimit());
    }

    @Test
    public void testGetUserByEmail() {
        String email = "john.doe@example.com";
        User user = new User();
        user.setUserID(UUID.randomUUID());
        user.setFirstName("John");
        user.setLastName("Doe");
        user.setEmail(email);
        user.setPassword("password");
        user.setRole(User.RoleEnum.USER);
        user.setActive(true);

        when(userRepository.getUserByEmail(email)).thenReturn(user);

        User foundUser = userService.getUserByEmail(email);

        Assertions.assertEquals(user, foundUser);
    }
}
// END: 9f8d7c6b1a2e
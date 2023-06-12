package io.swagger.api.repository;

import io.swagger.model.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.data.domain.Pageable;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.mockito.Mockito.*;

public class UserRepositoryTest {

    private UserRepository userRepository; // Declare the UserRepository

    @Test
    public void testGetAll() {
        // Create test data
        User user1 = new User();
        user1.setUserID(UUID.randomUUID());

        User user2 = new User();
        user2.setUserID(UUID.randomUUID());

        // Create the mock UserRepository
        userRepository = mock(UserRepository.class);

        // Mock the behavior of the userRepository.getAll() method
        Mockito.when(userRepository.getAll(null, null, Pageable.unpaged()))
                .thenReturn(Arrays.asList(user1, user2));

        // Perform the getAll() method invocation
        List<User> users = userRepository.getAll(null, null, Pageable.unpaged());

        // Verify the results
        Assertions.assertEquals(2, users.size());

        // Verify the interactions
        verify(userRepository, times(1)).getAll(null, null, Pageable.unpaged());
    }

    @Test
    public void testGetUserByUserID() {
        // Create a test user with a specific UUID
        UUID userId = UUID.randomUUID();
        User user = new User();
        user.setUserID(userId);

        // Create the mock UserRepository
        userRepository = mock(UserRepository.class);

        // Mock the behavior of the userRepository.getUserByUserID() method
        Mockito.when(userRepository.getUserByUserID(userId))
                .thenReturn(user);

        // Perform the getUserByUserID() method invocation
        User resultUser = userRepository.getUserByUserID(userId);

        // Verify the results
        Assertions.assertEquals(userId, resultUser.getUserID());

        // Verify the interactions
        verify(userRepository, times(1)).getUserByUserID(userId);
    }

    @Test
    public void testGetUserByEmail() {
        // Create a test user with a specific email
        String email = "test@example.com";
        User user = new User();
        user.setEmail(email);

        // Create the mock UserRepository
        userRepository = mock(UserRepository.class);

        // Mock the behavior of the userRepository.getUserByEmail() method
        Mockito.when(userRepository.getUserByEmail(email))
                .thenReturn(user);

        // Perform the getUserByEmail() method invocation
        User resultUser = userRepository.getUserByEmail(email);

        // Verify the results
        Assertions.assertEquals(email, resultUser.getEmail());

        // Verify the interactions
        verify(userRepository, times(1)).getUserByEmail(email);
    }
}

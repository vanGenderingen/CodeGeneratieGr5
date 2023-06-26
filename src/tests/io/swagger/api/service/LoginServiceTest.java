package io.swagger.api.service;

import io.swagger.api.repository.UserRepository;
import io.swagger.api.security.JwtTokenProvider;
import io.swagger.model.Role;
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
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class LoginServiceTest {
    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private JwtTokenProvider jwtTokenProvider;

    @InjectMocks
    private LoginService loginService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }
    @Test
    public void testLoginWithValidCredentials() {
        String userEmail = "john.doe@mail.nl";
        String password = "password";
        String encodedPassword = "password";
        User user = new User();
        user.setEmail(userEmail);
        user.setPassword(encodedPassword);
        List<Role> roles = new ArrayList<>();
        roles.add(Role.ROLE_USER);
        user.setRoles(roles);

        when(userRepository.getUserByEmail(userEmail)).thenReturn(user);
        when(passwordEncoder.matches(password, encodedPassword)).thenReturn(true);
        when(jwtTokenProvider.createToken(user.getUserID(), user.getRoles())).thenReturn("token");

        String token = loginService.login(userEmail, password);

        Assertions.assertEquals("token", token);
        Mockito.verify(userRepository, times(1)).getUserByEmail(userEmail);
        Mockito.verify(passwordEncoder, times(1)).matches(password, encodedPassword);
        Mockito.verify(jwtTokenProvider, times(1)).createToken(user.getUserID(), user.getRoles());
    }
    @Test
    public void testLoginWithInvalidCredentials() {
        String userEmail = "test@example.com";
        String password = "invalidPassword";
        String encodedPassword = "invalidEncodedPassword";
        User user = new User();
        user.setEmail(userEmail);
        user.setPassword(encodedPassword);
        List<Role> roles = new ArrayList<>();
        roles.add(Role.ROLE_USER);
        user.setRoles(roles);

        when(userRepository.getUserByEmail(userEmail)).thenReturn(user);
        when(passwordEncoder.matches(password, encodedPassword)).thenReturn(false);

        assertThrows(ResponseStatusException.class, () -> loginService.login(userEmail, password));
        Mockito.verify(userRepository, times(1)).getUserByEmail(userEmail);
        Mockito.verify(passwordEncoder, times(1)).matches(password, encodedPassword);
        Mockito.verify(jwtTokenProvider, never()).createToken(any(), anyList());
    }
    @Test
    public void testLoginWithNonexistentUser() {
        String userEmail = "test@example.com";
        String password = "password";

        when(userRepository.getUserByEmail(userEmail)).thenReturn(null);

        assertThrows(ResponseStatusException.class, () -> loginService.login(userEmail, password));
        Mockito.verify(userRepository, times(1)).getUserByEmail(userEmail);
        Mockito.verify(passwordEncoder, never()).matches(anyString(), anyString());
        Mockito.verify(jwtTokenProvider, never()).createToken(any(), anyList());
    }
    @Test
    public void testLoginWithExistingUser() {
        String userEmail = "debank@mail.nl";
        String password = "password";

        when(userRepository.getUserByEmail(userEmail)).thenReturn(null);

        assertThrows(ResponseStatusException.class, () -> loginService.login(userEmail, password));
        Mockito.verify(userRepository, times(1)).getUserByEmail(userEmail);
        Mockito.verify(passwordEncoder, never()).matches(anyString(), anyString());
        Mockito.verify(jwtTokenProvider, never()).createToken(any(), anyList());
    }
    @Test
    public void testLoginWithEmptyUsername() {
        String userEmail = "";
        String password = "password";
        assertThrows(ResponseStatusException.class, () -> loginService.login(userEmail, password));
    }
    @Test
    public void testLoginWithEmptyPassword() {
        String userEmail = "debank@mail.nl";
        String password = "";
        assertThrows(ResponseStatusException.class, () -> loginService.login(userEmail, password));
    }
    @Test
    public void testLoginWithInvalidPasswordButCorrectEmail() {
        String userEmail = "debank@mail.nl";
        String password = "invalidPassword";

        User user = new User();
        user.setEmail(userEmail);
        user.setPassword("password");

        when(userRepository.getUserByEmail(userEmail)).thenReturn(user);
        when(passwordEncoder.matches(password, user.getPassword())).thenReturn(false);

        assertThrows(ResponseStatusException.class, () -> loginService.login(userEmail, password));
    }
    @Test
    public void testLoginWithCorrectPasswordButIncorrectEmail() {
        String userEmail = "test@example.com";
        String password = "password";

        User user = new User();
        user.setPassword(userEmail);
        user.setPassword("password");

        when(userRepository.getUserByEmail(userEmail)).thenReturn(user);
        when(passwordEncoder.matches(password, user.getPassword())).thenReturn(false);

        assertThrows(ResponseStatusException.class, () -> loginService.login(userEmail, password));
    }

}

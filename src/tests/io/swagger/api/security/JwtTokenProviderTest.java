package io.swagger.api.security;

import io.swagger.model.Role;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;

import jakarta.servlet.http.HttpServletRequest;

import java.util.Collections;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class JwtTokenProviderTest {

    private JwtTokenProvider jwtTokenProvider;

    @Mock
    private HttpServletRequest request;

    @Mock
    private UserDetails userDetails;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        jwtTokenProvider = new JwtTokenProvider();
        jwtTokenProvider.init();
    }

    @Test
    void testCreateToken() {
        jwtTokenProvider.init();
        UUID userId = UUID.randomUUID();
        String token = jwtTokenProvider.createToken(userId, Collections.singletonList(Role.ROLE_USER));
        assertNotNull(token);
    }

    @Test
    void testGetAuthentication() {
        when(userDetails.getAuthorities()).thenReturn(Collections.emptyList());
        when(userDetails.getUsername()).thenReturn("test");
        String token = jwtTokenProvider.createToken(UUID.randomUUID(), Collections.singletonList(Role.ROLE_USER));
        Authentication authentication = jwtTokenProvider.getAuthentication(token);
        assertNotNull(authentication);
        assertEquals(userDetails.getUsername(), authentication.getName());
    }

    @Test
    void testGetUserID() {
        UUID userId = UUID.randomUUID();
        String token = jwtTokenProvider.createToken(userId, Collections.singletonList(Role.ROLE_USER));
        String result = jwtTokenProvider.getUserID(token);
        assertEquals(userId.toString(), result);
    }

    @Test
    void testResolveToken() {
        String token = jwtTokenProvider.createToken(UUID.randomUUID(), Collections.singletonList(Role.ROLE_USER));
        when(request.getHeader("Authorization")).thenReturn("Bearer " + token);
        String result = jwtTokenProvider.resolveToken(request);
        assertEquals(token, result);
    }

    @Test
    void testValidateToken() {
        jwtTokenProvider.init();
        String token = jwtTokenProvider.createToken(UUID.randomUUID(), Collections.singletonList(Role.ROLE_USER));
        assertTrue(jwtTokenProvider.validateToken(token));
        assertThrows(Exception.class, () -> jwtTokenProvider.validateToken("invalid_token"));
    }
 
}
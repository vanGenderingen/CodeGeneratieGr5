package io.swagger.api.service;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.server.ResponseStatusException;

import java.security.Principal;
import java.util.Collections;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

class ValidationServiceTest {

    @Test
    void testValidateAccountGetAccess_EmployeeAccess() {
        UUID accountId = UUID.randomUUID();
        Principal principal = createPrincipal(accountId.toString(), "ROLE_EMPLOYEE");

        assertDoesNotThrow(() -> ValidationService.validateAccountGetAccess(accountId, principal));
    }

    @Test
    void testValidateAccountGetAccess_UserAccessWithMatchingAccountId() {
        UUID accountId = UUID.randomUUID();
        Principal principal = createPrincipal(accountId.toString(), "ROLE_USER");

        assertDoesNotThrow(() -> ValidationService.validateAccountGetAccess(accountId, principal));
    }

    @Test
    void testValidateAccountGetAccess_UserAccessWithDifferentAccountId() {
        UUID accountId = UUID.randomUUID();
        UUID differentAccountId = UUID.randomUUID();
        Principal principal = createPrincipal(differentAccountId.toString(), "ROLE_USER");

        ResponseStatusException exception = assertThrows(ResponseStatusException.class,
                () -> ValidationService.validateAccountGetAccess(accountId, principal));

        assertEquals("The user is not authorized to access this account", exception.getReason());
        assertEquals(403, exception.getStatus().value());
    }

    @Test
    void testValidateAccountPutAccess_EmployeeAccess() {
        UUID accountId = UUID.randomUUID();
        Principal principal = createPrincipal(accountId.toString(), "ROLE_EMPLOYEE");
        String IBAN = "NL01INHO0000000002";

        assertDoesNotThrow(() -> ValidationService.validateAccountPutAccess(accountId, IBAN, principal));
    }

    @Test
    void testValidateAccountPutAccess_UserAccessWithMatchingAccountId() {
        UUID accountId = UUID.randomUUID();
        Principal principal = createPrincipal(accountId.toString(), "ROLE_USER");
        String IBAN = "NL01INHO0000000002";

        assertDoesNotThrow(() -> ValidationService.validateAccountPutAccess(accountId, IBAN, principal));
    }

    @Test
    void testValidateAccountPutAccess_UpdatingBankAccount() {
        UUID accountId = UUID.randomUUID();
        Principal principal = createPrincipal(accountId.toString(), "ROLE_USER");
        String IBAN = "NL01INHO0000000001";

        ResponseStatusException exception = assertThrows(ResponseStatusException.class,
                () -> ValidationService.validateAccountPutAccess(accountId, IBAN, principal));

        assertEquals("The bank's account can not be adjusted", exception.getReason());
        assertEquals(403, exception.getStatus().value());
    }

    @Test
    void testValidateAccountPutAccess_UserAccessWithDifferentAccountId() {
        UUID accountId = UUID.randomUUID();
        UUID differentAccountId = UUID.randomUUID();
        Principal principal = createPrincipal(differentAccountId.toString(), "ROLE_USER");
        String IBAN = "NL01INHO0000000002";

        ResponseStatusException exception = assertThrows(ResponseStatusException.class,
                () -> ValidationService.validateAccountPutAccess(accountId, IBAN, principal));

        assertEquals("The user is not authorized to access this account", exception.getReason());
        assertEquals(403, exception.getStatus().value());
    }

    @Test
    void testValidateUserGetAndPutAcess_UserAccessWithMatchingUserId() {
        UUID userId = UUID.randomUUID();
        Principal principal = createPrincipal(userId.toString(), "ROLE_USER");

        assertDoesNotThrow(() -> ValidationService.validateUserGetAndPutAccess(userId, principal));
    }

    @Test
    void testValidateUserGetAndPutAcess_UserAccessWithDifferentUserId() {
        UUID userId = UUID.randomUUID();
        UUID differentUserId = UUID.randomUUID();
        Principal principal = createPrincipal(differentUserId.toString(), "ROLE_USER");

        ResponseStatusException exception = assertThrows(ResponseStatusException.class,
                () -> ValidationService.validateUserGetAndPutAccess(userId, principal));

        assertEquals("You're not authorized to do this", exception.getReason());
        assertEquals(403, exception.getStatus().value());
    }

    @Test
    void testValidateUserGetAndPutAcess_EmployeeAccess() {
        UUID userId = UUID.randomUUID();
        Principal principal = createPrincipal(userId.toString(), "ROLE_EMPLOYEE");

        assertDoesNotThrow(() -> ValidationService.validateUserGetAndPutAccess(userId, principal));
    }

    private Principal createPrincipal(String username, String role) {
        User user = new User(username, "", Collections.singletonList(() -> role));
        Authentication authentication = new UsernamePasswordAuthenticationToken(user, null);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return mock(Principal.class, Mockito.withSettings().defaultAnswer(invocation -> username));
    }
}

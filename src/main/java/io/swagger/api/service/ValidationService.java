package io.swagger.api.service;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.security.Principal;
import java.util.Objects;
import java.util.UUID;

@Service
public class ValidationService {

    public static void validateAccountGetAccess(UUID userID, Principal principal) {
        validate(userID, principal, "The user is not authorized to access this account");
}

    public static void validateUserGetAndPutAccess(UUID userID, Principal principal) {
        validate(userID, principal, "You're not authorized to do this");
    }

    public static void validateAccountPutAccess(UUID userID, String IBAN, Principal principal) {
        boolean isEmployee = SecurityContextHolder.getContext().getAuthentication().getAuthorities()
                .stream()
                .anyMatch(role -> role.getAuthority().equals("ROLE_EMPLOYEE"));
        if (!Objects.equals(IBAN, "NL01INHO0000000001")) {
            validate(userID, principal, "The user is not authorized to access this account");
        }else{
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "The bank's account can not be adjusted");
        }
    }

    private static void validate (UUID userID, Principal principal, String message) {
        String userIdFromToken = principal.getName();

        boolean isEmployee = SecurityContextHolder.getContext().getAuthentication().getAuthorities()
                .stream()
                .anyMatch(role -> role.getAuthority().equals("ROLE_EMPLOYEE"));
        if (!isEmployee) {
            boolean isExpectedUser = userIdFromToken.equals(userID.toString());
            if (!isExpectedUser) {
                throw new ResponseStatusException(HttpStatus.FORBIDDEN, message);
            }
        }
    }
}

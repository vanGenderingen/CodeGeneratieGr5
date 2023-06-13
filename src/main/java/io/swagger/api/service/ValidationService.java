package io.swagger.api.service;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.security.Principal;
import java.util.UUID;

@Service
public class ValidationService {

    public static void validateAccountGetAndPutAccess(UUID accountID, Principal principal) {
        String userIdFromToken = principal.getName();

        boolean isEmployee = SecurityContextHolder.getContext().getAuthentication().getAuthorities()
                .stream()
                .anyMatch(role -> role.getAuthority().equals("ROLE_EMPLOYEE"));
        if (!isEmployee) {
            boolean isExpectedUser = userIdFromToken.equals(accountID.toString());
            if (!isExpectedUser) {
                throw new ResponseStatusException(HttpStatus.FORBIDDEN, "The user is not authorized to access this account");
            }
        }
    }
}

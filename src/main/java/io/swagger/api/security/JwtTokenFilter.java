package io.swagger.api.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.file.AccessDeniedException;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

@Component
public class JwtTokenFilter extends OncePerRequestFilter {

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
        String path = httpServletRequest.getRequestURI();

        // Skip filtering for the /login endpoint
        if (path.equals("/login")) {
            filterChain.doFilter(httpServletRequest, httpServletResponse);
            return;
        }

        String token = jwtTokenProvider.resolveToken(httpServletRequest);

        try {
            if (token != null && jwtTokenProvider.validateToken(token)) {
                Authentication authentication = jwtTokenProvider.getAuthentication(token);
                SecurityContextHolder.getContext().setAuthentication(authentication);

                // Filter roles
                filterRoles(authentication.getAuthorities());
            }
        } catch (ResponseStatusException exception) {
            SecurityContextHolder.clearContext();
            return;
        }

        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }

    private void filterRoles(Collection<? extends GrantedAuthority> authorities) throws AccessDeniedException {
        // Check if the authenticated user has any of the required roles
        List<String> requiredRoles = Arrays.asList("ROLE_EMPLOYEE", "ROLE_USER");
        boolean hasRequiredRole = authorities.stream()
                .anyMatch(authority -> requiredRoles.contains(authority.getAuthority()));

        if (!hasRequiredRole) {
            throw new AccessDeniedException("Access is denied. User does not have the required roles.");
        }
    }
}

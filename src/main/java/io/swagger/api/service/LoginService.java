package io.swagger.api.service;

import io.swagger.api.repository.UserRepository;
import io.swagger.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import io.swagger.api.security.JwtTokenProvider;

import javax.naming.AuthenticationException;
import java.util.Objects;

@Service
public class LoginService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    public String login(String userEmail, String password) {
        try {
            User user = userRepository.getUserByEmail(userEmail);
            //passwordEncoder.encode(password);
//            if (Objects.equals(password, user.getPassword())) {
            if (passwordEncoder.matches(password, user.getPassword())) {
                return jwtTokenProvider.createToken(user.getUserID(), user.getRoles());
            } else {
                throw new AuthenticationException("Invalid username/password");
            }

        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, "Username/password invalid");
        }
    }




}

package io.swagger.api.service;

import io.swagger.api.controllers.LoginApiController;
import io.swagger.api.exceptions.ValidationException;
import io.swagger.api.repository.UserRepository;
import io.swagger.model.DTO.UpdateUserDTO;
import io.swagger.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.naming.AuthenticationException;
import java.util.List;
import java.util.UUID;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    public User add(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User getUserByUserID(UUID userID) {
        return userRepository.getUserByUserID(userID);
    }

    public User update(UpdateUserDTO updateUserDTO, UUID userID) throws ValidationException {
        User existingUser = getUserByUserID(userID);
        existingUser.setFirstName(updateUserDTO.getFirstName());
        existingUser.setLastName(updateUserDTO.getLastName());
        existingUser.setEmail(updateUserDTO.getEmail());
        existingUser.setPassword(updateUserDTO.getPassword());
        existingUser.setRole(User.RoleEnum.fromValue(updateUserDTO.getRole().toString()));
        existingUser.setActive(updateUserDTO.getActive());
        // TODO: add account
        existingUser.setTransactionLimit(updateUserDTO.getTransactionLimit().doubleValue());
        existingUser.setDailyLimit(updateUserDTO.getDailyLimit().doubleValue());

        try {
            return userRepository.save(existingUser);
        } catch (Exception e) {
            throw new ValidationException("Error while updating user");
        }
    }
    public String login(String userEmail, String password) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userEmail, password));

            User user = userRepository.getUserByEmail(userEmail);
            return LoginApiController.generateJwtToken(user);

        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, "Username/password invalid");
        }
    }

    public User getUserByEmail(String email) {
        return userRepository.getUserByEmail(email);
    }
}

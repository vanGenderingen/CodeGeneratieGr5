package io.swagger.api.service;

import io.swagger.api.exceptions.ValidationException;
import io.swagger.api.repository.UserRepository;
import io.swagger.api.security.JwtTokenProvider;
import io.swagger.model.DTO.UpdateUserDTO;
import io.swagger.model.Role;
import io.swagger.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.naming.AuthenticationException;
import java.util.Arrays;

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

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public User add(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        if (user.getRoles() == null){
            user.setRoles(Arrays.asList(Role.ROLE_USER));
        }
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
//        existingUser.setRole(User.RoleEnum.fromValue(updateUserDTO.getRole().toString()));
        existingUser.setActive(updateUserDTO.getActive());
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
            User user = userRepository.getUserByEmail(userEmail);

            if (bCryptPasswordEncoder.matches(password, user.getPassword())) {
                return jwtTokenProvider.createToken(user.getUserID(), user.getRoles());

            } else {
                throw new AuthenticationException("Invalid username/password");
            }


        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, "Username/password invalid");
        }
    }

    public User getUserByEmail(String email) {
        return userRepository.getUserByEmail(email);
    }

    public Boolean existsByEmail(String email){
        return userRepository.getUserByEmail(email) != null;
    }
}

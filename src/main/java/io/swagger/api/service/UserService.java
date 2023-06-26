package io.swagger.api.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.api.controllers.UsersApiController;
import io.swagger.api.repository.UserRepository;
import io.swagger.api.security.JwtTokenProvider;
import io.swagger.model.DTO.CreateUserDTO;
import io.swagger.model.DTO.GetUserDTO;
import io.swagger.model.DTO.UpdateUserDTO;
import io.swagger.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;



@Service
public class UserService {

    private static final Logger log = LoggerFactory.getLogger(UsersApiController.class);

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    public ResponseEntity<User> add(CreateUserDTO createUserDTO) {
        String email = createUserDTO.getEmail();
        // Check if user with the given email already exists
        if (existsByEmail(email)) {
            return new ResponseEntity<>(HttpStatus.valueOf(" User with email " + email + " already exists"));
        }
        User user = objectMapper.convertValue(createUserDTO, User.class);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        User result = userRepository.save(user);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    public ResponseEntity<List<GetUserDTO>> getAllUsers(Integer limit, Integer offset, String searchStrings, String Email) {
        try {
            Pageable pageable = PageRequest.of(offset, limit);
            List<User> users = userRepository.getAll(Email, searchStrings, pageable);
            List<GetUserDTO> userDTOS = new ArrayList<>();
            for (User user : users) {
                GetUserDTO userDTO = objectMapper.convertValue(user, GetUserDTO.class);
                userDTOS.add(userDTO);
            }
            return new ResponseEntity<>(userDTOS, HttpStatus.OK);
        } catch (Exception e) {
            log.error("Couldn't serialize response for content type application/json", e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<GetUserDTO> getUserByUserID(UUID userID) {
        try {
            User user = userRepository.getUserByUserID(userID);
            GetUserDTO userDTO = objectMapper.convertValue(user, GetUserDTO.class);
            return new ResponseEntity<>(userDTO, HttpStatus.OK);
        } catch (Exception e) {
            log.error("Couldn't serialize response for content type application/json", e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<GetUserDTO> updateUser(UUID userID, UpdateUserDTO updateUserDTO) {
        try {
            User existingUser = userRepository.getUserByUserID(userID);
            if (updateUserDTO.getFirstName() != null) {
                existingUser.setFirstName(updateUserDTO.getFirstName());
            }
            if (updateUserDTO.getLastName() != null) {
                existingUser.setLastName(updateUserDTO.getLastName());
            }
            if (updateUserDTO.getEmail() != null) {
                existingUser.setEmail(updateUserDTO.getEmail());
            }
            if (updateUserDTO.getActive() != null) {
                existingUser.setActive(updateUserDTO.getActive());
            }
            if (updateUserDTO.getTransactionLimit() != null) {
                existingUser.setTransactionLimit(updateUserDTO.getTransactionLimit());
            }
            if (updateUserDTO.getDailyLimit() != null) {
                existingUser.setDailyLimit(updateUserDTO.getDailyLimit());
            }
            User updatedUser = userRepository.save(existingUser);
            GetUserDTO userDTO = objectMapper.convertValue(updatedUser, GetUserDTO.class);
            return new ResponseEntity<>(userDTO, HttpStatus.OK);
        } catch (Exception e) {
            log.error("Couldn't serialize response for content type application/json", e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    public void updatePasswordByEmail(String email, String newPassword) {
        try {
            User user = userRepository.getUserByEmail(email);

            if (user != null) {
                user.setPassword(passwordEncoder.encode(newPassword));
                userRepository.save(user);
            } else {
                // Handle the case when the user is not found
                // For example, throw an exception or log an error
                throw new RuntimeException("User not found for email: " + email);
            }
        } catch (Exception e) {
            // Handle any exceptions that occur during the password update process
            // For example, log the error or throw a custom exception
            e.printStackTrace();
            throw new RuntimeException("Error updating password: " + e.getMessage());
        }
    }


    public User getUserByEmail(String email) {
        return userRepository.getUserByEmail(email);
    }

    public Boolean existsByEmail(String email){
        return userRepository.getUserByEmail(email) != null;
    }
}

package io.swagger.api.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.api.controllers.UsersApiController;
import io.swagger.api.repository.UserRepository;
import io.swagger.model.DTO.CreateUserDTO;
import io.swagger.model.DTO.GetUserDTO;
import io.swagger.model.DTO.UpdateUserDTO;
import io.swagger.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;



@Service
public class UserService {

    private static final Logger log = LoggerFactory.getLogger(UsersApiController.class);

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private TransactionService transactionService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public User add(CreateUserDTO createUserDTO) {
        try {
            String email = createUserDTO.getEmail();
            // Check if user with the given email already exists
            if (existsByEmail(email)) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User with email " + email + " already exists");
            }
            // Convert DTO to User
            User user = objectMapper.convertValue(createUserDTO, User.class);
            user.setPassword(passwordEncoder.encode(user.getPassword()));

            // Save the user
            return userRepository.save(user);

        } catch (IllegalArgumentException | NullPointerException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Failed to add user due to invalid data");
        }
    }

    public List<GetUserDTO> getAllUsers(Integer limit, Integer offset, String searchStrings, String Email) {
        try {
            return convertToDTO(userRepository.getAll(Email, searchStrings, PageRequest.of(offset, limit)));
        } catch (NullPointerException nullPointerException) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Failed to get users");
        }
    }

    public GetUserDTO getUserByUserID(UUID userID) {
        try {
            GetUserDTO userDTO = objectMapper.convertValue(userRepository.getUserByUserID(userID), GetUserDTO.class);

            userDTO.setLeftOverDailyLimit(transactionService.calculateLeftOverDailyLimit(userDTO));

            return userDTO;

        } catch (NullPointerException nullPointerException) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Failed to get user");
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

    public GetUserDTO updateUser(UUID userID, UpdateUserDTO updateUserDTO) {
        try {
            // Send the user to the update method andn return the updated user then save it and convert it to DTO
            return objectMapper.convertValue(userRepository.save(updateUserCredentials(
                    userRepository.getUserByUserID(userID), updateUserDTO)), GetUserDTO.class);

        } catch (NullPointerException nullPointerException) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "The user you are trying to update does not exist");
        } catch (IllegalArgumentException illegalArgumentException) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Failed to update user");
        }
    }

    public User getUserByEmail(String email) {
        return userRepository.getUserByEmail(email);
    }

    private List<GetUserDTO> convertToDTO(List<User> users) {
        List<GetUserDTO> userDTOS = new ArrayList<>();
        for (User user : users) {
            GetUserDTO userDTO = objectMapper.convertValue(user, GetUserDTO.class);
            userDTOS.add(userDTO);
        }
        return userDTOS;
    }

    private User updateUserCredentials(User existingUser, UpdateUserDTO updateUserDTO) {
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

        return existingUser;
    }
    private Boolean existsByEmail(String email){
        return userRepository.getUserByEmail(email) != null;
    }
}

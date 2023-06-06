package io.swagger.api.service;

import io.swagger.api.exceptions.ValidationException;
import io.swagger.api.repository.UserRepository;
import io.swagger.model.DTO.UpdateUserDTO;
import io.swagger.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

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
}

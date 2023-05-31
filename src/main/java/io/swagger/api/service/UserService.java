package io.swagger.api.service;

import io.swagger.api.repository.UserRepository;
import io.swagger.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public User add(User user) {
        user = userRepository.save(user);
        return user;
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User getUserByUserID(UUID userID) {
        return userRepository.getUserByUserID(userID);
    }

    public User update(User user, UUID userID){
        User getExistingUser = getUserByUserID(userID);
        getExistingUser.setFirstName(user.getFirstName());
        getExistingUser.setLastName(user.getLastName());
        getExistingUser.setEmail(user.getEmail());
        getExistingUser.setRole(user.getRole());
        getExistingUser.setActive(user.getActive());

    }
}

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
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.naming.AuthenticationException;
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

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public ResponseEntity<User> add(CreateUserDTO createUserDTO) {
        String email = createUserDTO.getEmail();

        // Check if user with the given email already exists
        if (existsByEmail(email)) {
            return new ResponseEntity<>(HttpStatus.valueOf(" User with email " + email + " already exists"));
        }

        User user = objectMapper.convertValue(createUserDTO, User.class);
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

    public ResponseEntity<GetUserDTO> update(UUID userID, UpdateUserDTO updateUserDTO) {
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

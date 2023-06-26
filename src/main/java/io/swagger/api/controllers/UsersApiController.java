package io.swagger.api.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.api.UsersApi;
import io.swagger.api.service.UserService;
import io.swagger.api.service.ValidationService;
import io.swagger.model.DTO.CreateUserDTO;
import io.swagger.model.DTO.GetUserDTO;
import io.swagger.model.DTO.UpdateUserDTO;
import io.swagger.model.User;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.security.Principal;
import java.util.List;
import java.util.UUID;

@CrossOrigin(origins = "")
@RestController
@RequestMapping("/users")
public class UsersApiController implements UsersApi {

    private final ObjectMapper objectMapper;

    private final HttpServletRequest request;

    @Autowired
    private UserService userService;


    public UsersApiController(ObjectMapper objectMapper, HttpServletRequest request) {
        this.objectMapper = objectMapper;
        this.request = request;
    }
    @Autowired
    public UsersApiController(ObjectMapper objectMapper, UserService userService, HttpServletRequest request) {
        this.userService = userService;
        this.objectMapper = objectMapper;
        this.request = request;
    }

    @Override
    @PreAuthorize("hasRole('ROLE_EMPLOYEE')")
    @PostMapping(produces = "application/json")
    public ResponseEntity<User> createUser(@Valid @RequestBody CreateUserDTO createUserDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.add(createUserDTO));
    }
    @Override
    @PreAuthorize("hasRole('ROLE_EMPLOYEE')")
    @GetMapping(produces = "application/json")
    public ResponseEntity<List<GetUserDTO>> getUsers(
            @Valid @RequestParam(value = "limit", defaultValue = "10") @Min(0) @Max(50) Integer limit,
            @Valid @RequestParam(value = "offset", defaultValue = "0") @Min(0) Integer offset,
            @Valid @RequestParam(value = "searchstrings", required = false) String searchStrings,
            @Valid @RequestParam(value = "Email", required = false) String Email)
    {
        return ResponseEntity.ok(userService.getAllUsers(limit, offset, searchStrings, Email));
    }

    @Override
    @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping(value = "/{userID}", produces = {"application/json"})
    public ResponseEntity<GetUserDTO> getUserById(
            @Valid @PathVariable("userID") UUID userID,
            Principal principal)
    {

        //Validate that the user is the same as the one in the token
        ValidationService.validateUserGetAndPutAccess(userID, principal);

        return ResponseEntity.ok(userService.getUserByUserID(userID));
    }
    @Override
    @PreAuthorize("hasRole('ROLE_USER')")
    @PutMapping(value = "/{userID}", produces = {"application/json"})
    public ResponseEntity<GetUserDTO> updateUser(
            @Valid @PathVariable("userID") UUID userID,
            @Valid @RequestBody UpdateUserDTO updateUserDTO,
            Principal principal)
    {
        //Validate that the user is the same as the one in the token
        ValidationService.validateUserGetAndPutAccess(userID, principal);

        return ResponseEntity.ok(userService.updateUser(userID, updateUserDTO));
    }

}
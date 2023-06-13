package io.swagger.api.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.api.interfaces.UsersApi;
import io.swagger.api.service.UserService;
import io.swagger.model.DTO.CreateUserDTO;
import io.swagger.model.DTO.GetUserDTO;
import io.swagger.model.DTO.UpdateUserDTO;
import io.swagger.model.User;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Schema;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.validation.ValidationException;
import java.util.List;
import java.util.UUID;

@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2023-05-16T13:11:00.686570329Z[GMT]")
@CrossOrigin(origins = "*")
@RestController
public class UsersApiController implements UsersApi {

    private static final Logger log = LoggerFactory.getLogger(UsersApiController.class);

    private final ObjectMapper objectMapper;

    @Autowired
    private UserService userService;
    private final HttpServletRequest request;


    public UsersApiController(ObjectMapper objectMapper, HttpServletRequest request) {
        this.userService = userService;
        this.objectMapper = objectMapper;
        this.request = request;
    }
    @Autowired
    public UsersApiController(ObjectMapper objectMapper, UserService userService, HttpServletRequest request) {
        this.userService = userService;
        this.objectMapper = objectMapper;
        this.request = request;
    }

    @PreAuthorize("hasRole('ROLE_EMPLOYEE')")
    @RequestMapping(value = "/users", produces = "application/json", method = RequestMethod.POST)
    public ResponseEntity<User> usersPost(@RequestBody CreateUserDTO createUserDTO) {
        return userService.add(createUserDTO);
    }

    @PreAuthorize("hasRole('ROLE_EMPLOYEE')")
    @RequestMapping(value = "/users", produces = {"application/json"}, method = RequestMethod.GET)
    public ResponseEntity<List<GetUserDTO>> usersGet(
            @Parameter(in = ParameterIn.QUERY, description = "The maximum number of accounts to retrieve.", schema = @Schema(allowableValues = {"0", "50"}, type = "integer", defaultValue = "20", maximum = "50")) @Valid @RequestParam(value = "limit", required = false, defaultValue = "10") Integer limit,
            @Parameter(in = ParameterIn.QUERY, description = "The offset for paginated results.", schema = @Schema(type = "integer", defaultValue = "0", minimum = "0")) @Valid @RequestParam(value = "offset", required = false, defaultValue = "0") Integer offset,
            @Parameter(in = ParameterIn.QUERY, description = "Comma-separated list of search strings to filter accounts.", schema = @Schema(type = "string")) @Valid @RequestParam(value = "searchstrings", required = false) String searchstrings,
            @Parameter(in = ParameterIn.QUERY, description = "Email to filter by email", schema = @Schema(type = "string")) @Valid @RequestParam(value = "Email", required = false) String Email)
    {
        return userService.getAllUsers(limit, offset, searchstrings, Email);
    }

    @PreAuthorize("hasRole('ROLE_EMPLOYEE')")
    @RequestMapping(value = "/users/{userID}", produces = {"application/json"}, method = RequestMethod.GET)
    public ResponseEntity<GetUserDTO> usersUserIDGet(@PathVariable("userID") UUID userID) {
        return userService.getUserByUserID(userID);
    }
    @PreAuthorize("hasRole('ROLE_EMPLOYEE')")
   @RequestMapping(value = "/users/{userID}", produces = {"application/json"}, method = RequestMethod.PUT)
    public ResponseEntity<GetUserDTO> usersUserIDPut(@PathVariable("userID") UUID userID, @RequestBody UpdateUserDTO updateUserDTO) throws ValidationException {
        return userService.updateUser(userID, updateUserDTO);
    }

}

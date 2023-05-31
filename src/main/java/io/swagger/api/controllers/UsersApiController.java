package io.swagger.api.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.api.UsersApi;
import io.swagger.api.service.UserService;
import io.swagger.model.DTO.CreateUserDTO;
import io.swagger.model.DTO.GetUserDTO;
import io.swagger.model.DTO.UpdateUserDTO;
import io.swagger.model.User;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Schema;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.validation.constraints.Max;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2023-05-16T13:11:00.686570329Z[GMT]")
@RestController
public class UsersApiController implements UsersApi {

    private static final Logger log = LoggerFactory.getLogger(UsersApiController.class);

    private final ObjectMapper objectMapper;

    @Autowired
    private UserService userService;
    private ModelMapper modelMapper;
    private final HttpServletRequest request;

    @org.springframework.beans.factory.annotation.Autowired
    public UsersApiController(ObjectMapper objectMapper, HttpServletRequest request) {
        this.objectMapper = objectMapper;
        this.request = request;
    }

    @RequestMapping(value = "/users", produces = {"application/json"}, method = RequestMethod.GET)
    public ResponseEntity<List<GetUserDTO>> usersGet(@Max(50) @Parameter(in = ParameterIn.QUERY, description = "The maximum number of accounts to retrieve." ,schema=@Schema(allowableValues={ "50" }, maximum="50"
            , defaultValue="10")) @Valid @RequestParam(value = "count", required = false, defaultValue="10") Integer count) {

        try{
            List<User> users = userService.getAllUsers();
            List<GetUserDTO> userDTOs = new ArrayList<>();

            for (User user : users) {
                GetUserDTO userDTO = objectMapper.convertValue(user, GetUserDTO.class);
                userDTOs.add(userDTO);
            }

            return new ResponseEntity<List<GetUserDTO>>(userDTOs, HttpStatus.OK);
        } catch (Exception e){
            log.error("Couldn't serialize response for content type application/json", e);
            return new ResponseEntity<List<GetUserDTO>>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @RequestMapping(value = "/users", produces = {"application/json"}, method = RequestMethod.POST)
    public ResponseEntity<User> usersPost(@RequestBody CreateUserDTO createUserDTO) {
        User user = objectMapper.convertValue(createUserDTO, User.class);
        User result = userService.add(user);
        return new ResponseEntity<User>(result, HttpStatus.OK);
    }


    @RequestMapping(value = "/users/{userID}", produces = {"application/json"}, method = RequestMethod.GET)
    public ResponseEntity<GetUserDTO> usersUserIDGet(@PathVariable("userID") UUID userID) {
        try{
            User user = userService.getUserByUserID(userID);
            GetUserDTO userDTO = objectMapper.convertValue(user, GetUserDTO.class);
            return new ResponseEntity<GetUserDTO>(userDTO, HttpStatus.OK);

        } catch (Exception e){
            log.error("Couldn't serialize response for content type application/json", e);
            return new ResponseEntity<GetUserDTO>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

   @RequestMapping(value = "/users/{userID}", produces = {"application/json"}, method = RequestMethod.PUT)
    public ResponseEntity<GetUserDTO> usersUserIDPut(@PathVariable("userID") UUID userID, @RequestBody UpdateUserDTO updateUserDTO) {
        User user = objectMapper.convertValue(updateUserDTO, User.class);
        User result = userService.update(userID, user);
        GetUserDTO userDTO = objectMapper.convertValue(result, GetUserDTO.class);
        return new ResponseEntity<GetUserDTO>(userDTO, HttpStatus.OK);
    }

    /*public ResponseEntity<GetUserDTO> usersUserIDPut(@Parameter(in = ParameterIn.PATH, description = "ID of the user to update", required=true, schema=@Schema()) @PathVariable("userID") UUID userID,@Parameter(in = ParameterIn.DEFAULT, description = "New user details to update for the specified user", required=true, schema=@Schema()) @Valid @RequestBody UpdateUserDTO body) {
        String accept = request.getHeader("Accept");
        if (accept != null && accept.contains("application/json")) {
            try {
                return new ResponseEntity<GetUserDTO>(objectMapper.readValue("{\n  \"Role\" : \"User\",\n  \"Active\" : true,\n  \"Email\" : \"\",\n  \"DailyLimit\" : 6.027456183070403,\n  \"UserID\" : \"046b6c7f-0b8a-43b9-b35d-6489e6daee91\",\n  \"FirstName\" : \"FirstName\",\n  \"TransactionLimit\" : 0.8008281904610115,\n  \"LastName\" : \"LastName\",\n  \"Accounts\" : [ {\n    \"Type\" : \"Current\",\n    \"Active\" : true,\n    \"AccountID\" : \"046b6c7f-0b8a-43b9-b35d-6489e6daee91\",\n    \"IBAN\" : \"IBAN\",\n    \"MinBal\" : 6.027456183070403,\n    \"UserID\" : \"046b6c7f-0b8a-43b9-b35d-6489e6daee91\",\n    \"Balance\" : 0.8008281904610115,\n    \"Name\" : \"Name\"\n  }, {\n    \"Type\" : \"Current\",\n    \"Active\" : true,\n    \"AccountID\" : \"046b6c7f-0b8a-43b9-b35d-6489e6daee91\",\n    \"IBAN\" : \"IBAN\",\n    \"MinBal\" : 6.027456183070403,\n    \"UserID\" : \"046b6c7f-0b8a-43b9-b35d-6489e6daee91\",\n    \"Balance\" : 0.8008281904610115,\n    \"Name\" : \"Name\"\n  } ]\n}", GetUserDTO.class), HttpStatus.NOT_IMPLEMENTED);
            } catch (IOException e) {
                log.error("Couldn't serialize response for content type application/json", e);
                return new ResponseEntity<GetUserDTO>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }

        return new ResponseEntity<GetUserDTO>(HttpStatus.NOT_IMPLEMENTED);
    }*/

}

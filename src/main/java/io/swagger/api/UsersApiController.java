package io.swagger.api;

import io.swagger.model.CreateUserDTO;
import io.swagger.model.GetUserDTO;
import java.util.UUID;
import io.swagger.model.UpdateUserDTO;
import io.swagger.model.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.*;
import javax.validation.Valid;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2023-05-16T13:11:00.686570329Z[GMT]")
@RestController
public class UsersApiController implements UsersApi {

    private static final Logger log = LoggerFactory.getLogger(UsersApiController.class);

    private final ObjectMapper objectMapper;

    private final HttpServletRequest request;

    @org.springframework.beans.factory.annotation.Autowired
    public UsersApiController(ObjectMapper objectMapper, HttpServletRequest request) {
        this.objectMapper = objectMapper;
        this.request = request;
    }

    public ResponseEntity<List<GetUserDTO>> usersGet( @Max(50) @Parameter(in = ParameterIn.QUERY, description = "The maximum number of accounts to retrieve." ,schema=@Schema(allowableValues={ "50" }, maximum="50"
, defaultValue="10")) @Valid @RequestParam(value = "count", required = false, defaultValue="10") Integer count) {
        String accept = request.getHeader("Accept");
        if (accept != null && accept.contains("application/json")) {
            try {
                return new ResponseEntity<List<GetUserDTO>>(objectMapper.readValue("[ {\n  \"Role\" : \"User\",\n  \"Active\" : true,\n  \"Email\" : \"\",\n  \"DailyLimit\" : 6.027456183070403,\n  \"UserID\" : \"046b6c7f-0b8a-43b9-b35d-6489e6daee91\",\n  \"FirstName\" : \"FirstName\",\n  \"TransactionLimit\" : 0.8008281904610115,\n  \"LastName\" : \"LastName\",\n  \"Accounts\" : [ {\n    \"Type\" : \"Current\",\n    \"Active\" : true,\n    \"AccountID\" : \"046b6c7f-0b8a-43b9-b35d-6489e6daee91\",\n    \"IBAN\" : \"IBAN\",\n    \"MinBal\" : 6.027456183070403,\n    \"UserID\" : \"046b6c7f-0b8a-43b9-b35d-6489e6daee91\",\n    \"Balance\" : 0.8008281904610115,\n    \"Name\" : \"Name\"\n  }, {\n    \"Type\" : \"Current\",\n    \"Active\" : true,\n    \"AccountID\" : \"046b6c7f-0b8a-43b9-b35d-6489e6daee91\",\n    \"IBAN\" : \"IBAN\",\n    \"MinBal\" : 6.027456183070403,\n    \"UserID\" : \"046b6c7f-0b8a-43b9-b35d-6489e6daee91\",\n    \"Balance\" : 0.8008281904610115,\n    \"Name\" : \"Name\"\n  } ]\n}, {\n  \"Role\" : \"User\",\n  \"Active\" : true,\n  \"Email\" : \"\",\n  \"DailyLimit\" : 6.027456183070403,\n  \"UserID\" : \"046b6c7f-0b8a-43b9-b35d-6489e6daee91\",\n  \"FirstName\" : \"FirstName\",\n  \"TransactionLimit\" : 0.8008281904610115,\n  \"LastName\" : \"LastName\",\n  \"Accounts\" : [ {\n    \"Type\" : \"Current\",\n    \"Active\" : true,\n    \"AccountID\" : \"046b6c7f-0b8a-43b9-b35d-6489e6daee91\",\n    \"IBAN\" : \"IBAN\",\n    \"MinBal\" : 6.027456183070403,\n    \"UserID\" : \"046b6c7f-0b8a-43b9-b35d-6489e6daee91\",\n    \"Balance\" : 0.8008281904610115,\n    \"Name\" : \"Name\"\n  }, {\n    \"Type\" : \"Current\",\n    \"Active\" : true,\n    \"AccountID\" : \"046b6c7f-0b8a-43b9-b35d-6489e6daee91\",\n    \"IBAN\" : \"IBAN\",\n    \"MinBal\" : 6.027456183070403,\n    \"UserID\" : \"046b6c7f-0b8a-43b9-b35d-6489e6daee91\",\n    \"Balance\" : 0.8008281904610115,\n    \"Name\" : \"Name\"\n  } ]\n} ]", List.class), HttpStatus.NOT_IMPLEMENTED);
            } catch (IOException e) {
                log.error("Couldn't serialize response for content type application/json", e);
                return new ResponseEntity<List<GetUserDTO>>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }

        return new ResponseEntity<List<GetUserDTO>>(HttpStatus.NOT_IMPLEMENTED);
    }

    public ResponseEntity<User> usersPost(@Parameter(in = ParameterIn.DEFAULT, description = "", required=true, schema=@Schema()) @Valid @RequestBody CreateUserDTO body) {
        String accept = request.getHeader("Accept");
        if (accept != null && accept.contains("application/json")) {
            try {
                return new ResponseEntity<User>(objectMapper.readValue("{\n  \"Role\" : \"User\",\n  \"Active\" : true,\n  \"Email\" : \"\",\n  \"DailyLimit\" : 6.027456183070403,\n  \"UserID\" : \"046b6c7f-0b8a-43b9-b35d-6489e6daee91\",\n  \"FirstName\" : \"FirstName\",\n  \"TransactionLimit\" : 0.8008281904610115,\n  \"LastName\" : \"LastName\",\n  \"Accounts\" : [ {\n    \"Type\" : \"Current\",\n    \"Active\" : true,\n    \"AccountID\" : \"046b6c7f-0b8a-43b9-b35d-6489e6daee91\",\n    \"IBAN\" : \"IBAN\",\n    \"MinBal\" : 6.027456183070403,\n    \"UserID\" : \"046b6c7f-0b8a-43b9-b35d-6489e6daee91\",\n    \"Balance\" : 0.8008281904610115,\n    \"Name\" : \"Name\"\n  }, {\n    \"Type\" : \"Current\",\n    \"Active\" : true,\n    \"AccountID\" : \"046b6c7f-0b8a-43b9-b35d-6489e6daee91\",\n    \"IBAN\" : \"IBAN\",\n    \"MinBal\" : 6.027456183070403,\n    \"UserID\" : \"046b6c7f-0b8a-43b9-b35d-6489e6daee91\",\n    \"Balance\" : 0.8008281904610115,\n    \"Name\" : \"Name\"\n  } ],\n  \"Password\" : \"Password\"\n}", User.class), HttpStatus.NOT_IMPLEMENTED);
            } catch (IOException e) {
                log.error("Couldn't serialize response for content type application/json", e);
                return new ResponseEntity<User>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }

        return new ResponseEntity<User>(HttpStatus.NOT_IMPLEMENTED);
    }

    public ResponseEntity<GetUserDTO> usersUserIDGet(@Parameter(in = ParameterIn.PATH, description = "The ID of the user to retrieve", required=true, schema=@Schema()) @PathVariable("userID") UUID userID) {
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
    }

    public ResponseEntity<GetUserDTO> usersUserIDPut(@Parameter(in = ParameterIn.PATH, description = "ID of the user to update", required=true, schema=@Schema()) @PathVariable("userID") UUID userID,@Parameter(in = ParameterIn.DEFAULT, description = "New user details to update for the specified user", required=true, schema=@Schema()) @Valid @RequestBody UpdateUserDTO body) {
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
    }

}

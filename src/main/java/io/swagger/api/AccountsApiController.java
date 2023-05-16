package io.swagger.api;

import io.swagger.model.Account;
import io.swagger.model.CreateAccountDTO;
import io.swagger.model.FindByUserName;
import io.swagger.model.GetAccountDTO;
import java.util.UUID;
import io.swagger.model.UpdateAccountDTO;
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
public class AccountsApiController implements AccountsApi {

    private static final Logger log = LoggerFactory.getLogger(AccountsApiController.class);

    private final ObjectMapper objectMapper;

    private final HttpServletRequest request;

    @org.springframework.beans.factory.annotation.Autowired
    public AccountsApiController(ObjectMapper objectMapper, HttpServletRequest request) {
        this.objectMapper = objectMapper;
        this.request = request;
    }

    public ResponseEntity<List<GetAccountDTO>> accountsAccountIDGet(@Parameter(in = ParameterIn.PATH, description = "ID of the account to retrieve", required=true, schema=@Schema()) @PathVariable("accountID") UUID accountID) {
        String accept = request.getHeader("Accept");
        if (accept != null && accept.contains("application/json")) {
            try {
                return new ResponseEntity<List<GetAccountDTO>>(objectMapper.readValue("[ {\n  \"Type\" : \"Current\",\n  \"Active\" : true,\n  \"AccountID\" : \"046b6c7f-0b8a-43b9-b35d-6489e6daee91\",\n  \"IBAN\" : \"IBAN\",\n  \"MinBal\" : 6.027456183070403,\n  \"UserID\" : \"046b6c7f-0b8a-43b9-b35d-6489e6daee91\",\n  \"Balance\" : 0.8008281904610115,\n  \"Name\" : \"Name\"\n}, {\n  \"Type\" : \"Current\",\n  \"Active\" : true,\n  \"AccountID\" : \"046b6c7f-0b8a-43b9-b35d-6489e6daee91\",\n  \"IBAN\" : \"IBAN\",\n  \"MinBal\" : 6.027456183070403,\n  \"UserID\" : \"046b6c7f-0b8a-43b9-b35d-6489e6daee91\",\n  \"Balance\" : 0.8008281904610115,\n  \"Name\" : \"Name\"\n} ]", List.class), HttpStatus.NOT_IMPLEMENTED);
            } catch (IOException e) {
                log.error("Couldn't serialize response for content type application/json", e);
                return new ResponseEntity<List<GetAccountDTO>>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }

        return new ResponseEntity<List<GetAccountDTO>>(HttpStatus.NOT_IMPLEMENTED);
    }

    public ResponseEntity<List<UpdateAccountDTO>> accountsAccountIDPut(@Parameter(in = ParameterIn.PATH, description = "ID of the account to update", required=true, schema=@Schema()) @PathVariable("accountID") UUID accountID,@Parameter(in = ParameterIn.DEFAULT, description = "New account details to update for the specified account", required=true, schema=@Schema()) @Valid @RequestBody UpdateAccountDTO body) {
        String accept = request.getHeader("Accept");
        if (accept != null && accept.contains("application/json")) {
            try {
                return new ResponseEntity<List<UpdateAccountDTO>>(objectMapper.readValue("[ {\n  \"Active\" : true,\n  \"MinBal\" : 6.027456183070403,\n  \"Balance\" : 0.8008281904610115,\n  \"Name\" : \"Name\"\n}, {\n  \"Active\" : true,\n  \"MinBal\" : 6.027456183070403,\n  \"Balance\" : 0.8008281904610115,\n  \"Name\" : \"Name\"\n} ]", List.class), HttpStatus.NOT_IMPLEMENTED);
            } catch (IOException e) {
                log.error("Couldn't serialize response for content type application/json", e);
                return new ResponseEntity<List<UpdateAccountDTO>>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }

        return new ResponseEntity<List<UpdateAccountDTO>>(HttpStatus.NOT_IMPLEMENTED);
    }

    public ResponseEntity<List<GetAccountDTO>> accountsGet(@Parameter(in = ParameterIn.QUERY, description = "user ID of the accounts" ,schema=@Schema()) @Valid @RequestParam(value = "userID", required = false) UUID userID, @Max(50) @Parameter(in = ParameterIn.QUERY, description = "The maximum number of accounts to retrieve." ,schema=@Schema(allowableValues={ "50" }, maximum="50"
, defaultValue="10")) @Valid @RequestParam(value = "count", required = false, defaultValue="10") Integer count) {
        String accept = request.getHeader("Accept");
        if (accept != null && accept.contains("application/json")) {
            try {
                return new ResponseEntity<List<GetAccountDTO>>(objectMapper.readValue("[ {\n  \"Type\" : \"Current\",\n  \"Active\" : true,\n  \"AccountID\" : \"046b6c7f-0b8a-43b9-b35d-6489e6daee91\",\n  \"IBAN\" : \"IBAN\",\n  \"MinBal\" : 6.027456183070403,\n  \"UserID\" : \"046b6c7f-0b8a-43b9-b35d-6489e6daee91\",\n  \"Balance\" : 0.8008281904610115,\n  \"Name\" : \"Name\"\n}, {\n  \"Type\" : \"Current\",\n  \"Active\" : true,\n  \"AccountID\" : \"046b6c7f-0b8a-43b9-b35d-6489e6daee91\",\n  \"IBAN\" : \"IBAN\",\n  \"MinBal\" : 6.027456183070403,\n  \"UserID\" : \"046b6c7f-0b8a-43b9-b35d-6489e6daee91\",\n  \"Balance\" : 0.8008281904610115,\n  \"Name\" : \"Name\"\n} ]", List.class), HttpStatus.NOT_IMPLEMENTED);
            } catch (IOException e) {
                log.error("Couldn't serialize response for content type application/json", e);
                return new ResponseEntity<List<GetAccountDTO>>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }

        return new ResponseEntity<List<GetAccountDTO>>(HttpStatus.NOT_IMPLEMENTED);
    }

    public ResponseEntity<FindByUserName> accountsNameGet(@Parameter(in = ParameterIn.PATH, description = "Account's name to search for", required=true, schema=@Schema()) @PathVariable("name") String name) {
        String accept = request.getHeader("Accept");
        if (accept != null && accept.contains("application/json")) {
            try {
                return new ResponseEntity<FindByUserName>(objectMapper.readValue("{\n  \"firstName\" : \"firstName\",\n  \"lastName\" : \"lastName\",\n  \"IBAN\" : \"IBAN\"\n}", FindByUserName.class), HttpStatus.NOT_IMPLEMENTED);
            } catch (IOException e) {
                log.error("Couldn't serialize response for content type application/json", e);
                return new ResponseEntity<FindByUserName>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }

        return new ResponseEntity<FindByUserName>(HttpStatus.NOT_IMPLEMENTED);
    }

    public ResponseEntity<Account> accountsPost(@Parameter(in = ParameterIn.DEFAULT, description = "", required=true, schema=@Schema()) @Valid @RequestBody CreateAccountDTO body) {
        String accept = request.getHeader("Accept");
        if (accept != null && accept.contains("application/json")) {
            try {
                return new ResponseEntity<Account>(objectMapper.readValue("{\n  \"Type\" : \"Current\",\n  \"Active\" : true,\n  \"AccountID\" : \"046b6c7f-0b8a-43b9-b35d-6489e6daee91\",\n  \"IBAN\" : \"IBAN\",\n  \"MinBal\" : 6.027456183070403,\n  \"UserID\" : \"046b6c7f-0b8a-43b9-b35d-6489e6daee91\",\n  \"Balance\" : 0.8008281904610115,\n  \"Name\" : \"Name\"\n}", Account.class), HttpStatus.NOT_IMPLEMENTED);
            } catch (IOException e) {
                log.error("Couldn't serialize response for content type application/json", e);
                return new ResponseEntity<Account>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }

        return new ResponseEntity<Account>(HttpStatus.NOT_IMPLEMENTED);
    }

}

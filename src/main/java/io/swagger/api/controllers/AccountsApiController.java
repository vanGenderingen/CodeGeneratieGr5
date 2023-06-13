package io.swagger.api.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.api.AccountsApi;
import io.swagger.api.service.AccountService;
import io.swagger.api.service.UserService;
import io.swagger.api.service.ValidationService;
import io.swagger.model.Account;
import io.swagger.model.DTO.CreateAccountDTO;
import io.swagger.model.DTO.GetAccountDTO;
import io.swagger.model.DTO.UpdateAccountDTO;
import io.swagger.model.User;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Schema;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@CrossOrigin(origins = "*")
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2023-05-16T13:11:00.686570329Z[GMT]")
@RestController
public class AccountsApiController implements AccountsApi {

    //TODO: make sure that only a user can only access their own accounts

    @Autowired
    private final ObjectMapper objectMapper;

    @Autowired
    private AccountService accountService;

    @Autowired
    private UserService userService;
    private final HttpServletRequest request;

    @org.springframework.beans.factory.annotation.Autowired
    public AccountsApiController(ObjectMapper objectMapper, HttpServletRequest request) {
        this.objectMapper = objectMapper;
        this.request = request;
    }

    @PreAuthorize("hasRole('ROLE_EMPLOYEE')")
    @RequestMapping(value = "/accounts", produces = {"application/json"}, method = RequestMethod.POST)
    public ResponseEntity<Account> accountsPost(@RequestBody CreateAccountDTO createAccountDTO) {
        return new ResponseEntity<>(accountService.add(createAccountDTO), HttpStatus.CREATED);
    }

    @PreAuthorize("hasRole('ROLE_EMPLOYEE')")
    @RequestMapping(value = "/accounts", produces = {"application/json"}, method = RequestMethod.GET)
    public ResponseEntity<List<GetAccountDTO>> accountsGet(
            @Parameter(in = ParameterIn.QUERY, description = "The maximum number of accounts to retrieve.", schema = @Schema(allowableValues = {"0", "50"}, type = "integer", defaultValue = "20", maximum = "50")) @Valid @RequestParam(value = "limit", required = false, defaultValue = "10") Integer limit,
            @Parameter(in = ParameterIn.QUERY, description = "The offset for paginated results.", schema = @Schema(type = "integer", defaultValue = "0", minimum = "0")) @Valid @RequestParam(value = "offset", required = false, defaultValue = "0") Integer offset,
            @Parameter(in = ParameterIn.QUERY, description = "Comma-separated list of search strings to filter accounts.", schema = @Schema(type = "string")) @Valid @RequestParam(value = "searchstrings", required = false) String searchstrings,
            @Parameter(in = ParameterIn.QUERY, description = "IBAN to filter accounts.", schema = @Schema(type = "string")) @Valid @RequestParam(value = "IBAN", required = false) String IBAN)
    {
        return new ResponseEntity<>(accountService.getAllAccounts(limit, offset, searchstrings, IBAN), HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('ROLE_USER')")
    @RequestMapping(value = "/accounts/{accountID}", produces = {"application/json"}, method = RequestMethod.GET)
    public ResponseEntity<GetAccountDTO> getAccountByAccountID(
            @Parameter(in = ParameterIn.PATH, description = "ID of the account to retrieve", required=true, schema=@Schema())
            @PathVariable("accountID") UUID accountID,
            Principal principal
    ) {
        //Get the requested account from the database
        GetAccountDTO getAccountDTO = accountService.getAccountByAccountID(accountID);

        //Validate the user is authorized to access the account
        ValidationService.validateAccountGetAndPutAccess(getAccountDTO.getAccountID(), principal);

        // Return the account if the user is authorized
        return new ResponseEntity<>(getAccountDTO, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @RequestMapping(value = "/accounts/user/{userId}/accounts", produces = {"application/json"}, method = RequestMethod.GET)
    public ResponseEntity<List<GetAccountDTO>> accountsUserUserIdAccountsGet(
            @Parameter(in = ParameterIn.PATH, description = "ID of the user whose accounts to retrieve", required = true, schema = @Schema(type = "string", format = "uuid"))
            @PathVariable("userId") UUID userId,
            @Parameter(in = ParameterIn.QUERY, description = "The maximum number of accounts to retrieve.", schema = @Schema(type = "integer", defaultValue = "10", maximum = "50"))
            @Valid @RequestParam(value = "limit", required = false, defaultValue = "10") Integer limit,
            @Parameter(in = ParameterIn.QUERY, description = "The offset for paginated results.", schema = @Schema(type = "integer", defaultValue = "0", minimum = "0"))
            @Valid @RequestParam(value = "offset", required = false, defaultValue = "0") Integer offset,
            @Parameter(in = ParameterIn.QUERY, description = "Comma-separated list of search strings to filter accounts.", schema = @Schema(type = "string"))
            @Valid @RequestParam(value = "searchstrings", required = false) String searchstrings
    ) {
        return new ResponseEntity<>(accountService.getAccountsOfUser(userId, limit, offset, searchstrings), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @RequestMapping(value = "/accounts/{accountID}", produces = {"application/json"}, method = RequestMethod.PUT)
    public ResponseEntity<GetAccountDTO> accountsAccountIDPut(
            @Parameter(in = ParameterIn.PATH, description = "ID of the account to update", required=true, schema=@Schema())
            @PathVariable("accountID") UUID accountID,
            @Parameter(in = ParameterIn.DEFAULT, description = "New account details to update for the specified account", required=true, schema=@Schema()) @Valid
            @RequestBody UpdateAccountDTO updateAccountDTO,
            Principal principal
    ){
        //Get the requested account from the database
        GetAccountDTO getAccountDTO = accountService.updateAccount(accountID, updateAccountDTO);

        //Validate the user is authorized to access the account
        ValidationService.validateAccountGetAndPutAccess(getAccountDTO.getAccountID(), principal);

        // Return the account if the user is authorized
        return new ResponseEntity<>(getAccountDTO, HttpStatus.OK);
    }
}
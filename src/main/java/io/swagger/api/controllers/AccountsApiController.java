package io.swagger.api.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.api.AccountsApi;
import io.swagger.api.service.AccountService;
import io.swagger.api.service.UserService;
import io.swagger.model.Account;
import io.swagger.model.DTO.CreateAccountDTO;
import io.swagger.model.DTO.GetAccountDTO;
import io.swagger.model.DTO.UpdateAccountDTO;
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
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2023-05-16T13:11:00.686570329Z[GMT]")
@RestController
public class AccountsApiController implements AccountsApi {

    private static final Logger log = LoggerFactory.getLogger(AccountsApiController.class);

    private final ObjectMapper objectMapper;

    @Autowired
    private AccountService accountService;

    @Autowired
    private UserService userService;
    private ModelMapper modelMapper;
    private final HttpServletRequest request;

    @org.springframework.beans.factory.annotation.Autowired
    public AccountsApiController(ObjectMapper objectMapper, HttpServletRequest request) {
        this.objectMapper = objectMapper;
        this.request = request;
    }

    @RequestMapping(value = "/accounts", produces = {"application/json"}, method = RequestMethod.POST)
    public ResponseEntity<Account> accountsPost(@RequestBody CreateAccountDTO createAccountDTO) {
        Account account = objectMapper.convertValue(createAccountDTO, Account.class);
        Account result = accountService.add(account);
        return new ResponseEntity<Account>(result, HttpStatus.OK);
    }

    @RequestMapping(value = "/accounts", produces = {"application/json"}, method = RequestMethod.GET)
    public ResponseEntity<List<GetAccountDTO>> accountsGet(
            @Parameter(in = ParameterIn.QUERY, description = "The maximum number of accounts to retrieve.", schema = @Schema(type = "integer", defaultValue = "10", maximum = "50"))
            @Valid @RequestParam(value = "limit", required = false, defaultValue = "10") Integer limit,
            @Parameter(in = ParameterIn.QUERY, description = "The offset for paginated results.", schema = @Schema(type = "integer", defaultValue = "0", minimum = "0"))
            @Valid @RequestParam(value = "offset", required = false, defaultValue = "0") Integer offset,
            @Parameter(in = ParameterIn.QUERY, description = "Comma-separated list of search strings to filter accounts.", schema = @Schema(type = "string"))
            @Valid @RequestParam(value = "searchstrings", required = false) String searchstrings
    ) {
        try {
            List<Account> accounts = new ArrayList<>();
            List<GetAccountDTO> accountDTOS = new ArrayList<>();
            accounts = accountService.getAllAccounts();
            for (Account account : accounts) {
                GetAccountDTO accountDTO = objectMapper.convertValue(account, GetAccountDTO.class);
                accountDTOS.add(accountDTO);
            }
            return new ResponseEntity<List<GetAccountDTO>>(accountDTOS, HttpStatus.OK);
        } catch (Exception e) {
            log.error("Couldn't serialize response for content type application/json", e);
            return new ResponseEntity<List<GetAccountDTO>>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(value = "/accounts/{accountID}", produces = {"application/json"}, method = RequestMethod.GET)
    public ResponseEntity<GetAccountDTO> accountsAccountIDGet(@Parameter(in = ParameterIn.PATH, description = "ID of the account to retrieve", required=true, schema=@Schema()) @PathVariable("accountID") UUID accountID) {
        try {
            Account account = accountService.getAccountByAccountID(accountID);
            GetAccountDTO accountDTO = objectMapper.convertValue(account, GetAccountDTO.class);
            return new ResponseEntity<GetAccountDTO>(accountDTO, HttpStatus.OK);

        } catch (Exception e) {
            log.error("Couldn't serialize response for content type application/json", e);
            return new ResponseEntity<GetAccountDTO>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(value = "/accounts/user/{userID}/accounts", produces = {"application/json"}, method = RequestMethod.GET)
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
        try {
            List<Account> accounts = accountService.getAccountsOfUser(userId, limit, offset, searchstrings).getContent();
            List<GetAccountDTO> accountDTOS = new ArrayList<>();
            for (Account account : accounts) {
                GetAccountDTO accountDTO = objectMapper.convertValue(account, GetAccountDTO.class);
                accountDTOS.add(accountDTO);
            }
            return new ResponseEntity<List<GetAccountDTO>>(accountDTOS, HttpStatus.OK);
        } catch (Exception e) {
            log.error("Couldn't serialize response for content type application/json", e);
            return new ResponseEntity<List<GetAccountDTO>>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(value = "/accounts/{accountID}", produces = {"application/json"}, method = RequestMethod.PUT)
    public ResponseEntity<GetAccountDTO> accountsAccountIDPut(@Parameter(in = ParameterIn.PATH, description = "ID of the account to update", required=true, schema=@Schema()) @PathVariable("accountID") UUID accountID, @Parameter(in = ParameterIn.DEFAULT, description = "New account details to update for the specified account", required=true, schema=@Schema()) @Valid @RequestBody UpdateAccountDTO updateAccountDTO) {
        try {
            Account account = objectMapper.convertValue(updateAccountDTO, Account.class);
            Account result = accountService.update(updateAccountDTO, accountID);
            GetAccountDTO accountDTO = objectMapper.convertValue(result, GetAccountDTO.class);
            return new ResponseEntity<>(accountDTO, HttpStatus.OK);
        } catch (Exception e) {
            log.error("Couldn't serialize response for content type application/json", e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}

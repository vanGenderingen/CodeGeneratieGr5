package io.swagger.api.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.api.AccountsApi;
import io.swagger.api.service.AccountService;
import io.swagger.api.service.UserService;
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
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@CrossOrigin(origins = "*")
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2023-05-16T13:11:00.686570329Z[GMT]")
@RestController
public class AccountsApiController implements AccountsApi {
    private static final Logger log = LoggerFactory.getLogger(AccountsApiController.class);

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

    @Autowired
    public AccountsApiController(UserService userService, AccountService accountService, ObjectMapper objectMapper, HttpServletRequest request) {
        this.userService = userService;
        this.accountService = accountService;
        this.objectMapper = objectMapper;
        this.request = request;
    }

    @PreAuthorize("hasRole('ROLE_EMPLOYEE')")
    @RequestMapping(value = "/accounts", produces = {"application/json"}, method = RequestMethod.POST)
    public ResponseEntity<Account> accountsPost(@RequestBody CreateAccountDTO createAccountDTO) {
        UUID userId = createAccountDTO.getUserId();
        User user = userService.getUserByUserID(userId);

        if (user == null) {
            // Handle the case when the user does not exist
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        Account account = objectMapper.convertValue(createAccountDTO, Account.class);
        account.setUser(user);
        Account result = accountService.add(account);

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_EMPLOYEE')")
    @RequestMapping(value = "/accounts", produces = {"application/json"}, method = RequestMethod.GET)
    public ResponseEntity<List<GetAccountDTO>> accountsGet(
            @Parameter(in = ParameterIn.QUERY, description = "The maximum number of accounts to retrieve.", schema = @Schema(allowableValues = {"0", "50"}, type = "integer", defaultValue = "20", maximum = "50")) @Valid @RequestParam(value = "limit", required = false, defaultValue = "10") Integer limit,
            @Parameter(in = ParameterIn.QUERY, description = "The offset for paginated results.", schema = @Schema(type = "integer", defaultValue = "0", minimum = "0")) @Valid @RequestParam(value = "offset", required = false, defaultValue = "0") Integer offset,
            @Parameter(in = ParameterIn.QUERY, description = "Comma-separated list of search strings to filter accounts.", schema = @Schema(type = "string")) @Valid @RequestParam(value = "searchstrings", required = false) String searchstrings,
            @Parameter(in = ParameterIn.QUERY, description = "IBAN to filter accounts.", schema = @Schema(type = "string")) @Valid @RequestParam(value = "IBAN", required = false) String IBAN)
    {
        try {
            List<Account> accounts = new ArrayList<>();
            List<GetAccountDTO> accountDTOS = new ArrayList<>();
            accounts = accountService.getAllAccounts(limit, offset, searchstrings, IBAN);
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

    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_EMPLOYEE')")
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
        try {
            List<Account> accounts = accountService.getAccountsOfUser(userId, limit, offset, searchstrings).getContent();
            List<GetAccountDTO> accountDTOS = new ArrayList<>();
            for (Account account : accounts) {
                GetAccountDTO accountDTO = objectMapper.convertValue(account, GetAccountDTO.class);
                accountDTOS.add(accountDTO);
            }
            int totalAccounts = accountService.getTotalPages(userId, searchstrings);
            return ResponseEntity.ok()
                    .header("X-Total-Accounts", String.valueOf(totalAccounts))
                    .body(accountDTOS);
        } catch (Exception e) {
            log.error("Couldn't serialize response for content type application/json", e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PreAuthorize("hasRole('ROLE_USER')")
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

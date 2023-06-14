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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.security.Principal;
import java.util.List;
import java.util.UUID;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/accounts")
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

    @Override
    @PreAuthorize("hasRole('ROLE_EMPLOYEE')")
    @PostMapping(produces = "application/json")
    public ResponseEntity<Account> createAccount(@Valid @RequestBody CreateAccountDTO createAccountDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(accountService.add(createAccountDTO));    }

    @Override
    @PreAuthorize("hasRole('ROLE_EMPLOYEE')")
    @GetMapping(produces = "application/json")
    public ResponseEntity<List<GetAccountDTO>> getAccounts(
            @Valid @RequestParam(value = "limit", defaultValue = "10") Integer limit,
            @Valid @RequestParam(value = "offset", defaultValue = "0") Integer offset,
            @Valid @RequestParam(value = "searchstrings", required = false) String searchStrings,
            @Valid @RequestParam(value = "IBAN", required = false) String iban)
    {
        return ResponseEntity.ok(accountService.getAllAccounts(limit, offset, searchStrings, iban));
    }

    @Override
    @PreAuthorize("hasAnyRole('ROLE_USER')")
    @GetMapping(value = "/{accountID}", produces = "application/json")
    public ResponseEntity<GetAccountDTO> getAccountById(
            @Valid @PathVariable("accountID") UUID accountID,
            Principal principal
    ) {
        //Get the requested account from the database
        GetAccountDTO getAccountDTO = accountService.getAccountByAccountID(accountID);

        //Validate the user is authorized to access the account
        ValidationService.validateAccountAndUserGetAccess(getAccountDTO.getUserID(), principal);

        // Return the account if the user is authorized
        return ResponseEntity.ok(getAccountDTO);
    }

    @Override
    @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping(value = "/user/{userId}/accounts", produces = "application/json")
    public ResponseEntity<List<GetAccountDTO>> getUserAccounts(
            @Valid @PathVariable("userId") UUID userId,
            @Valid @RequestParam(value = "limit", defaultValue = "10") Integer limit,
            @Valid @RequestParam(value = "offset", defaultValue = "0") Integer offset,
            @Valid @RequestParam(value = "searchstrings", required = false) String searchStrings,
            Principal principal
    ) {
        //Validate the user is authorized to access the account
        ValidationService.validateAccountAndUserGetAccess(userId, principal);

        //Get the requested accounts from the database
        List<GetAccountDTO> getAccountDTOList = accountService.getAccountsOfUser(userId, limit, offset, searchStrings);

        // Return the accounts if the user is authorized
        return ResponseEntity.ok(getAccountDTOList);
    }

    @Override
    @PreAuthorize("hasRole('ROLE_USER')")
    @PutMapping(value = "/{accountID}", produces = "application/json")
    public ResponseEntity<GetAccountDTO> updateAccount(
            @Valid @PathVariable("accountID") UUID accountID,
            @Valid @RequestBody UpdateAccountDTO updateAccountDTO,
            Principal principal
    ){
        //Get the requested account from the database
        GetAccountDTO getAccountDTO = accountService.updateAccount(accountID, updateAccountDTO);

        //Validate the user is authorized to access the account
        ValidationService.validateAccountPutAccess(getAccountDTO.getUserID(), getAccountDTO.getIban(), principal);

        // Return the account if the user is authorized
        return new ResponseEntity<>(getAccountDTO, HttpStatus.OK);
    }
}
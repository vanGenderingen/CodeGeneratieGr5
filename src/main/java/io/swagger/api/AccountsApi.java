package io.swagger.api;

import io.swagger.model.Account;
import io.swagger.model.DTO.CreateAccountDTO;
import io.swagger.model.DTO.GetAccountDTO;
import io.swagger.model.DTO.UpdateAccountDTO;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.security.Principal;
import java.util.List;
import java.util.UUID;

@Validated
@RequestMapping("/accounts")
@SecurityRequirement(name = "JWTAuth")
public interface AccountsApi {
    @PostMapping
    ResponseEntity<Account> createAccount(@Valid @RequestBody CreateAccountDTO createAccountDTO);

    @GetMapping
    ResponseEntity<List<GetAccountDTO>> getAccounts(
            @Valid @RequestParam(value = "limit", defaultValue = "10") @Min(0) @Max(50) Integer limit,
            @Valid @RequestParam(value = "offset", defaultValue = "0") @Min(0) Integer offset,
            @Valid @RequestParam(value = "searchstrings", required = false) String searchStrings,
            @Valid @RequestParam(value = "IBAN", required = false) String iban
    );

    @GetMapping("/{accountID}")
    ResponseEntity<GetAccountDTO> getAccountById(
            @Valid @PathVariable("accountID") UUID accountID,
            Principal principal
    );

    @GetMapping("/user/{userId}/accounts")
    ResponseEntity<List<GetAccountDTO>> getUserAccounts(
            @Valid @PathVariable("userId") UUID userId,
            @Valid @RequestParam(value = "limit", defaultValue = "10") @Min(0) @Max(50) Integer limit,
            @Valid @RequestParam(value = "offset", defaultValue = "0") @Min(0) Integer offset,
            @Valid @RequestParam(value = "searchstrings", required = false) String searchStrings,
            Principal principal
    );

    @PutMapping("/{accountID}")
    ResponseEntity<GetAccountDTO> updateAccount(
            @Valid @PathVariable("accountID") UUID accountID,
            @RequestBody UpdateAccountDTO updateAccountDTO,
            Principal principal
    );
}
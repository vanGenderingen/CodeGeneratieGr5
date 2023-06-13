/**
 * NOTE: This class is auto generated by the swagger code generator program (3.0.42).
 * https://github.com/swagger-api/swagger-codegen
 * Do not edit the class manually.
 */
package io.swagger.api;

import io.swagger.model.Account;
import io.swagger.model.DTO.CreateAccountDTO;
import io.swagger.model.DTO.GetAccountDTO;

import java.security.Principal;
import java.util.UUID;
import io.swagger.model.DTO.UpdateAccountDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import java.util.List;

@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2023-05-16T13:11:00.686570329Z[GMT]")
@Validated
public interface AccountsApi {

    // security = {
    //    @SecurityRequirement(name = "JWTAuth")},

    @Operation(summary = "Create Account", description = "Endpoint for creating a new account.", security = {
            @SecurityRequirement(name = "JWTAuth")}, tags = { "Accounts", "Employees" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Account created successfully.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Account.class))),
            @ApiResponse(responseCode = "400", description = "Invalid request body."),
            @ApiResponse(responseCode = "401", description = "Access token is missing or invalid"),
            @ApiResponse(responseCode = "403", description = "Forbidden. The client does not have access")})
    @RequestMapping(value = "/accounts",
            produces = { "application/json" },
            consumes = { "application/json" },
            method = RequestMethod.POST)
    ResponseEntity<Account> accountsPost(@Parameter(in = ParameterIn.DEFAULT, description = "", required=true, schema=@Schema()) @Valid @RequestBody CreateAccountDTO body);


    @Operation(summary = "Get accounts", description = "Retrieves a list of accounts.", security = {
            @SecurityRequirement(name = "JWTAuth")}, tags = { "Accounts", "Employees", "Customers" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation", content = @Content(mediaType = "application/json", schema = @Schema(implementation = GetAccountDTO.class))),
            @ApiResponse(responseCode = "400", description = "Invalid request body."),
            @ApiResponse(responseCode = "401", description = "Access token is missing or invalid"),
            @ApiResponse(responseCode = "403", description = "Forbidden. The client does not have access")})
    @RequestMapping(value = "/accounts",
            produces = { "application/json" },
            method = RequestMethod.GET)
    ResponseEntity<List<GetAccountDTO>> accountsGet(@Parameter(in = ParameterIn.QUERY, description = "The maximum number of accounts to retrieve.", schema=@Schema(type = "integer", defaultValue = "10", maximum = "50")) @Valid @RequestParam(value = "limit", required = false, defaultValue = "10") Integer limit, @Parameter(in = ParameterIn.QUERY, description = "The offset for paginated results.", schema=@Schema(type = "integer", defaultValue = "0", minimum = "0")) @Valid @RequestParam(value = "offset", required = false, defaultValue = "0") Integer offset, @Parameter(in = ParameterIn.QUERY, description = "Comma-separated list of search strings to filter accounts.", schema=@Schema(type = "string")) @Valid @RequestParam(value = "searchstrings", required = false) String searchstrings, @Parameter(in = ParameterIn.QUERY, description = "IBAN to filter accounts.", schema=@Schema(type = "string")) @Valid @RequestParam(value = "IBAN", required = false) String IBAN);

    @Operation(summary = "Get a single account by ID", description = "Retrieve information for a single account based on its unique identifier", security = {
            @SecurityRequirement(name = "JWTAuth")}, tags = { "Accounts", "Employees", "Customers" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK", content = @Content(mediaType = "application/json", schema = @Schema(implementation = GetAccountDTO.class))),
            @ApiResponse(responseCode = "400", description = "Invalid request body."),
            @ApiResponse(responseCode = "401", description = "Access token is missing or invalid"),
            @ApiResponse(responseCode = "403", description = "Forbidden. The client does not have access")})
    @RequestMapping(value = "/accounts/{accountID}",
            produces = { "application/json" },
            method = RequestMethod.GET)
    ResponseEntity<GetAccountDTO> getAccountByAccountID(
            @Parameter(in = ParameterIn.PATH, description = "ID of the account to retrieve", required = true, schema=@Schema(type = "string", format = "uuid"))
            @PathVariable("accountID") UUID accountID,
            Principal principal
    );
    @Operation(summary = "Get accounts for a specific user", description = "", security = {
            @SecurityRequirement(name = "JWTAuth")}, tags = { "Accounts", "Employees", "Customers" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK", content = @Content(mediaType = "application/json", schema = @Schema(implementation = GetAccountDTO.class))),
            @ApiResponse(responseCode = "400", description = "Invalid request body."),
            @ApiResponse(responseCode = "401", description = "Access token is missing or invalid"),
            @ApiResponse(responseCode = "403", description = "Forbidden. The client does not have access")})
    @RequestMapping(value = "/accounts/user/{userId}/accounts",
            produces = { "application/json" },
            method = RequestMethod.GET)
    ResponseEntity<List<GetAccountDTO>> getAccountsOfUser(@Parameter(in = ParameterIn.PATH, description = "ID of the user whose accounts to retrieve", required = true, schema=@Schema(type = "string", format = "uuid")) @PathVariable("userId") UUID userId, @Parameter(in = ParameterIn.QUERY, description = "The maximum number of accounts to retrieve.", schema=@Schema(type = "integer", defaultValue = "10", maximum = "50")) @Valid @RequestParam(value = "limit", required = false, defaultValue = "10") Integer limit, @Parameter(in = ParameterIn.QUERY, description = "The offset for paginated results.", schema=@Schema(type = "integer", defaultValue = "0", minimum = "0")) @Valid @RequestParam(value = "offset", required = false, defaultValue = "0") Integer offset, @Parameter(in = ParameterIn.QUERY, description = "Comma-separated list of search strings to filter accounts.", schema=@Schema(type = "string")) @Valid @RequestParam(value = "searchstrings", required = false) String searchstrings);

    @Operation(summary = "Update account details", description = "Update account details for a specific account based on its unique identifier", security = {
            @SecurityRequirement(name = "JWTAuth")}, tags = { "Accounts", "Employees", "Customers" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK", content = @Content(mediaType = "application/json", schema = @Schema(implementation = UpdateAccountDTO.class))),
            @ApiResponse(responseCode = "400", description = "Invalid request body."),
            @ApiResponse(responseCode = "401", description = "Access token is missing or invalid"),
            @ApiResponse(responseCode = "403", description = "Forbidden. The client does not have access")})
    @RequestMapping(value = "/accounts/{accountID}",
            produces = { "application/json" },
            consumes = { "application/json" },
            method = RequestMethod.PUT)
    ResponseEntity<GetAccountDTO> accountsAccountIDPut(
            @Parameter(in = ParameterIn.PATH, description = "ID of the account to update", required = true, schema=@Schema(type = "string", format = "uuid"))
            @PathVariable("accountID") UUID accountID, @Parameter(in = ParameterIn.DEFAULT, description = "New account details to update for the specified account", required=true, schema=@Schema()) @Valid
            @RequestBody UpdateAccountDTO body,
            Principal principal);
}
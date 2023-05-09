/**
 * NOTE: This class is auto generated by the swagger code generator program (3.0.42).
 * https://github.com/swagger-api/swagger-codegen
 * Do not edit the class manually.
 */
package io.swagger.api;

import io.swagger.model.Account;
import io.swagger.model.CreateAccountDTO;
import io.swagger.model.FindByUserName;
import io.swagger.model.GetAccountDTO;
import java.util.UUID;
import io.swagger.model.UpdateAccountDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.bind.annotation.CookieValue;

import javax.validation.Valid;
import javax.validation.constraints.*;
import java.util.List;
import java.util.Map;

@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2023-05-09T16:01:34.487688617Z[GMT]")
@Validated
public interface AccountsApi {

    @Operation(summary = "Get a single account by ID", description = "Retrieve information for a single account based on its unique identifier", security = {
        @SecurityRequirement(name = "JWTAuth")    }, tags={ "Accounts", "Employees", "Customers" })
    @ApiResponses(value = { 
        @ApiResponse(responseCode = "200", description = "OK", content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = GetAccountDTO.class)))),
        
        @ApiResponse(responseCode = "400", description = "Invalid request body."),
        
        @ApiResponse(responseCode = "401", description = "Unauthorized. The user does not have permission to perform this action."),
        
        @ApiResponse(responseCode = "500", description = "Internal server error.") })
    @RequestMapping(value = "/accounts/{accountID}",
        produces = { "application/json" }, 
        method = RequestMethod.GET)
    ResponseEntity<List<GetAccountDTO>> accountsAccountIDGet(@Parameter(in = ParameterIn.PATH, description = "ID of the account to retrieve", required=true, schema=@Schema()) @PathVariable("accountID") UUID accountID);


    @Operation(summary = "Update account details", description = "Update account details for a specific account based on its unique identifier", security = {
        @SecurityRequirement(name = "JWTAuth")    }, tags={ "Accounts", "Employees", "Customers" })
    @ApiResponses(value = { 
        @ApiResponse(responseCode = "200", description = "OK", content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = UpdateAccountDTO.class)))),
        
        @ApiResponse(responseCode = "400", description = "Invalid request body."),
        
        @ApiResponse(responseCode = "401", description = "Unauthorized. The user does not have permission to perform this action."),
        
        @ApiResponse(responseCode = "500", description = "Internal server error.") })
    @RequestMapping(value = "/accounts/{accountID}",
        produces = { "application/json" }, 
        consumes = { "application/json" }, 
        method = RequestMethod.PUT)
    ResponseEntity<List<UpdateAccountDTO>> accountsAccountIDPut(@Parameter(in = ParameterIn.PATH, description = "ID of the account to update", required=true, schema=@Schema()) @PathVariable("accountID") UUID accountID, @Parameter(in = ParameterIn.DEFAULT, description = "New account details to update for the specified account", required=true, schema=@Schema()) @Valid @RequestBody UpdateAccountDTO body);


    @Operation(summary = "Get accounts", description = "Retrieves a list of accounts.", security = {
        @SecurityRequirement(name = "JWTAuth")    }, tags={ "Accounts", "Employees", "Customers" })
    @ApiResponses(value = { 
        @ApiResponse(responseCode = "200", description = "Successful operation", content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = GetAccountDTO.class)))),
        
        @ApiResponse(responseCode = "400", description = "Invalid request body."),
        
        @ApiResponse(responseCode = "401", description = "Unauthorized. The user does not have permission to perform this action."),
        
        @ApiResponse(responseCode = "500", description = "Internal server error.") })
    @RequestMapping(value = "/accounts",
        produces = { "application/json" }, 
        method = RequestMethod.GET)
    ResponseEntity<List<GetAccountDTO>> accountsGet(@Parameter(in = ParameterIn.QUERY, description = "user ID of the accounts" ,schema=@Schema()) @Valid @RequestParam(value = "userID", required = false) UUID userID,  @Max(50) @Parameter(in = ParameterIn.QUERY, description = "The maximum number of accounts to retrieve." ,schema=@Schema(allowableValues={ "50" }, maximum="50"
, defaultValue="10")) @Valid @RequestParam(value = "count", required = false, defaultValue="10") Integer count);


    @Operation(summary = "Get account by name", description = "", security = {
        @SecurityRequirement(name = "JWTAuth")    }, tags={ "Accounts", "Customers", "Employees" })
    @ApiResponses(value = { 
        @ApiResponse(responseCode = "200", description = "Account found", content = @Content(mediaType = "application/json", schema = @Schema(implementation = FindByUserName.class))),
        
        @ApiResponse(responseCode = "400", description = "Invalid request body."),
        
        @ApiResponse(responseCode = "401", description = "Unauthorized. The user does not have permission to perform this action."),
        
        @ApiResponse(responseCode = "500", description = "Internal server error.") })
    @RequestMapping(value = "/accounts/{name}",
        produces = { "application/json" }, 
        method = RequestMethod.GET)
    ResponseEntity<FindByUserName> accountsNameGet(@Parameter(in = ParameterIn.PATH, description = "Account's name to search for", required=true, schema=@Schema()) @PathVariable("name") String name);


    @Operation(summary = "Create Account", description = "Endpoint for creating a new account.", security = {
        @SecurityRequirement(name = "JWTAuth")    }, tags={ "Accounts", "Employees" })
    @ApiResponses(value = { 
        @ApiResponse(responseCode = "201", description = "Account created successfully.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Account.class))),
        
        @ApiResponse(responseCode = "400", description = "Invalid request body."),
        
        @ApiResponse(responseCode = "401", description = "Unauthorized. The user does not have permission to perform this action."),
        
        @ApiResponse(responseCode = "500", description = "Internal server error.") })
    @RequestMapping(value = "/accounts",
        produces = { "application/json" }, 
        consumes = { "application/json" }, 
        method = RequestMethod.POST)
    ResponseEntity<Account> accountsPost(@Parameter(in = ParameterIn.DEFAULT, description = "", required=true, schema=@Schema()) @Valid @RequestBody CreateAccountDTO body);

}


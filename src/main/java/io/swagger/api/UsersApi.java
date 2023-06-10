/**
 * NOTE: This class is auto generated by the swagger code generator program (3.0.42).
 * https://github.com/swagger-api/swagger-codegen
 * Do not edit the class manually.
 */
package io.swagger.api;

import io.swagger.model.DTO.CreateUserDTO;
import io.swagger.model.DTO.GetUserDTO;
import io.swagger.model.DTO.UpdateUserDTO;
import io.swagger.model.User;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2023-05-16T13:11:00.686570329Z[GMT]")
@Validated
public interface UsersApi {

    @Operation(summary = "Get users", description = "Retrieves a list of users.", security = {
        @SecurityRequirement(name = "JWTAuth")    }, tags={ "Users", "Employees" })
    @ApiResponses(value = { 
        @ApiResponse(responseCode = "200", description = "Successful operation", content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = GetUserDTO.class)))),
        
        @ApiResponse(responseCode = "400", description = "Invalid request body."),
        
        @ApiResponse(responseCode = "401", description = "Unauthorized. The user does not have permission to perform this action."),
        
        @ApiResponse(responseCode = "500", description = "Internal server error.") })
    @RequestMapping(value = "/users",
        produces = { "application/json" }, 
        method = RequestMethod.GET)
    ResponseEntity<List<GetUserDTO>> usersGet( @Parameter(in = ParameterIn.QUERY, description = "The maximum number of accounts to retrieve.", schema = @Schema(allowableValues = {"0", "50"}, type = "integer", defaultValue = "20", maximum = "50")) @Valid @RequestParam(value = "limit", required = false, defaultValue = "10") Integer limit,
                                               @Parameter(in = ParameterIn.QUERY, description = "The offset for paginated results.", schema = @Schema(type = "integer", defaultValue = "0", minimum = "0")) @Valid @RequestParam(value = "offset", required = false, defaultValue = "0") Integer offset,
                                               @Parameter(in = ParameterIn.QUERY, description = "Comma-separated list of search strings to filter accounts.", schema = @Schema(type = "string")) @Valid @RequestParam(value = "searchstrings", required = false) String searchstrings,
                                               @Parameter(in = ParameterIn.QUERY, description = "Email to filter by email", schema = @Schema(type = "string")) @Valid @RequestParam(value = "Email", required = false) String Email);


    @Operation(summary = "Create User", description = "Endpoint for creating a new user.", tags={ "Users", "Customers", "Employees" })
    @ApiResponses(value = { 
        @ApiResponse(responseCode = "201", description = "Account created successfully.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = User.class))),
        
        @ApiResponse(responseCode = "400", description = "Invalid request body."),
        
        @ApiResponse(responseCode = "401", description = "Unauthorized. The user does not have permission to perform this action."),
        
        @ApiResponse(responseCode = "500", description = "Internal server error.") })
    @RequestMapping(value = "/users",
        produces = { "application/json" }, 
        consumes = { "application/json" }, 
        method = RequestMethod.POST)
    ResponseEntity<User> usersPost(@Parameter(in = ParameterIn.DEFAULT, description = "", required=true, schema=@Schema()) @Valid @RequestBody CreateUserDTO body);


    @Operation(summary = "Get User by ID", description = "", security = {
        @SecurityRequirement(name = "JWTAuth")    }, tags={ "Users", "Customers", "Employees" })
    @ApiResponses(value = { 
        @ApiResponse(responseCode = "200", description = "OK", content = @Content(mediaType = "application/json", schema = @Schema(implementation = GetUserDTO.class))),
        
        @ApiResponse(responseCode = "400", description = "Invalid request body."),
        
        @ApiResponse(responseCode = "401", description = "Unauthorized. The user does not have permission to perform this action."),
        
        @ApiResponse(responseCode = "500", description = "Internal server error.") })
    @RequestMapping(value = "/users/{userID}",
        produces = { "application/json" }, 
        method = RequestMethod.GET)
    ResponseEntity<GetUserDTO> usersUserIDGet(@Parameter(in = ParameterIn.PATH, description = "The ID of the user to retrieve", required=true, schema=@Schema()) @PathVariable("userID") UUID userID);


    @Operation(summary = "Update a user by ID", description = "", security = {
        @SecurityRequirement(name = "JWTAuth")    }, tags={ "Users", "Employees" })
    @ApiResponses(value = { 
        @ApiResponse(responseCode = "200", description = "User updated successfully", content = @Content(mediaType = "application/json", schema = @Schema(implementation = GetUserDTO.class))),
        
        @ApiResponse(responseCode = "401", description = "Unauthorized access"),
        
        @ApiResponse(responseCode = "404", description = "User not found"),
        
        @ApiResponse(responseCode = "500", description = "Internal server error") })
    @RequestMapping(value = "/users/{userID}",
        produces = { "application/json" }, 
        consumes = { "application/json" }, 
        method = RequestMethod.PUT)
    ResponseEntity<GetUserDTO> usersUserIDPut(@Parameter(in = ParameterIn.PATH, description = "ID of the user to update", required=true, schema=@Schema()) @PathVariable("userID") UUID userID, @Parameter(in = ParameterIn.DEFAULT, description = "New user details to update for the specified user", required=true, schema=@Schema()) @Valid @RequestBody UpdateUserDTO body);

}


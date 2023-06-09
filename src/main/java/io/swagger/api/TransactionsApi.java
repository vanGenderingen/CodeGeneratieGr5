/**
 * NOTE: This class is auto generated by the swagger code generator program (3.0.42).
 * https://github.com/swagger-api/swagger-codegen
 * Do not edit the class manually.
 */
package io.swagger.api;

import io.swagger.model.DTO.AmountFilter;
import io.swagger.model.DTO.CreateTransactionDTO;
import io.swagger.model.DTO.IBANFilter;
import io.swagger.model.Transaction;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
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

@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2023-05-16T13:11:00.686570329Z[GMT]")
@Validated
public interface TransactionsApi {


    //security = {
    //            @SecurityRequirement(name = "JWTAuth")}
    @Operation(summary = "Retrieve transactions", description = "", tags = {"Transactions", "Customers", "Employees"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Transaction.class))),
            @ApiResponse(responseCode = "400", description = "Bad request"),
            @ApiResponse(responseCode = "401", description = "Unauthorized. No authentication"),
            @ApiResponse(responseCode = "403", description = "Forbidden. The client does not have access")})
    @RequestMapping(value = "/transactions",
            produces = {"application/json"},
            method = RequestMethod.GET)
    ResponseEntity<List<Transaction>> getTransactions(
            @Parameter(in = ParameterIn.QUERY, description = "The offset for paginated results.", schema=@Schema(type = "integer", defaultValue = "0", minimum = "0")) @Valid @RequestParam(value = "offset", required = false, defaultValue = "0") Integer offset,
            @Parameter(in = ParameterIn.QUERY, description = "The maximum number of transactions to retrieve.", schema = @Schema(allowableValues = {"0", "100"}, maximum = "100", defaultValue = "20")) @Valid @RequestParam(value = "limit", required = false, defaultValue = "20") Integer limit,
            @Parameter(in = ParameterIn.QUERY, description = "Retrieve transactions of a transaction type.", schema = @Schema(allowableValues = {"withdraw", "deposit"})) @Valid @RequestParam(value = "transactionType", required = false) String transactionType,
            @Parameter(in = ParameterIn.QUERY, description = "Filter on account on or toIBAN, fromIBAN, or accountID", schema = @Schema()) @Valid @ModelAttribute(value = "accountFilter") IBANFilter accountFilter,
            @Parameter(in = ParameterIn.QUERY, description = "Retrieve transactions that are filtered on the amount.", schema = @Schema()) @Valid @ModelAttribute(value = "amountFilter") AmountFilter amountFilter);

    @Operation(summary = "Create a transaction", description = "", security = {
            @SecurityRequirement(name = "JWTAuth")}, tags = {"Transactions", "Customers", "Employees"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Created", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Transaction.class))),
            @ApiResponse(responseCode = "400", description = "Bad request"),
            @ApiResponse(responseCode = "401", description = "Access token is missing or invalid"),
            @ApiResponse(responseCode = "403", description = "Forbidden. The client does not have access")})
    @RequestMapping(value = "/transactions",
            produces = {"application/json"},
            consumes = {"application/json"},
            method = RequestMethod.POST)
    ResponseEntity<Transaction> postTransactions(@Parameter(in = ParameterIn.DEFAULT, description = "", required = true, schema = @Schema()) @Valid @RequestBody CreateTransactionDTO body);

}


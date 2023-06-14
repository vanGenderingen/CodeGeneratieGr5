package io.swagger.api.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.api.service.TransactionService;
import io.swagger.api.specification.SearchCriteria;
import io.swagger.model.DTO.CreateTransactionDTO;
import io.swagger.model.transactions.Transaction;
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
import java.security.Principal;
import java.util.List;
import java.util.UUID;

@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2023-05-16T13:11:00.686570329Z[GMT]")
@CrossOrigin(origins = "*")
@RestController
public class TransactionsApiController {

    private static final Logger log = LoggerFactory.getLogger(TransactionsApiController.class);

    @Autowired
    private final ObjectMapper objectMapper;

    @Autowired
    private TransactionService transactionService;

    private final HttpServletRequest request;


    @org.springframework.beans.factory.annotation.Autowired
    public TransactionsApiController(ObjectMapper objectMapper, HttpServletRequest request, TransactionService transactionService){
        this.transactionService = transactionService;
        this.objectMapper = objectMapper;
        this.request = request;
    }

//    @PreAuthorize("hasRole('ROLE_USER')")
    @RequestMapping(value = "/transactions",
            produces = {"application/json"},
            method = RequestMethod.GET)
    public ResponseEntity<List<Transaction>> getTransactions(
            @Parameter(in = ParameterIn.QUERY, description = "The offset for paginated results.", schema=@Schema(type = "integer", defaultValue = "0", minimum = "0")) @Valid @RequestParam(value = "offset", required = false, defaultValue = "0") Integer offset,
            @Parameter(in = ParameterIn.QUERY, description = "The maximum number of transactions to retrieve.", schema = @Schema(allowableValues = {"0", "100"}, maximum = "100", defaultValue = "20")) @Valid @RequestParam(value = "limit", required = false, defaultValue = "20") Integer limit,
            @Parameter(in = ParameterIn.QUERY, description = "Retrieve transactions of a transaction type.", schema = @Schema(allowableValues = {"withdraw", "deposit"})) @Valid @RequestParam(value = "transactionType", required = false) String transactionType,
            @Parameter(in = ParameterIn.QUERY, description = "Filter transactions", schema = @Schema()) @Valid @ModelAttribute(value = "filters") SearchCriteria filters
            ){
        List<Transaction> transactions = transactionService.getTransactions(offset,limit, filters, request.getUserPrincipal());
        return new ResponseEntity<List<Transaction>>(transactions, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @RequestMapping(value = "/transactions",
            produces = {"application/json"},
            consumes = {"application/json"},
            method = RequestMethod.POST)
    public ResponseEntity<Transaction> postTransactions(@RequestBody CreateTransactionDTO body) {
        try{
            Principal principal = request.getUserPrincipal();
            Transaction transaction = objectMapper.convertValue(body, Transaction.class);
            transaction.setUserPerforming(UUID.fromString(principal.getName()));

            Transaction result = transactionService.add(transaction);
            return new ResponseEntity<Transaction>(result, HttpStatus.OK);
        }catch (Exception e) {
            return new ResponseEntity<Transaction>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}

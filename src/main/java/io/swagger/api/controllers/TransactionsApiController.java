package io.swagger.api.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.api.TransactionsApi;
import io.swagger.api.service.TransactionService;
import io.swagger.model.DTO.CreateTransactionDTO;
import io.swagger.model.Transaction;
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
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2023-05-16T13:11:00.686570329Z[GMT]")
@RestController
public class TransactionsApiController implements TransactionsApi {

    private static final Logger log = LoggerFactory.getLogger(TransactionsApiController.class);


    private final ObjectMapper objectMapper;

    @Autowired
    private TransactionService transactionService;

    private ModelMapper modelMapper;

    private final HttpServletRequest request;


    @org.springframework.beans.factory.annotation.Autowired
    public TransactionsApiController(ObjectMapper objectMapper, HttpServletRequest request) {
        this.objectMapper = objectMapper;
        this.request = request;
    }

    @RequestMapping(value = "/transactions",
            produces = {"application/json"},
            method = RequestMethod.GET)
    public ResponseEntity<List<Transaction>> getTransactions(
            @Parameter(in = ParameterIn.QUERY, description = "ID of the user", schema = @Schema()) @Valid @RequestParam(value = "userID", required = false) UUID userID, @Min(0) @Max(100)
            @Parameter(in = ParameterIn.QUERY, description = "The maximum number of transactions to retrieve.", schema = @Schema(allowableValues = {"0", "100"}, maximum = "100", defaultValue = "20")) @Valid @RequestParam(value = "count", required = false, defaultValue = "20") Integer count,
            @Parameter(in = ParameterIn.QUERY, description = "The date range of the transactions to retrieve.", schema = @Schema()) @Valid @RequestParam(value = "dateRange", required = false) OffsetDateTime dateRange,
            @Parameter(in = ParameterIn.QUERY, description = "The the IBAN from who the transaction is done.", schema = @Schema()) @Valid @RequestParam(value = "from", required = false) String from,
            @Parameter(in = ParameterIn.QUERY, description = "The the IBAN to who the transaction is done.", schema = @Schema()) @Valid @RequestParam(value = "to", required = false) String to, @DecimalMin("0")
            @Parameter(in = ParameterIn.QUERY, description = "Retrieve transactions that are lower than number.", schema = @Schema()) @Valid @RequestParam(value = "lower", required = false) Double lower, @DecimalMin("0")
            @Parameter(in = ParameterIn.QUERY, description = "Retrieve transactions that are higher than number.", schema = @Schema()) @Valid @RequestParam(value = "higher", required = false) Double higher, @DecimalMin("0")
            @Parameter(in = ParameterIn.QUERY, description = "Retrieve transactions that are equal than number.", schema = @Schema()) @Valid @RequestParam(value = "equal", required = false) Double equal,
            @Parameter(in = ParameterIn.QUERY, description = "Retrieve transactions of a specific account.", schema = @Schema()) @Valid @RequestParam(value = "account", required = false) UUID account,
            @Parameter(in = ParameterIn.QUERY, description = "Retrieve transactions of a transaction type.", schema = @Schema(allowableValues = {"withdraw", "deposit"})) @Valid @RequestParam(value = "transactionType", required = false) String transactionType) {
        try {
            List<Transaction> transactions = new ArrayList<>();
            transactions = transactionService.getAllTransactions();
            return new ResponseEntity<List<Transaction>>(transactions, HttpStatus.OK);

        } catch (Exception e) {
            log.error("Couldn't serialize response for content type application/json", e);
            return new ResponseEntity<List<Transaction>>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(value = "/transactions",
            produces = {"application/json"},
            consumes = {"application/json"},
            method = RequestMethod.POST)
    public ResponseEntity<Transaction> postTransactions(@RequestBody CreateTransactionDTO body) {
        Transaction transaction = objectMapper.convertValue(body, Transaction.class);
        Transaction result = transactionService.add(transaction);
        return new ResponseEntity<Transaction>(result, HttpStatus.OK);
    }

    public ResponseEntity<List<Transaction>> transactionsGet(
            @Parameter(in = ParameterIn.QUERY, description = "ID of the user", schema = @Schema()) @Valid @RequestParam(value = "userID", required = false) UUID userID, @Min(0) @Max(100)
    @Parameter(in = ParameterIn.QUERY, description = "The maximum number of transactions to retrieve.", schema = @Schema(allowableValues = {"0", "100"}, maximum = "100", defaultValue = "20")) @Valid @RequestParam(value = "count", required = false, defaultValue = "20") Integer count,
            @Parameter(in = ParameterIn.QUERY, description = "The date range of the transactions to retrieve.", schema = @Schema()) @Valid @RequestParam(value = "dateRange", required = false) OffsetDateTime dateRange,
            @Parameter(in = ParameterIn.QUERY, description = "The the IBAN from who the transaction is done.", schema = @Schema()) @Valid @RequestParam(value = "from", required = false) String from,
            @Parameter(in = ParameterIn.QUERY, description = "The the IBAN to who the transaction is done.", schema = @Schema()) @Valid @RequestParam(value = "to", required = false) String to, @DecimalMin("0")
            @Parameter(in = ParameterIn.QUERY, description = "Retrieve transactions that are lower than number.", schema = @Schema()) @Valid @RequestParam(value = "lower", required = false) Double lower, @DecimalMin("0")
            @Parameter(in = ParameterIn.QUERY, description = "Retrieve transactions that are higher than number.", schema = @Schema()) @Valid @RequestParam(value = "higher", required = false) Double higher, @DecimalMin("0")
            @Parameter(in = ParameterIn.QUERY, description = "Retrieve transactions that are equal than number.", schema = @Schema()) @Valid @RequestParam(value = "equal", required = false) Double equal,
            @Parameter(in = ParameterIn.QUERY, description = "Retrieve transactions of a specific account.", schema = @Schema()) @Valid @RequestParam(value = "account", required = false) UUID account,
            @Parameter(in = ParameterIn.QUERY, description = "Retrieve transactions of a transaction type.", schema = @Schema(allowableValues = {"withdraw", "deposit"})) @Valid @RequestParam(value = "transactionType", required = false) String transactionType) {

        String accept = request.getHeader("Accept");
        if (accept != null && accept.contains("application/json")) {
            try {
                List<Transaction> transactions = new ArrayList<>();
                transactions = transactionService.getAllTransactions();
                return new ResponseEntity<List<Transaction>>(transactions, HttpStatus.OK);
            } catch (Exception e) {
                log.error("Couldn't serialize response for content type application/json", e);
                return new ResponseEntity<List<Transaction>>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }

        return new ResponseEntity<List<Transaction>>(HttpStatus.NOT_IMPLEMENTED);
    }
//
//    public ResponseEntity<Transaction> transactionsPost(@Parameter(in = ParameterIn.DEFAULT, description = "", required=true, schema=@Schema()) @Valid @RequestBody CreateTransactionDTO body) {
//        String accept = request.getHeader("Accept");
//        if (accept != null && accept.contains("application/json")) {
//            try {
//                return new ResponseEntity<Transaction>(objectMapper.readValue("{\n  \"transactionType\" : \"Deposit\",\n  \"timeStamp\" : \"2000-01-23T04:56:07.000+00:00\",\n  \"amount\" : 123.12,\n  \"userPerforming\" : \"046b6c7f-0b8a-43b9-b35d-6489e6daee91\",\n  \"description\" : \"description\",\n  \"from\" : \"NL01INHO0000000001\",\n  \"to\" : \"NL01INHO0000000001\",\n  \"transactionID\" : \"046b6c7f-0b8a-43b9-b35d-6489e6daee91\"\n}", Transaction.class), HttpStatus.NOT_IMPLEMENTED);
//            } catch (IOException e) {
//                log.error("Couldn't serialize response for content type application/json", e);
//                return new ResponseEntity<Transaction>(HttpStatus.INTERNAL_SERVER_ERROR);
//            }
//        }
//
//        return new ResponseEntity<Transaction>(HttpStatus.NOT_IMPLEMENTED);
//    }

}

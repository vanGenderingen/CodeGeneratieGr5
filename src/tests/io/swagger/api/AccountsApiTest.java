package io.swagger.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.api.controllers.AccountsApiController;
import io.swagger.model.Account;
import io.swagger.model.DTO.CreateAccountDTO;
import io.swagger.model.DTO.GetAccountDTO;
import io.swagger.model.DTO.UpdateAccountDTO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;

import java.security.Principal;
import java.util.List;
import java.util.UUID;

import static org.mockito.Mockito.when;

public class AccountsApiTest {

    @Mock
    AccountsApiController accountsApiController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);    }

    @Test
    public void testAccountsPost() {
        // Create test data
        CreateAccountDTO createAccountDTO = new CreateAccountDTO();

        // Set the mock behaviour of the accountsApiController

        when(accountsApiController.createAccount(createAccountDTO)).thenReturn(new ResponseEntity<Account>(HttpStatus.CREATED));

        // Call the accountsPost method
        ResponseEntity<Account> response = accountsApiController.createAccount(createAccountDTO);

        // Verify the response
        Assertions.assertEquals(HttpStatus.CREATED, response.getStatusCode());
        Account account = response.getBody();
        // Verify the properties of the account object
    }

    @Test
    public void testAccountsGet() {
        // Create test data
        Integer limit = 10;
        Integer offset = 0;
        String searchstrings = "";
        String iban = "";

        // Set the mock behaviour of the accountsApiController
        when(accountsApiController.getAccounts(limit, offset, searchstrings, iban)).thenReturn(new ResponseEntity<List<GetAccountDTO>>(HttpStatus.OK));

        // Call the accountsGet method
        ResponseEntity<List<GetAccountDTO>> response = accountsApiController.getAccounts(limit, offset, searchstrings, iban);

        // Verify the response
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        List<GetAccountDTO> accounts = response.getBody();
        // Verify the properties of the accounts list
    }

    @Test
    public void testAccountsAccountIDGet() {
        // Create test data
        UUID accountID = UUID.randomUUID();
        Principal principal = Mockito.mock(Principal.class);

        // Set the mock behaviour of the accountsApiController
        when(accountsApiController.getAccountById(accountID, principal)).thenReturn(new ResponseEntity<GetAccountDTO>(HttpStatus.OK));

        // Call the accountsAccountIDGet method
        ResponseEntity<GetAccountDTO> response = accountsApiController.getAccountById(accountID, principal);

        // Verify the response
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        GetAccountDTO account = response.getBody();
        // Verify the properties of the account object
    }

    @Test
    public void testAccountsUserUserIdAccountsGet() {
        // Create test data
        UUID userId = UUID.randomUUID();
        Integer limit = 10;
        Integer offset = 0;
        String searchstrings = "";
        Principal principal = Mockito.mock(Principal.class);

        // Set the mock behaviour of the accountsApiController
        when(accountsApiController.getUserAccounts(userId, limit, offset, searchstrings, principal)).thenReturn(new ResponseEntity<List<GetAccountDTO>>(HttpStatus.OK));

        // Call the accountsUserUserIdAccountsGet method
        ResponseEntity<List<GetAccountDTO>> response = accountsApiController.getUserAccounts(userId, limit, offset, searchstrings, principal);

        // Verify the response
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());

        // Verify the properties of the accounts list
        List<GetAccountDTO> accounts = response.getBody();
    }

    @Test
    public void testAccountsAccountIDPut() {
        // Create test data
        UUID accountID = UUID.randomUUID();
        UpdateAccountDTO updateAccountDTO = new UpdateAccountDTO();
        Principal principal = Mockito.mock(Principal.class);

        // Set the mock behaviour of the accountsApiController
        when(accountsApiController.updateAccount(accountID, updateAccountDTO, principal)).thenReturn(new ResponseEntity<GetAccountDTO>(HttpStatus.OK));

        // Call the accountsAccountIDPut method
        ResponseEntity<GetAccountDTO> response = accountsApiController.updateAccount(accountID, updateAccountDTO, principal);

        // Verify the response
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        GetAccountDTO account = response.getBody();
        // Verify the properties of the account object
    }
}

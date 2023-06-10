package io.swagger.api.controllers;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.api.controllers.AccountsApiController;
import io.swagger.api.exceptions.ValidationException;
import io.swagger.model.DTO.CreateUserDTO;
import io.swagger.model.Role;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import io.swagger.api.service.AccountService;
import io.swagger.api.service.UserService;
import io.swagger.model.Account;
import io.swagger.model.DTO.CreateAccountDTO;
import io.swagger.model.DTO.GetAccountDTO;
import io.swagger.model.DTO.UpdateAccountDTO;
import io.swagger.model.User;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import javax.servlet.http.HttpServletRequest;

@ExtendWith(MockitoExtension.class)
public class AccountsApiControllerTest {

    @Mock
    private AccountService accountService;

    @Mock
    private UserService userService;

    @Mock
    private ObjectMapper objectMapper;

    @InjectMocks
    private AccountsApiController accountsApiController;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);

        // Create a mock for the ObjectMapper
        ObjectMapper objectMapper = mock(ObjectMapper.class);
        // Create a mock for the HttpServletRequest
        HttpServletRequest request = mock(HttpServletRequest.class);

        accountsApiController = new AccountsApiController(userService, accountService, objectMapper, request);
    }
    @Test
    void testAccountsPost() {
        User user = new User(UUID.fromString("bb0cc36d-69a7-471e-a665-3609bc14c27a"), "Test", "Account", "testaccount@mail.nl", "password", Arrays.asList(Role.ROLE_USER), true, new ArrayList<>(), 1000.00, 10000.00);
        Account bankAccount = new Account(UUID.randomUUID(), user, user.getUserID(), "test account", "NL01INHO0000000001", 9999999999999999.00, Account.TypeEnum.CURRENT, -9999999999999999.00, true);
        CreateAccountDTO createAccountDTO = new CreateAccountDTO("test account2", 100.00, CreateAccountDTO.TypeEnum.CURRENT, 1000.00, UUID.randomUUID());

        // Configure the mock userService to return the mock account when saving
        when(userService.getUserByUserID(any(UUID.class))).thenReturn(user);

        // Configure the mock accountService to return the mock account when saving
        when(accountService.add(any(Account.class))).thenReturn(bankAccount);

        // Mock the behavior of objectMapper.convertValue to return the bankAccount directly
        when(objectMapper.convertValue(any(CreateAccountDTO.class), eq(Account.class))).thenReturn(bankAccount);

        // Invoke the accountsPost method and assert the response
        ResponseEntity<Account> response = accountsApiController.accountsPost(createAccountDTO);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(bankAccount.getAccountID(), response.getBody().getAccountID());
        assertEquals(user.getUserID(), response.getBody().getUserID());

        // Verify that the accountService saveAccount method was called with the expected account
        verify(accountService).add(any(Account.class));
    }

    @Test
    public void testAccountsGet() {
        int limit = 10;
        int offset = 0;
        String searchstrings = null;
        String IBAN = null;

        List<Account> accounts = new ArrayList<>();
        accounts.add(new Account());

        when(accountService.getAllAccounts(limit, offset, searchstrings, IBAN)).thenReturn(accounts);

        ResponseEntity<List<GetAccountDTO>> responseEntity = accountsApiController.accountsGet(limit, offset,
                searchstrings, IBAN);

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody()).hasSize(1);

        verify(accountService, times(1)).getAllAccounts(limit, offset, searchstrings, IBAN);
    }

    @Test
    public void testAccountsAccountIDGet() {
        UUID accountId = UUID.randomUUID();
        Account account = new Account();
        account.setAccountID(accountId);

        when(accountService.getAccountByAccountID(accountId)).thenReturn(account);

        ResponseEntity<GetAccountDTO> responseEntity = accountsApiController.accountsAccountIDGet(accountId);

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody()).isNotNull();

        verify(accountService, times(1)).getAccountByAccountID(accountId);
    }

    @Test
    public void testAccountsUserUserIdAccountsGet() {
        UUID userId = UUID.randomUUID();
        int limit = 10;
        int offset = 0;
        String searchstrings = null;

        List<Account> accounts = new ArrayList<>();
        accounts.add(new Account());

        when(accountService.getAccountsOfUser(userId, limit, offset, searchstrings)).thenReturn((Page<Account>) accounts);
        when(accountService.getTotalPages(userId, searchstrings)).thenReturn(1);

        ResponseEntity<List<GetAccountDTO>> responseEntity = accountsApiController.accountsUserUserIdAccountsGet(userId,
                limit, offset, searchstrings);

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody()).hasSize(1);
        assertThat(responseEntity.getHeaders().get("X-Total-Accounts")).containsExactly("1");

        verify(accountService, times(1)).getAccountsOfUser(userId, limit, offset, searchstrings);
        verify(accountService, times(1)).getTotalPages(userId, searchstrings);
    }

    @Test
    public void testAccountsAccountIDPut() throws ValidationException {
        UUID accountId = UUID.randomUUID();
        UpdateAccountDTO updateAccountDTO = new UpdateAccountDTO();
        Account updatedAccount = new Account();

        when(accountService.update(eq(updateAccountDTO), eq(accountId))).thenReturn(updatedAccount);

        ResponseEntity<GetAccountDTO> responseEntity = accountsApiController.accountsAccountIDPut(accountId,
                updateAccountDTO);

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody()).isNotNull();

        verify(accountService, times(1)).update(eq(updateAccountDTO), eq(accountId));
    }
}
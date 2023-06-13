package io.swagger.api.controllers;

import io.swagger.api.service.AccountService;
import io.swagger.api.service.ValidationService;
import io.swagger.model.Account;
import io.swagger.model.DTO.CreateAccountDTO;
import io.swagger.model.DTO.GetAccountDTO;
import io.swagger.model.DTO.UpdateAccountDTO;
import io.swagger.model.Role;
import io.swagger.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AccountsApiControllerTest {

    @Mock
    private AccountService accountService;

    @Mock
    private ValidationService validationService;

    @InjectMocks
    private AccountsApiController accountsApiController;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }
    @Test
    void testAccountsPost() {
        User user = new User(UUID.fromString("bb0cc36d-69a7-471e-a665-3609bc14c27a"), "Test", "Account", "testaccount@mail.nl", "password", Arrays.asList(Role.ROLE_USER), true, new ArrayList<>(), 1000.00, 10000.00);
        Account bankAccount = new Account(UUID.randomUUID(), user, user.getUserID(), "test account", "NL01INHO0000000001", 9999999999999999.00, Account.TypeEnum.CURRENT, -9999999999999999.00, true);
        CreateAccountDTO createAccountDTO = new CreateAccountDTO("test account2", 100.00, CreateAccountDTO.TypeEnum.CURRENT, 1000.00, UUID.randomUUID());

        when(accountService.add(any(CreateAccountDTO.class))).thenReturn(bankAccount);

        ResponseEntity<Account> response = accountsApiController.accountsPost(createAccountDTO);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(bankAccount);

        verify(accountService).add(any(CreateAccountDTO.class));
    }

    @Test
    void testAccountsGet() {
        int limit = 10;
        int offset = 0;
        String searchstrings = null;
        String IBAN = null;

        UUID accountId = UUID.randomUUID();
        GetAccountDTO account = new GetAccountDTO();
        account.setAccountID(accountId);

        List<GetAccountDTO> accounts = new ArrayList<>();
        accounts.add(account);

        when(accountService.getAllAccounts(limit, offset, searchstrings, IBAN)).thenReturn(accounts);

        ResponseEntity<List<GetAccountDTO>> responseEntity = accountsApiController.accountsGet(limit, offset,
                searchstrings, IBAN);

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody()).hasSize(1);
        assertThat(responseEntity.getBody().get(0).getAccountID()).isEqualTo(account.getAccountID());

        verify(accountService, times(1)).getAllAccounts(limit, offset, searchstrings, IBAN);
    }

    @Test
    void testAccountsAccountIDGet() {
        UUID accountId = UUID.randomUUID();
        GetAccountDTO account = new GetAccountDTO();
        account.setAccountID(accountId);
        Principal principal = accountId::toString;

        doNothing().when(validationService).validateAccountGetAndPutAccess(accountId, principal);
        when(accountService.getAccountByAccountID(accountId)).thenReturn(account);

        ResponseEntity<GetAccountDTO> responseEntity = accountsApiController.getAccountByAccountID(accountId, principal);

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody().getAccountID()).isEqualTo(account.getAccountID());

        verify(accountService, times(1)).getAccountByAccountID(accountId);
    }

    @Test
    void testAccountsUserUserIdAccountsGet() {
        int limit = 10;
        int offset = 0;
        String searchstrings = null;

        UUID userId = UUID.randomUUID();
        GetAccountDTO account = new GetAccountDTO();
        account.setUserID(userId);

        List<GetAccountDTO> accounts = new ArrayList<>();
        accounts.add(account);

        when(accountService.getAccountsOfUser(userId, limit, offset, searchstrings)).thenReturn(accounts);

        ResponseEntity<List<GetAccountDTO>> responseEntity = accountsApiController.accountsUserUserIdAccountsGet(userId, limit, offset, searchstrings);

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody()).hasSize(1);
        assertThat(responseEntity.getBody().get(0).getUserID()).isEqualTo(account.getUserID());

        verify(accountService, times(1)).getAccountsOfUser(userId, limit, offset, searchstrings);
    }

    @Test
    void testAccountsAccountIDPut() {
        UUID accountId = UUID.randomUUID();
        UpdateAccountDTO updateAccountDTO = new UpdateAccountDTO();
        updateAccountDTO.setName("test");
        Principal principal = accountId::toString;

        GetAccountDTO account = new GetAccountDTO();
        account.setAccountID(accountId);

        when(accountService.updateAccount(accountId, updateAccountDTO)).thenReturn(account);

        ResponseEntity<GetAccountDTO> responseEntity = accountsApiController.accountsAccountIDPut(accountId, updateAccountDTO, principal);

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody().getAccountID()).isEqualTo(accountId);

        verify(accountService, times(1)).updateAccount(accountId, updateAccountDTO);
    }
}
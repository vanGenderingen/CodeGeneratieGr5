package io.swagger.api.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.api.repository.AccountRepository;
import io.swagger.api.repository.UserRepository;
import io.swagger.model.Account;
import io.swagger.model.DTO.CreateAccountDTO;
import io.swagger.model.DTO.GetAccountDTO;
import io.swagger.model.DTO.GetUserDTO;
import io.swagger.model.DTO.UpdateAccountDTO;
import io.swagger.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AccountServiceTest {

    @Mock
    private AccountRepository accountRepository;
    @Mock
    private UserRepository userRepository;

    @Mock
    private ObjectMapper objectMapper;

    @Mock
    private UserService userService;

    @InjectMocks
    private AccountService accountService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testAdd() {
        UUID userId = UUID.fromString("bb0cc36d-69a7-471e-a665-3609bc14c27a");
        User user = new User(userId, "Test", "Account", "testaccount@mail.nl", "password", new ArrayList<>(), true, new ArrayList<>(), 1000.00, 10000.00);
        Account bankAccount = new Account(UUID.randomUUID(), user, user.getUserID(), "test account", "NL01INHO0000000001", 9999999999999999.00, Account.TypeEnum.CURRENT, -9999999999999999.00, true);
        CreateAccountDTO createAccountDTO = new CreateAccountDTO("test account2", 100.00, CreateAccountDTO.TypeEnum.CURRENT, 1000.00, userId);

        GetUserDTO userDTO = new GetUserDTO();
        userDTO.setUserID(userId);

        when(userRepository.getUserByUserID(any(UUID.class))).thenReturn(user);
        when(objectMapper.convertValue(any(CreateAccountDTO.class), eq(Account.class))).thenReturn(bankAccount);
        when(accountRepository.save(any(Account.class))).thenReturn(bankAccount);

        Account response = accountService.add(createAccountDTO);

        assertThat(response).isEqualTo(bankAccount);

        verify(userRepository).getUserByUserID(any(UUID.class));
        verify(accountRepository).save(any(Account.class));
    }

    @Test
    public void testAdd_NullCreateAccountDTO() {
        CreateAccountDTO createAccountDTO = null;

        // Invoke the method and assert that it throws an IllegalArgumentException
        assertThrows(ResponseStatusException.class, () -> accountService.add(createAccountDTO));
    }

    @Test
    public void testAdd_NullUserID() {
        CreateAccountDTO createAccountDTO = new CreateAccountDTO("test account2", 100.00, CreateAccountDTO.TypeEnum.CURRENT, 1000.00, null);

        // Invoke the method and assert that it throws an IllegalArgumentException
        assertThrows(ResponseStatusException.class, () -> accountService.add(createAccountDTO));
    }

    @Test
    public void testGetAllAccounts() {
        Integer limit = 10;
        Integer offset = 0;
        String searchStrings = null;
        String IBAN = null;

        UUID accountId = UUID.randomUUID();
        GetAccountDTO accountDTO = new GetAccountDTO();
        accountDTO.setAccountID(accountId);

        List<Account> accounts = new ArrayList<>();
        Account account = new Account();
        account.setAccountID(accountId);
        accounts.add(account);

        when(accountRepository.getAll(
                ArgumentMatchers.<String>any(),
                ArgumentMatchers.<String>any(),
                ArgumentMatchers.<Pageable>any()
        )).thenReturn(accounts);

        when(objectMapper.convertValue(
                Mockito.any(Account.class),
                Mockito.eq(GetAccountDTO.class)
        )).thenReturn(accountDTO);

        List<GetAccountDTO> response = accountService.getAllAccounts(limit, offset, searchStrings, IBAN);

        assertThat(response).hasSize(1);
        assertThat(response.get(0).getAccountID()).isEqualTo(accountId);

        verify(accountRepository, times(1)).getAll(
                ArgumentMatchers.<String>any(),
                ArgumentMatchers.<String>any(),
                ArgumentMatchers.<Pageable>any()
        );
    }
    @Test
    public void testGetAccountByAccountID() {
        UUID accountId = UUID.randomUUID();
        GetAccountDTO accountDTO = new GetAccountDTO();
        accountDTO.setAccountID(accountId);
        Account account = new Account();
        account.setAccountID(accountId);

        when(accountRepository.getAccountByAccountID(accountId)).thenReturn(account);
        when(objectMapper.convertValue(any(Account.class), eq(GetAccountDTO.class))).thenReturn(accountDTO);

        GetAccountDTO response = accountService.getAccountByAccountID(accountId);

        assertThat(response).isEqualTo(accountDTO);
        assertThat(response.getAccountID()).isEqualTo(accountId);

        verify(accountRepository, times(1)).getAccountByAccountID(accountId);
    }

    @Test
    public void testUpdateAccount() {
        UUID accountId = UUID.randomUUID();
        UpdateAccountDTO updateAccountDTO = new UpdateAccountDTO();
        updateAccountDTO.setName("test");

        GetAccountDTO accountDTO = new GetAccountDTO();
        accountDTO.setAccountID(accountId);

        Account existingAccount = new Account();
        existingAccount.setAccountID(accountId);
        when(accountRepository.getAccountByAccountID(accountId)).thenReturn(existingAccount);
        when(accountRepository.save(any(Account.class))).thenReturn(existingAccount);
        when(objectMapper.convertValue(any(Account.class), eq(GetAccountDTO.class))).thenReturn(accountDTO);

        GetAccountDTO response = accountService.updateAccount(accountId, updateAccountDTO);

        assertThat(response.getAccountID()).isEqualTo(accountId);

        verify(accountRepository, times(1)).getAccountByAccountID(accountId);
        verify(accountRepository, times(1)).save(any(Account.class));
    }

    @Test
    public void testUpdateAccount_NonexistentAccountID() {
        UUID accountId = UUID.randomUUID();
        UpdateAccountDTO updateAccountDTO = new UpdateAccountDTO();
        updateAccountDTO.setName("test");

        when(accountRepository.getAccountByAccountID(accountId)).thenReturn(null);

        // Invoke the method and assert that it throws a ResponseStatusException with HTTP status 404
        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> accountService.updateAccount(accountId, updateAccountDTO));
        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatus());
        assertEquals("This account does not exist", exception.getReason());

        verify(accountRepository, times(1)).getAccountByAccountID(accountId);
    }
}
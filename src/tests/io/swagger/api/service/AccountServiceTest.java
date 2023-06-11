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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
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

        ResponseEntity<Account> response = accountService.add(createAccountDTO);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(bankAccount);

        verify(userRepository).getUserByUserID(any(UUID.class));
        verify(accountRepository).save(any(Account.class));
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

        ResponseEntity<List<GetAccountDTO>> responseEntity = accountService.getAllAccounts(limit, offset, searchStrings, IBAN);

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody()).hasSize(1);
        assertThat(responseEntity.getBody().get(0).getAccountID()).isEqualTo(accountId);

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

            ResponseEntity<GetAccountDTO> responseEntity = accountService.getAccountByAccountID(accountId);

            assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
            assertThat(responseEntity.getBody().getAccountID()).isEqualTo(accountId);

            verify(accountRepository, times(1)).getAccountByAccountID(accountId);
        }

        @Test
        public void testGetAccountsOfUser() {
            Integer limit = 10;
            Integer offset = 0;
            String searchStrings = null;

            UUID userId = UUID.randomUUID();
            GetAccountDTO accountDTO = new GetAccountDTO();
            accountDTO.setUserID(userId);

            // Create accounts
            List<Account> accounts = new ArrayList<>();
            UUID accountId = UUID.randomUUID();
            Account account = new Account();
            account.setUserID(userId);
            account.setAccountID(accountId);
            accounts.add(account);

            // Create Pageable
            Pageable pageable = PageRequest.of(offset, limit);

            // Create Page<Account> with the accounts list and pageable
            Page<Account> accountPage = new PageImpl<>(accounts, pageable, accounts.size());

            // Configure mock behavior
            when(accountRepository.getAccountsOfUser(
                    ArgumentMatchers.<UUID>any(),
                    ArgumentMatchers.<String>any(),
                    ArgumentMatchers.<Pageable>any()
            )).thenReturn(accountPage);
            //when(accountRepository.getAccountsOfUser(eq(userId), anyString(), any(Pageable.class))).thenReturn(accountPage);

            when(objectMapper.convertValue(any(Account.class), eq(GetAccountDTO.class))).thenReturn(accountDTO);

            ResponseEntity<List<GetAccountDTO>> responseEntity = accountService.getAccountsOfUser(userId, limit, offset, searchStrings);

            assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
            assertThat(responseEntity.getBody()).hasSize(1);
            assertThat(responseEntity.getBody().get(0).getUserID()).isEqualTo(userId);
            assertThat(responseEntity.getHeaders().get("X-Total-Accounts").get(0)).isEqualTo("1");

            verify(accountRepository, times(1)).getAccountsOfUser(any(UUID.class), anyString(), any());
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

            ResponseEntity<GetAccountDTO> responseEntity = accountService.updateAccount(accountId, updateAccountDTO);

            assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
            assertThat(responseEntity.getBody().getAccountID()).isEqualTo(accountId);

            verify(accountRepository, times(1)).getAccountByAccountID(accountId);
            verify(accountRepository, times(1)).save(any(Account.class));
        }
    }
package io.swagger.api.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.api.service.AccountService;
import io.swagger.api.service.UserService;
import io.swagger.model.Account;
import io.swagger.model.DTO.CreateAccountDTO;
import io.swagger.model.User;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest(AccountsApiController.class)
public class AccountsApiControllerTest {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @MockBean
    private AccountService accountService;

    @Test
    public void testAccountsPost() throws Exception {
        // Create a mock user and account
        UUID userId = UUID.randomUUID();
        User user = new User();
        user.setUserID(userId);
        Account account = new Account();
        account.setAccountID(UUID.randomUUID());

        // Mock the userService to return the user
        when(userService.getUserByUserID(userId)).thenReturn(user);

        // Mock the accountService to return the created account
        when(accountService.add(any(Account.class))).thenReturn(account);

        // Create a mock CreateAccountDTO
        CreateAccountDTO createAccountDTO = new CreateAccountDTO();
        createAccountDTO.setUserId(userId);

        // Perform the request to the controller
        mockMvc.perform(MockMvcRequestBuilders.post("/accounts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createAccountDTO)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.accountId").value(account.getAccountID().toString()));

        // Verify that the userService method was called with the correct userId
        verify(userService, times(1)).getUserByUserID(userId);

        // Verify that the accountService method was called with the correct account
        verify(accountService, times(1)).add(any(Account.class));
    }
}
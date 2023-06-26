package io.swagger.api.cucumber.steps;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.swagger.api.controllers.AccountsApiController;
import io.swagger.api.service.AccountService;
import io.swagger.model.Account;
import io.swagger.model.AccountType;
import io.swagger.model.DTO.CreateAccountDTO;
import io.swagger.model.DTO.GetAccountDTO;
import io.swagger.model.DTO.UpdateAccountDTO;
import io.swagger.model.Role;
import io.swagger.model.User;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class AccountsApiControllerSteps {
    private ObjectMapper objectMapper;
    private AccountsApiController accountsApiController;
    private AccountService accountService;
    private ResponseEntity<Account> createAccountResponse;
    private ResponseEntity<List<GetAccountDTO>> getAccountsResponse;
    private ResponseEntity<GetAccountDTO> getAccountByIdResponse;
    private ResponseEntity<List<GetAccountDTO>> getUserAccountsResponse;
    private ResponseEntity<GetAccountDTO> updateAccountResponse;

    @Given("^I have an AccountsApiController$")
    public void iHaveAnAccountsApiController() {
        objectMapper = new ObjectMapper();
        accountService = Mockito.mock(AccountService.class);
        HttpServletRequest httpServletRequest = Mockito.mock(HttpServletRequest.class);
        accountsApiController = new AccountsApiController(objectMapper, httpServletRequest);
    }

    @When("^I create an account with the following details:$")
    public void iCreateAnAccountWithTheFollowingDetails(List<CreateAccountDTO> createAccountDTOList) {
        CreateAccountDTO createAccountDTO = createAccountDTOList.get(0);

        User user = new User(UUID.fromString("bb0cc36d-69a7-471e-a665-3609bc14c27a"), "Test", "Account", "testaccount@mail.nl", "password", Arrays.asList(Role.ROLE_USER), true, new ArrayList<>(), 1000.00, 10000.00);
        Account bankAccount = new Account(UUID.randomUUID(), user, user.getUserID(), "test account", "NL01INHO0000000001", 9999999999999999.00, AccountType.TypeEnum.CURRENT, -9999999999999999.00, true);

        when(accountService.add(any(CreateAccountDTO.class))).thenReturn(bankAccount);

        createAccountResponse = accountsApiController.createAccount(createAccountDTO);
    }

    @Then("^I should receive a response with status code (\\d+)$")
    public void iShouldReceiveAResponseWithStatusCode(int expectedStatusCode) {
        assertThat(createAccountResponse.getStatusCode()).isEqualTo(HttpStatus.valueOf(expectedStatusCode));
    }

    @Then("^the created account should match the following details:$")
    public void theCreatedAccountShouldMatchTheFollowingDetails(List<GetAccountDTO> expectedAccountDTOList) {
        GetAccountDTO expectedAccountDTO = expectedAccountDTOList.get(0);
        Account expectedAccount = new Account(expectedAccountDTO.getAccountID(), null, null, expectedAccountDTO.getName(), expectedAccountDTO.getIban(), expectedAccountDTO.getBalance(), expectedAccountDTO.getType(), expectedAccountDTO.getMinBal(), expectedAccountDTO.getActive());

        assertThat(createAccountResponse.getBody()).isEqualTo(expectedAccount);
    }

    @When("^I retrieve all accounts$")
    public void iRetrieveAllAccounts() {
        List<GetAccountDTO> accountDTOList = new ArrayList<>();

        User user = new User(UUID.fromString("bb0cc36d-69a7-471e-a665-3609bc14c27a"), "Test", "Account", "testaccount@mail.nl", "password", Arrays.asList(Role.ROLE_USER), true, new ArrayList<>(), 1000.00, 10000.00);

        Account account1 = new Account(UUID.randomUUID(), user, user.getUserID(), "test account 1", "NL01INHO0000000001", 9999999999999999.00, AccountType.TypeEnum.CURRENT, -9999999999999999.00, true);
        Account account2 = new Account(UUID.randomUUID(), user, user.getUserID(), "test account 2", "NL01INHO0000000002", 8888888888888888.00, AccountType.TypeEnum.CURRENT, -8888888888888888.00, true);

        accountDTOList.add(objectMapper.convertValue(account1, GetAccountDTO.class));
        accountDTOList.add(objectMapper.convertValue(account2, GetAccountDTO.class));

        when(accountService.getAllAccounts(10, 0, null, null)).thenReturn(accountDTOList);

        getAccountsResponse = accountsApiController.getAccounts(10, 0 , null, null);
    }

    @Then("^I should receive a list of accounts with size (\\d+)$")
    public void iShouldReceiveAListOfAccountsWithSize(int expectedSize) {
        assertThat(getAccountsResponse.getBody()).hasSize(expectedSize);
    }

    @Then("^the received accounts should match the following details:$")
    public void theReceivedAccountsShouldMatchTheFollowingDetails(List<GetAccountDTO> expectedAccountDTOList) {
        List<GetAccountDTO> actualAccountDTOList = getAccountsResponse.getBody();
        assertThat(actualAccountDTOList).containsExactlyElementsOf(expectedAccountDTOList);
    }

    @When("^I retrieve the account with ID \"([^\"]*)\"$")
    public void iRetrieveTheAccountWithID(String accountId) {
        UUID accountUUID = UUID.fromString(accountId);

        User user = new User(UUID.fromString("bb0cc36d-69a7-471e-a665-3609bc14c27a"), "Test", "Account", "testaccount@mail.nl", "password", Arrays.asList(Role.ROLE_USER), true, new ArrayList<>(), 1000.00, 10000.00);
        Account account = new Account(accountUUID, user, user.getUserID(), "test account", "NL01INHO0000000001", 9999999999999999.00, AccountType.TypeEnum.CURRENT, -9999999999999999.00, true);

        when(accountService.getAccountByAccountID(any(UUID.class))).thenReturn(objectMapper.convertValue(account, GetAccountDTO.class));

        getAccountByIdResponse = accountsApiController.getAccountById(accountUUID, null);
    }

    @Then("^I should receive the account with ID \"([^\"]*)\"$")
    public void iShouldReceiveTheAccountWithID(String accountId) {
        UUID accountUUID = UUID.fromString(accountId);
        GetAccountDTO actualAccountDTO = getAccountByIdResponse.getBody();

        assertThat(actualAccountDTO).isNotNull();
        assertThat(actualAccountDTO.getAccountID()).isEqualTo(accountUUID);
    }

    @When("^I retrieve all accounts for the user with ID \"([^\"]*)\"$")
    public void iRetrieveAllAccountsForTheUserWithID(String userId) {
        UUID userUUID = UUID.fromString(userId);

        List<GetAccountDTO> accountDTOList = new ArrayList<>();

        User user = new User(userUUID, "Test", "User", "testuser@mail.nl", "password", Arrays.asList(Role.ROLE_USER), true, new ArrayList<>(), 1000.00, 10000.00);

        Account account1 = new Account(UUID.randomUUID(), user, user.getUserID(), "test account 1", "NL01INHO0000000001", 9999999999999999.00, AccountType.TypeEnum.CURRENT, -9999999999999999.00, true);
        Account account2 = new Account(UUID.randomUUID(), user, user.getUserID(), "test account 2", "NL01INHO0000000002", 8888888888888888.00, AccountType.TypeEnum.CURRENT, -8888888888888888.00, true);

        accountDTOList.add(objectMapper.convertValue(account1, GetAccountDTO.class));
        accountDTOList.add(objectMapper.convertValue(account2, GetAccountDTO.class));

        when(accountService.getAccountsOfUser(any(UUID.class), 10, 0, null)).thenReturn(accountDTOList);

        getUserAccountsResponse = accountsApiController.getUserAccounts(userUUID,10, 0, null, null);
    }

    @Then("^I should receive a list of accounts for the user with ID \"([^\"]*)\"$")
    public void iShouldReceiveAListOfAccountsForTheUserWithID(String userId) {
        UUID userUUID = UUID.fromString(userId);
        List<GetAccountDTO> actualAccountDTOList = getUserAccountsResponse.getBody();

        assertThat(actualAccountDTOList).isNotNull();
        assertThat(actualAccountDTOList).allMatch(accountDTO -> accountDTO.getUserID().equals(userUUID));
    }

    @When("^I update the account with ID \"([^\"]*)\" with the following details:$")
    public void iUpdateTheAccountWithIDWithTheFollowingDetails(String accountId, List<UpdateAccountDTO> updateAccountDTOList) {
        UUID accountUUID = UUID.fromString(accountId);
        UpdateAccountDTO updateAccountDTO = updateAccountDTOList.get(0);

        User user = new User(UUID.fromString("bb0cc36d-69a7-471e-a665-3609bc14c27a"), "Test", "Account", "testaccount@mail.nl", "password", Arrays.asList(Role.ROLE_USER), true, new ArrayList<>(), 1000.00, 10000.00);
        Account account = new Account(accountUUID, user, user.getUserID(), "test account", "NL01INHO0000000001", 9999999999999999.00, AccountType.TypeEnum.CURRENT, -9999999999999999.00, true);

        when(accountService.updateAccount(any(UUID.class), any(UpdateAccountDTO.class))).thenReturn(objectMapper.convertValue(account, GetAccountDTO.class));

        updateAccountResponse = accountsApiController.updateAccount(accountUUID, updateAccountDTO, null);
    }

    @Then("^I should receive a response with status code (\\d+) for the account update$")
    public void iShouldReceiveAResponseWithStatusCodeForTheAccountUpdate(int expectedStatusCode) {
        assertThat(updateAccountResponse.getStatusCode()).isEqualTo(HttpStatus.valueOf(expectedStatusCode));
    }

    @Then("^the updated account should match the following details:$")
    public void theUpdatedAccountShouldMatchTheFollowingDetails(List<GetAccountDTO> expectedAccountDTOList) {
        GetAccountDTO expectedAccountDTO = expectedAccountDTOList.get(0);
        Account expectedAccount = new Account(expectedAccountDTO.getAccountID(), null, null, expectedAccountDTO.getName(), expectedAccountDTO.getIban(), expectedAccountDTO.getBalance(), expectedAccountDTO.getType(), expectedAccountDTO.getMinBal(), expectedAccountDTO.getActive());

        assertThat(updateAccountResponse.getBody()).isEqualTo(expectedAccount);
    }
}

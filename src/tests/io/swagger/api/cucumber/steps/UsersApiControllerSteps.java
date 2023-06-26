package io.swagger.api.cucumber.steps;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.swagger.api.controllers.UsersApiController;
import io.swagger.api.service.UserService;
import io.swagger.model.DTO.CreateUserDTO;
import io.swagger.model.DTO.GetUserDTO;
import io.swagger.model.DTO.UpdateUserDTO;
import io.swagger.model.User;
import org.junit.jupiter.api.Assertions;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.security.Principal;
import java.util.List;
import java.util.UUID;

public class UsersApiControllerSteps {
    private UsersApiController usersApiController;
    private UserService userService;
    private ResponseEntity<User> createUserResponse;
    private ResponseEntity<List<GetUserDTO>> getUsersResponse;
    private ResponseEntity<GetUserDTO> getUserByIdResponse;
    private ResponseEntity<GetUserDTO> updateUserResponse;

    @Given("^I have a UsersApiController$")
    public void iHaveAUsersApiController() {
        userService = Mockito.mock(UserService.class);
        usersApiController = new UsersApiController(null, userService, null);
    }

    @When("^I create a user with the following details$")
    public void iCreateAUserWithTheFollowingDetails(CreateUserDTO createUserDTO) {
        User user = new User();
        // Set user properties from createUserDTO

        Mockito.when(userService.add(createUserDTO)).thenReturn(user);

        createUserResponse = usersApiController.createUser(createUserDTO);
    }

    @Then("^the user should be created successfully$")
    public void theUserShouldBeCreatedSuccessfully() {
        Assertions.assertEquals(HttpStatus.CREATED, createUserResponse.getStatusCode());
        // Additional assertions if needed
    }

    @When("^I get the list of users$")
    public void iGetTheListOfUsers(Integer limit, Integer offset, String searchStrings, String email) {
        List<GetUserDTO> userList = Mockito.mock(List.class);

        Mockito.when(userService.getAllUsers(limit, offset, searchStrings, email)).thenReturn(userList);

        getUsersResponse = usersApiController.getUsers(limit, offset, searchStrings, email);
    }

    @Then("^the list of users should be returned successfully$")
    public void theListOfUsersShouldBeReturnedSuccessfully() {
        Assertions.assertEquals(HttpStatus.OK, getUsersResponse.getStatusCode());
        // Additional assertions if needed
    }

    @When("^I get the user by ID$")
    public void iGetTheUserById(UUID userId, Principal principal) {
        GetUserDTO userDto = Mockito.mock(GetUserDTO.class);
        Mockito.when(userService.getUserByUserID(userId)).thenReturn(userDto);

        getUserByIdResponse = usersApiController.getUserById(userId, principal);
    }

    @Then("^the user should be returned successfully$")
    public void theUserShouldBeReturnedSuccessfully() {
        Assertions.assertEquals(HttpStatus.OK, getUserByIdResponse.getStatusCode());
        // Additional assertions if needed
    }

    @When("^I update the user with ID$")
    public void iUpdateTheUserWithId(UUID userId, UpdateUserDTO updateUserDTO, Principal principal) {
        GetUserDTO userDto = Mockito.mock(GetUserDTO.class);
        Mockito.when(userService.updateUser(userId, updateUserDTO)).thenReturn(userDto);

        updateUserResponse = usersApiController.updateUser(userId, updateUserDTO, principal);
    }

    @Then("^the user should be updated successfully$")
    public void theUserShouldBeUpdatedSuccessfully() {
        Assertions.assertEquals(HttpStatus.OK, updateUserResponse.getStatusCode());
        // Additional assertions if needed
    }
}

package tests.api.model.cucumberSteps;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import java.util.Map;

public class AccountSteps {
    @Given("The endpoint for {string} is available for method {string}")
    public void theEndpointForIsAvailable(String endpoint, String method) {
        // Add your implementation here
    }


    @When("A new account is created with the following details:")
    public void createNewAccountWithDetails(Map<String, String> accountDetails) {
        // Extract account details from the map and create a new account
        // Replace the placeholders with actual values from the map
    }
    @Then("The account should be created successfully with the provided details")
    public void verifyAccountCreation() {
        // Add your implementation here
    }


    @When("The account details are requested")
    public void retrieveAccountDetails() {
        // Add your implementation here
    }
    @Then("The account details should be returned with the provided details")
    public void verifyAccountDetails() {
        // Add your implementation here
    }


    @When("The account details are updated with the following details:")
    public void updateAccountDetails(Map<String, String> updatedAccountDetails) {
        // Extract updated account details from the map and update the account
        // Replace the placeholders with actual values from the map
    }
    @Then("The account details should be updated successfully with the provided details")
    public void verifyAccountUpdate() {
        // Add your implementation here
    }


    @When("All accounts for the user are requested")
    public void retrieveAllAccountsForUser() {
        // Add your implementation here
    }
    @Then("All accounts should be returned with the provided details")
    public void verifyAllAccounts() {
        // Add your implementation here
    }
}

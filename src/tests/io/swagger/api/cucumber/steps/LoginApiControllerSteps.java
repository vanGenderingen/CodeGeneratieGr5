package io.swagger.api.cucumber.steps;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.swagger.api.repository.UserRepository;
import io.swagger.model.Token;
import io.swagger.model.User;
import io.swagger.model.DTO.LoginDTO;
import io.swagger.model.DTO.LoginResponseDTO;
import java.util.Map;
        import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.HashMap;
import java.util.UUID;

import org.junit.Assert;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
 import org.springframework.http.HttpHeaders;
    import org.springframework.http.HttpEntity;
    import org.springframework.http.MediaType;
    import org.springframework.http.HttpMethod;
    import org.springframework.web.client.RestTemplate;

public class LoginApiControllerSteps {

    private ResponseEntity<LoginResponseDTO> loginResponse;

    private UserRepository userRepository;

    private String email;
    private String token;
    private String newPassword;
    private String confirmPassword;
    private ResponseEntity<?> response;

    @Given("a user with email {string} and password {string}")
    public void givenUserWithEmailAndPassword(String email, String password) {
        // Assuming you have a user repository or service to create the user
        User user = new User();
        user.setEmail(email);
        user.setPassword(password);
        userRepository.save(user);
    }

    @When("I attempt to login with email {string} and password {string}")
    public void whenAttemptToLoginWithEmailAndPassword(String email, String password) {
        LoginDTO loginDTO = new LoginDTO();
        loginDTO.setEmail(email);
        loginDTO.setPassword(password);
        loginResponse = loginPost(loginDTO);
    }

    private ResponseEntity<LoginResponseDTO> loginPost(LoginDTO loginDTO) {
        // Assuming you have an HTTP client library like RestTemplate or WebClient
        // Here's an example using RestTemplate:
        RestTemplate restTemplate = new RestTemplate();
        String url = "http://localhost:8080/login";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<LoginDTO> requestEntity = new HttpEntity<>(loginDTO, headers);

        return restTemplate.exchange(url, HttpMethod.POST, requestEntity, LoginResponseDTO.class);
    }

    @Then("I should receive a successful login response")
    public void thenShouldReceiveSuccessfulLoginResponse() {
        assertEquals(HttpStatus.OK, loginResponse.getStatusCode());
        assertNotNull(loginResponse.getBody());
        assertNotNull(loginResponse.getBody().getToken());
    }

    @Then("I should receive an invalid credentials response")
    public void thenShouldReceiveInvalidCredentialsResponse() {
        assertEquals(HttpStatus.BAD_REQUEST, loginResponse.getStatusCode());
        // You can further verify the response body or specific error messages if needed
    }
     private ResponseEntity<?> forgotPasswordResponse;

    @Given("a user with email {string}")
    public void givenUserWithEmail(String email) {
        // Assuming you have a user repository or service to create the user
        User user = new User();
        user.setEmail(email);
        user.setPassword("password");
        userRepository.save(user);
    }

    @When("I request a password reset for email {string}")
    public void whenRequestPasswordReset(String email) {
        Map<String, String> request = new HashMap<String, String>();
        request.put("email", email);
        forgotPasswordResponse = forgotPassword(request);
    }

    private ResponseEntity<?> forgotPassword(Map<String, String> request) {
        RestTemplate restTemplate = new RestTemplate();
        String url = "http://localhost:8080/forgot-password";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Map<String, String>> requestEntity = new HttpEntity<>(request, headers);

        return restTemplate.exchange(url, HttpMethod.POST, requestEntity, Object.class);
    }

    @Then("I should receive a successful forgot password response")
    public void thenShouldReceiveSuccessfulForgotPasswordResponse() {
        assertEquals(HttpStatus.OK, forgotPasswordResponse.getStatusCode());
    }

    @Then("I should receive a failed to save token response")
    public void thenShouldReceiveFailedToSaveTokenResponse() {
        assertEquals(HttpStatus.BAD_REQUEST, forgotPasswordResponse.getStatusCode());
    }

    @Then("I should receive a failed to send email response")
    public void thenShouldReceiveFailedToSendEmailResponse() {
        assertEquals(HttpStatus.BAD_REQUEST, forgotPasswordResponse.getStatusCode());
    }
    @Given("^a user with email \"([^\"]*)\" and token \"([^\"]*)\"$")
    public void setUserWithEmailAndToken(String email, String token) {
        this.email = email;
        this.token = token;
    }

    @When("^I reset the password with new password \"([^\"]*)\" and confirm password \"([^\"]*)\"$")
    public void resetPasswordWithNewPasswordAndConfirmPassword(String newPassword, String confirmPassword) {
        this.newPassword = newPassword;
        this.confirmPassword = confirmPassword;
        this.response = resetPassword(email, token, newPassword, confirmPassword);
    }

    @When("^I reset the password without providing email, new password, confirm password, or token$")
    public void resetPasswordWithoutProvidingFields() {
        this.response = resetPassword(null, null, null, null);
    }

    @Then("^the password should be updated for the user$")
    public void verifyPasswordUpdate() {
        Assert.assertEquals(HttpStatus.OK, response.getStatusCode());
        // Add any additional assertions or verifications for password update if needed
    }

    @Then("^the reset password request should be rejected$")
    public void verifyRejectedRequest() {
        Assert.assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        // Add any additional assertions or verifications for rejected request if needed
    }

    private ResponseEntity<?> resetPassword(String email, String token, String newPassword, String confirmPassword) {
        // Implement the code to make a POST request to "/reset-password" endpoint with the provided data
        // Return the ResponseEntity object received from the endpoint

        // Sample implementation using RestTemplate
        String resetUrl = "http://localhost:8080/reset-password";
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        Map<String, String> request = new HashMap<>();
        request.put("email", email);
        request.put("newPassword", newPassword);
        request.put("confirmPassword", confirmPassword);
        request.put("token", token);

        HttpEntity<Map<String, String>> httpEntity = new HttpEntity<>(request, headers);

        return restTemplate.postForEntity(resetUrl, httpEntity, LoginResponseDTO.class);
    }
}

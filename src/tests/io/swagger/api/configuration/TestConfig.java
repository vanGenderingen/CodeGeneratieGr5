package io.swagger.api.configuration;

import io.swagger.api.service.AccountService;
import io.swagger.api.service.UserService;
import org.mockito.Mockito;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

@TestConfiguration
public class TestConfig {
    /*
    @Bean
    public UserService userService() {
        return Mockito.mock(UserService.class);
    }

    @Bean
    public AccountService accountService() {
        return Mockito.mock(AccountService.class);
    }
    */
}

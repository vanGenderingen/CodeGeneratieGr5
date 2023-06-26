package io.swagger;

import io.swagger.api.exceptions.ExitException;
import io.swagger.configuration.LocalDateConverter;
import io.swagger.configuration.LocalDateTimeConverter;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;

@SpringBootApplication
public class RedBankApplication implements CommandLineRunner {
    public static void main(String[] args) {
        SpringApplication.run(RedBankApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        if (args.length > 0 && args[0].equals("exitcode")) {
            throw new ExitException();
        }
    }
}

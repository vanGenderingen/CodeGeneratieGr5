package io.swagger.api.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    private static final String[] AUTH_WHITELIST = {
            "/login",
            "/forgot-password",
            "/reset-password",
            "/h2-console//",
            "/swagger-ui//",
            "/swagger-resources/",
            "/swagger-ui.html",
            "/swagger-ui",
            "/api-docs",
            "webjars/**",
            "/error/**"
    };

    @Autowired
    private JwtTokenFilter jwtTokenFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.httpBasic(withDefaults());
        http.cors().configurationSource(corsConfigurationSource()).and();
        http.csrf().disable();
        http.headers().frameOptions().disable();
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http.addFilterBefore(jwtTokenFilter, UsernamePasswordAuthenticationFilter.class);
        http.authorizeHttpRequests((authorize) -> authorize
                .requestMatchers(AUTH_WHITELIST).permitAll()
                .anyRequest().authenticated()
        );
        return http.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.addAllowedOrigin("*"); // Allow all origins, update this to your specific requirements
        config.addAllowedMethod("*"); // Allow all HTTP methods
        config.addAllowedHeader("*"); // Allow all headers
        source.registerCorsConfiguration("/**", config);
        return source;
    }
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(12);
    }
}
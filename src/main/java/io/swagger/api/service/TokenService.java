package io.swagger.api.service;

import io.swagger.api.repository.TokenRepository;
import io.swagger.model.Token;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TokenService {

    @Autowired
    private TokenRepository tokenRepository;

    public Token findByEmail(String email) {
        return tokenRepository.findByEmail(email);
    }
    public void saveToken(Token token) {
         tokenRepository.save(token);
    }

}

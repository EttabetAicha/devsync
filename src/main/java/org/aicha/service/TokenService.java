package org.aicha.service;

import org.aicha.model.Token;
import org.aicha.repository.TokenRepository;

import java.util.List;
import java.util.Optional;

public class TokenService {
    private final TokenRepository tokenRepository;

    public TokenService() {
        tokenRepository = new TokenRepository();
    }

    public List<Token> getAllTokens() {
        return tokenRepository.findAll();
    }

    public Token createToken(Token token) {
        // Validate token
        if (token == null || token.getModificationTokens() == null || token.getDeletionTokens() == null) {
            throw new IllegalArgumentException("Invalid token");
        }
        Optional<Token> tokenOp = tokenRepository.create(token);
        tokenOp.ifPresent(t -> t.setId(t.getId()));
        return token;
    }

    public Optional<Token> updateToken(Token token) {
        // Validate token
        if (token == null || token.getModificationTokens() == null || token.getDeletionTokens() == null) {
            throw new IllegalArgumentException("Invalid token");
        }
        return tokenRepository.update(token);
    }

}

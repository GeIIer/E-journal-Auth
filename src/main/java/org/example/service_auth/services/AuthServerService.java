package org.example.service_auth.services;

import org.example.service_auth.dto.AuthForm;
import org.example.service_auth.dto.ServerDto;
import org.example.service_auth.dto.Token;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class AuthServerService implements AuthService<ServerDto> {
    private static final String SERVICE_TYPE = "SERVER";

    @Override
    public boolean supportType(String type) {
        return SERVICE_TYPE.equals(type);
    }

    @Override
    public Mono<Token> generateToken(AuthForm form) {
        return null;
    }

    @Override
    public Mono<Token> refreshToken(Token token) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Class<?> getKey() {
        return AuthServerService.class;
    }
}

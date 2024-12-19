package org.example.service_auth.services;

import org.example.service_auth.dto.AuthForm;
import org.example.service_auth.dto.Token;
import org.example.service_auth.services.provide.Providable;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public interface AuthService<T> extends Providable<Class<?>> {
    boolean supportType (String type);

    Mono<Token> generateToken(AuthForm form);

    Mono<Token> refreshToken(Token token);
}

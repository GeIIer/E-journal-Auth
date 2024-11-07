package org.example.service_auth.services;

import org.example.service_auth.dto.AccessTokenValue;
import org.example.service_auth.dto.AuthForm;
import org.example.service_auth.dto.Token;
import org.example.service_auth.dto.registration.BaseRegistrationRequestDto;
import org.example.service_auth.dto.registration.RegistrationResponseDto;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public interface AuthService<T, R extends BaseRegistrationRequestDto> {
    boolean supportType (String type);

    Mono<Token> generateToken(AuthForm form);

    Mono<RegistrationResponseDto> signUp(R register);

    Mono<Token> refreshToken(Token token);

    Mono<Object> logout(String accessToken, ServerHttpResponse response, ServerHttpRequest request);

    Mono<T> getUserData(AccessTokenValue accessToken);
}

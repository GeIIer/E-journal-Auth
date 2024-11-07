package org.example.service_auth.services;

import org.example.service_auth.dto.AccessTokenValue;
import org.example.service_auth.dto.AuthForm;
import org.example.service_auth.dto.ServerDto;
import org.example.service_auth.dto.Token;
import org.example.service_auth.dto.registration.RegistrationResponseDto;
import org.example.service_auth.dto.registration.ServerRegistrationRequestDto;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class AuthServerService implements AuthService<ServerDto, ServerRegistrationRequestDto> {
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
    public Mono<RegistrationResponseDto> signUp(ServerRegistrationRequestDto register) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Mono<Token> refreshToken(Token token) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Mono<Object> logout(String accessToken, ServerHttpResponse response, ServerHttpRequest request) {
        return null;
    }

    @Override
    public Mono<ServerDto> getUserData(AccessTokenValue accessToken) {
        return null;
    }
}

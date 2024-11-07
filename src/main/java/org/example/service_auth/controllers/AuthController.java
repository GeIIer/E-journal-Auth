package org.example.service_auth.controllers;


import lombok.RequiredArgsConstructor;
import org.example.service_auth.dto.AccessTokenValue;
import org.example.service_auth.dto.AuthForm;
import org.example.service_auth.dto.Token;
import org.example.service_auth.dto.enums.ResponseTypeEnum;
import org.example.service_auth.dto.registration.BaseRegistrationRequestDto;
import org.example.service_auth.dto.registration.RegistrationResponseDto;
import org.example.service_auth.dto.registration.UserRegistrationRequestDto;
import org.example.service_auth.jwt.JwtService;
import org.example.service_auth.services.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Mono;

import java.util.List;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final List<AuthService> authServices;
    private final JwtService jwtService;

    @PostMapping(value = "/login")
    public Mono<Token> login(@RequestBody AuthForm authForm,
                             @RequestParam ResponseTypeEnum responseType) {
        for (AuthService authService : authServices) {
            if (authService.supportType(responseType.name())) {
                return authService.generateToken(authForm);
            }
        }
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid response type");
    }

    @PostMapping(value = "/refresh")
    public Mono<Token> refresh(@RequestBody Token requestToken,
                               @RequestParam ResponseTypeEnum responseType) {
        for (AuthService authService : authServices) {
            if (authService.supportType(responseType.name())) {
                return authService.refreshToken(requestToken);
            }
        }
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid response type");
    }

    @PostMapping("/sign-up")
    public Mono<RegistrationResponseDto> signUp(@RequestBody BaseRegistrationRequestDto requestDto) {
        for (AuthService authService : authServices) {
            if (authService.supportType(requestDto.getResponseType().name())) {
                if (requestDto instanceof UserRegistrationRequestDto) {
                    return authService.signUp(requestDto);
                }
            }
        }
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid response type");
    }

    @PostMapping("/access")
    public Mono<AccessTokenValue> access(@RequestBody Token requestToken) {
        return Mono.just(jwtService.parseAccessJwt(requestToken.getAccessToken()));
    }
}

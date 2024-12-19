package org.example.service_auth.controllers;


import lombok.RequiredArgsConstructor;
import org.example.service_auth.dto.AccessTokenValue;
import org.example.service_auth.dto.AuthForm;
import org.example.service_auth.dto.Token;
import org.example.service_auth.dto.enums.ResponseTypeEnum;
import org.example.service_auth.dto.registration.RegistrationResponseDto;
import org.example.service_auth.dto.registration.UserRegistrationRequestDto;
import org.example.service_auth.jwt.JwtService;
import org.example.service_auth.services.AuthService;
import org.example.service_auth.services.AuthServiceProvider;
import org.example.service_auth.services.AuthUserService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping()
@RequiredArgsConstructor
public class AuthController {
    private final AuthServiceProvider serviceProvider;
    private final AuthUserService authUserService;
    private final JwtService jwtService;

    @PostMapping(value = "/login")
    public Mono<ResponseEntity<Token>> login(@RequestBody AuthForm authForm) {
        if (authForm.getResponseType() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Response type is required");
        }
        for (AuthService<?> authService : serviceProvider.getAllServices()) {
            if (authService.supportType(authForm.getResponseType().name())) {
                return authService.generateToken(authForm)
                        .flatMap(token -> {
                            ResponseCookie accessTokenCookie = ResponseCookie.from("ACCESS-TOKEN", token.getAccessToken())
                                    .httpOnly(false)
                                    .secure(false)
                                    .maxAge(token.getAccessTokenExp())
                                    .path("/")
                                    .build();
                            ResponseCookie responseCookie = ResponseCookie.from("REFRESH-TOKEN", token.getRefreshToken())
                                    .httpOnly(false)
                                    .secure(false)
                                    .maxAge(token.getRefreshTokenExp())
                                    .path("/")
                                    .build();
                            ResponseEntity.BodyBuilder builder = ResponseEntity.status(HttpStatus.OK)
                                    .header(HttpHeaders.SET_COOKIE, accessTokenCookie.toString())
                                    .header(HttpHeaders.SET_COOKIE, responseCookie.toString());

                            return Mono.just(builder.build());
                        });
            }
        }
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
    }

    @PostMapping(value = "/refresh")
    public Mono<Token> refresh(@RequestBody Token requestToken,
                               @RequestParam ResponseTypeEnum responseType) {
        for (AuthService<?> authService : serviceProvider.getAllServices()) {
            if (authService.supportType(responseType.name())) {
                return authService.refreshToken(requestToken);
            }
        }
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid response type");
    }

    @PostMapping("/sign-up")
    public Mono<RegistrationResponseDto> signUp(@RequestBody UserRegistrationRequestDto requestDto) {
        return authUserService.signUp(requestDto);
    }

    @PostMapping("/access")
    public Mono<AccessTokenValue> access(@RequestBody Token requestToken) {
        return Mono.just(jwtService.parseAccessJwt(requestToken.getAccessToken()));
    }
}

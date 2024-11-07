package org.example.service_auth.config;

import io.jsonwebtoken.ExpiredJwtException;
import org.example.service_auth.dto.AccessTokenValue;
import org.example.service_auth.jwt.JwtService;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class AppAuthenticationManager implements ReactiveAuthenticationManager {
    private final JwtService jwtService;

    public AppAuthenticationManager(JwtService jwtService) {
        this.jwtService = jwtService;
    }

    @Override
    public Mono<Authentication> authenticate(Authentication authentication) {
        if (authentication == null || authentication.getPrincipal() == null) {
            return Mono.empty();
        }

        String authToken = authentication.getCredentials().toString();

        if (authToken != null && authToken.contains(".")) {
            AccessTokenValue accessTokenValue;
            try {
                accessTokenValue = jwtService.parseAccessJwt(authToken);
            } catch (ExpiredJwtException e) {
                return Mono.error(new ResponseStatusException(HttpStatus.FORBIDDEN, e.getMessage()));
            }

            List<SimpleGrantedAuthority> scope = accessTokenValue.getScope().stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList());

            UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(
                    accessTokenValue,
                    null,
                    scope
            );
             return Mono.just(auth);
        }
        return Mono.empty();

    }
}

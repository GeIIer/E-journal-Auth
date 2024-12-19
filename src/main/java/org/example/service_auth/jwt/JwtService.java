package org.example.service_auth.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.example.service_auth.dto.AccessTokenValue;
import org.example.service_auth.dto.Token;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.nio.charset.StandardCharsets;
import java.util.*;

@Service
@RequiredArgsConstructor
public class JwtService {

    public final JwtConfig jwtConfig;

    public AccessTokenValue parseAccessJwt(String token) {
        try {
            Claims claims = (Claims) Jwts.parser()
                    .verifyWith(Keys.hmacShaKeyFor(jwtConfig.getAccessSecret().getBytes(StandardCharsets.UTF_8)))
                    .build()
                    .parse(token)
                    .getPayload();

            Long id = claims.get("id", Long.class);
            String username = getUsername(claims);
            String type = claims.get("type", String.class);
            List<String> scopes = claims.get("scope", ArrayList.class) == null ? Collections.EMPTY_LIST :
                    claims.get("scope", ArrayList.class);
            return new AccessTokenValue(id, username, type, new HashSet<>(scopes));
        } catch (ExpiredJwtException ex) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, ex.getMessage());
        }
    }

    public Long parseRefreshJwt(String token) {
        try {
            Claims claims = (Claims) Jwts.parser()
                    .verifyWith(Keys.hmacShaKeyFor(jwtConfig.getRefreshSecret().getBytes(StandardCharsets.UTF_8)))
                    .build()
                    .parse(token)
                    .getPayload();

            return claims.get("id", Long.class);
        } catch (ExpiredJwtException ex) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, ex.getMessage());
        }
    }

    public Token generateToken(Map<String, Object> claims) {
        long now = System.currentTimeMillis();

        UUID generatUid = UUID.randomUUID();
        String uid = generatUid.toString();
        Date accessTokenExp = new Date(now + jwtConfig.getExpiration() * 1000L);
        Date refreshTokenExp = new Date(now + jwtConfig.getRefreshExpiration() * 1000L);
        String accessToken = Jwts.builder()
                .claims(claims)
                .id(uid)
                .issuer(jwtConfig.getIssuer())
                .issuedAt(new Date(now))
                .expiration(accessTokenExp)
                .signWith(Keys.hmacShaKeyFor(jwtConfig.getAccessSecret().getBytes(StandardCharsets.UTF_8)))
                .compact();

        String refreshToken = Jwts.builder()
                .id(uid)
                .claim("id", claims.get("id"))
                .issuer(jwtConfig.getIssuer())
                .issuedAt(new Date(now))
                .expiration(refreshTokenExp)
                .signWith(Keys.hmacShaKeyFor(jwtConfig.getRefreshSecret().getBytes(StandardCharsets.UTF_8)))
                .compact();

        return new Token(accessToken, accessTokenExp.getTime() / 1000 - now,
                refreshToken, refreshTokenExp.getTime() / 1000 - now);
    }

    private String getUsername(Claims claims) {
        if (claims.get("name", String.class) != null) {
            return claims.get("name", String.class);
        }
        return claims.get("email", String.class) != null ?
                claims.get("email", String.class) :
                claims.get("serverName", String.class);

    }
}

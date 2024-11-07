package org.example.service_auth.jwt;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
public class JwtConfig {
    @Value("${security.jwt.header:Authorization}")
    private String header;

    @Value("${security.jwt.prefix:Bearer }")
    private String prefix;

    @Value("${security.jwt.expiration:#{1*60*60}}")
    private int expiration;

    @Value("${security.jwt.refreshExpiration:#{24*60*60*1}}")
    private int refreshExpiration;

    @Value("${security.jwt.secret.access}")
    private String accessSecret;

    @Value("${security.jwt.secret.refresh}")
    private String refreshSecret;

    @Value("${security.jwt.issuer}")
    private String issuer;

    public JwtConfig() {
    }
}

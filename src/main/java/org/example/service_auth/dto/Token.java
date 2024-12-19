package org.example.service_auth.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Objects;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Token {
    private String accessToken;
    private Long accessTokenExp;
    private String refreshToken;
    private Long refreshTokenExp;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Token token)) return false;
        return Objects.equals(accessToken, token.accessToken)
                && Objects.equals(accessTokenExp, token.accessTokenExp)
                && Objects.equals(refreshToken, token.refreshToken)
                && Objects.equals(refreshTokenExp, token.refreshTokenExp);
    }

    @Override
    public int hashCode() {
        return Objects.hash(accessToken, accessTokenExp, refreshToken, refreshTokenExp);
    }
}

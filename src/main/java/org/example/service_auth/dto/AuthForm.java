package org.example.service_auth.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.Data;
import org.example.service_auth.dto.enums.ResponseTypeEnum;

@Data
public class AuthForm {

    @JsonAlias({"access_token", "token_access"})
    private String token_access;

    @JsonAlias("refresh_token")
    private String token_refresh;
    private String username;

    private String password;
    private String redirectTo;

    private Boolean decode = Boolean.FALSE;
    private ResponseTypeEnum responseType;
}

package org.example.service_auth.dto.enums;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public enum ResponseTypeEnum {
    @JsonProperty("USER")
    USER,
    @JsonProperty("CLIENT")
    CLIENT,
    @JsonProperty("SERVER")
    SERVER,
    @JsonProperty("refresh")
    refresh
}
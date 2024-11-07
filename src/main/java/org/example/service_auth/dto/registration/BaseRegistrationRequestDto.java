package org.example.service_auth.dto.registration;

import lombok.Data;
import org.example.service_auth.dto.enums.ResponseTypeEnum;

@Data
public abstract class BaseRegistrationRequestDto {
    private String password;
    private ResponseTypeEnum responseType;
}

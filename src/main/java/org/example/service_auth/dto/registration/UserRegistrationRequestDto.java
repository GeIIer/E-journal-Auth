package org.example.service_auth.dto.registration;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserRegistrationRequestDto extends BaseRegistrationRequestDto {
    private String username;
    private String firstName;
    private String secondName;
    private String lastName;
    private String email;
    private String phone;
}

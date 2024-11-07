package org.example.service_auth.dto;

import lombok.Data;

@Data
public class UserDto {
    private Long id;
    private String username;
    private String firstName;
    private String secondName;
    private String lastName;
    private String email;
    private String phone;
}

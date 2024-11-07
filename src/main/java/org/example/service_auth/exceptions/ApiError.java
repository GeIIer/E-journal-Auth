package org.example.service_auth.exceptions;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class ApiError {
    private String path;
    private String message;
    private int status;
    LocalDateTime localDateTime;
}

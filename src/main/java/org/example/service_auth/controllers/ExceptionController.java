package org.example.service_auth.controllers;


import org.example.service_auth.exceptions.ApiError;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.reactive.result.method.annotation.ResponseEntityExceptionHandler;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

@RestControllerAdvice
public class ExceptionController extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = UsernameNotFoundException.class)
    public Mono<ResponseEntity<?>> handleException(UsernameNotFoundException exception, ServerWebExchange exchange) {
        logger.error(exception.getMessage());
        ApiError apiError = new ApiError(
                exchange.getRequest().getURI().getPath(),
                exception.getMessage(),
                400,
                LocalDateTime.now()
        );
        return Mono.just(new ResponseEntity<>(apiError, HttpStatus.BAD_REQUEST));
    }
}

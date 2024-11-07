package org.example.service_auth.controllers;

import lombok.RequiredArgsConstructor;
import org.example.service_auth.dto.UserDto;
import org.example.service_auth.services.UserService;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/{userId}")
    public Mono<UserDto> getUser(@PathVariable Long userId) {
        return Mono.just(userService.getUserInfo(userId));
    }
}

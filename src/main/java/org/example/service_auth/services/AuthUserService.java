package org.example.service_auth.services;

import org.example.service_auth.dto.AccessTokenValue;
import org.example.service_auth.dto.AuthForm;
import org.example.service_auth.dto.Token;
import org.example.service_auth.dto.UserDto;
import org.example.service_auth.dto.registration.RegistrationResponseDto;
import org.example.service_auth.dto.registration.UserRegistrationRequestDto;
import org.example.service_auth.exceptions.AlreadyExistException;
import org.example.service_auth.jwt.JwtService;
import org.example.service_auth.models.Role;
import org.example.service_auth.models.User;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import reactor.core.publisher.SynchronousSink;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class AuthUserService implements AuthService<UserDto, UserRegistrationRequestDto> {
    private static final String SERVICE_TYPE = "USER";
    private final UserService userService;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;

    public AuthUserService(UserService userService,
                           JwtService jwtService,
                           PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.jwtService = jwtService;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public boolean supportType(String type) {
        return SERVICE_TYPE.equals(type);
    }

    @Override
    public Mono<Token> generateToken(AuthForm form) {
        return Mono.just(userService.findByUsername(form.getUsername()).orElseThrow(
                        () -> new UsernameNotFoundException(String.format("Пользователь с такими данными не найден %s", form.getUsername()))
                ))
                .handle((user, sink) -> {
                    boolean checkPassword = passwordEncoder.matches(form.getPassword(), user.getPassword());
                    if (checkPassword) {
                        generateUserToken(user, sink);
                        return;
                    }
                    sink.error(new UsernameNotFoundException(String.format("Пользователь с такими данными не найден %s", form.getUsername())));
                });
    }

    public Mono<RegistrationResponseDto> signUp(UserRegistrationRequestDto register) {
        if (userService.findByEmail(register.getEmail()).isPresent()) {
            throw new AlreadyExistException("Email уже зарегистрирован");
        }
        if (userService.findByUsername(register.getUsername()).isPresent()) {
            throw new AlreadyExistException("Username уже зарегистрирован");
        }
        User newUser = User.builder()
                .username(register.getUsername())
                .password(passwordEncoder.encode(register.getPassword()))
                .email(register.getEmail())
                .role(Role.ROLE_USER)
                .firstName(register.getFirstName())
                .secondName(register.getSecondName())
                .lastName(register.getLastName())
                .phone(register.getPhone())
                .build();
        return Mono.just(userService.save(newUser))
                .flatMap(userDto -> Mono.just(RegistrationResponseDto.builder()
                        .username(userDto.getUsername())
                        .email(userDto.getEmail())
                        .id(userDto.getId())
                        .build()));
    }

    @Override
    public Mono<Token> refreshToken(Token token) {
        Long userId = jwtService.parseRefreshJwt(token.getRefreshToken());
        return Mono.just(userService.findById(userId).orElseThrow(
                        () -> new UsernameNotFoundException(String.format("Пользователь с такими данными не найден %s", userId))
                ))
                .handle(this::generateUserToken);
    }

    @Override
    public Mono<Object> logout(String accessToken, ServerHttpResponse response, ServerHttpRequest request) {
        return null;
    }

    @Override
    public Mono<UserDto> getUserData(AccessTokenValue accessToken) {
        return null;
    }

    private void generateUserToken(User user, SynchronousSink<Token> sink) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("id", user.getId());
        claims.put("email", user.getEmail());
        claims.put("scope", List.of(user.getRole()));
        claims.put("type", "USER");
        sink.next(jwtService.generateToken(claims));
    }
}

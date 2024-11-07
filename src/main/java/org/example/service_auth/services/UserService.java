package org.example.service_auth.services;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.example.service_auth.dto.UserDto;
import org.example.service_auth.mappers.UserMapper;
import org.example.service_auth.models.User;
import org.example.service_auth.repositories.UserRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;


@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public UserDto getUserInfo(Long userId) {
        return userMapper.toDto(userRepository.findById(userId).orElseThrow(
                () -> new EntityNotFoundException(String.format("User %s", userId))
        ));
    }

    public Mono<User> findById(Long userId) {
        return Mono.justOrEmpty(userRepository.findById(userId));
    }

    public Mono<User> findByUsername(String username) {
        return Mono.justOrEmpty(userRepository.findByUsername(username));
    }

    public Mono<User> findByEmail(String email) {
        return Mono.justOrEmpty(userRepository.findByEmail(email));
    }

    public Mono<UserDto> save(User user) {
        return Mono.just(userMapper.toDto(userRepository.save(user)));
    }
}

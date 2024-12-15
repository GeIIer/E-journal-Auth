package org.example.service_auth.services;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.example.service_auth.dto.UserDto;
import org.example.service_auth.mappers.UserMapper;
import org.example.service_auth.models.User;
import org.example.service_auth.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;


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

    public Optional<User> findById(Long userId) {
        return userRepository.findById(userId);
    }

    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public UserDto save(User user) {
        return userMapper.toDto(userRepository.save(user));
    }
}

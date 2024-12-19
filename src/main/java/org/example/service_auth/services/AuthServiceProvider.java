package org.example.service_auth.services;

import org.example.service_auth.services.provide.BaseProviderService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthServiceProvider extends BaseProviderService<AuthService<?>, Class<?>> {
    public AuthServiceProvider(List<AuthService<?>> services) {
        super(services);
    }
}

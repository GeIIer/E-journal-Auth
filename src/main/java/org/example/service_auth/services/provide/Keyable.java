package org.example.service_auth.services.provide;

import com.fasterxml.jackson.annotation.JsonIgnore;

public interface Keyable<KeyType> {
    @JsonIgnore
    KeyType getKey();
}

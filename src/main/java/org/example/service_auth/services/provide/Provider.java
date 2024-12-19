package org.example.service_auth.services.provide;

public interface Provider<ProvidedType extends Providable<KeyType>, KeyType> {
    ProvidedType getByKey(KeyType key);
}
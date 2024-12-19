package org.example.service_auth.services.provide;

import java.util.*;

public abstract class BaseProviderService<ProvidedType extends Providable<KeyType>, KeyType> implements Provider<ProvidedType, KeyType>{
    Map<KeyType, ProvidedType> serviceMap = new HashMap<>();

    public BaseProviderService(List<ProvidedType> services) {
        services.forEach((el) -> serviceMap.put(el.getKey(), el));
    }

    public ProvidedType getByKey(KeyType key) {
        return serviceMap.get(key);
    }

    public Set<ProvidedType> getAllServices(){
        return new HashSet<>(this.serviceMap.values());
    }
}

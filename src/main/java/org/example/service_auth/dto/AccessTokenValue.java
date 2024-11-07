package org.example.service_auth.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collections;
import java.util.Set;

@Data
@NoArgsConstructor
public class AccessTokenValue {
    private Long id;
    private String name;
    private String type;
    private Set<String> scope;

    public AccessTokenValue(Long id, String name, String type, Set<String> scope) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.scope = Collections.unmodifiableSet(scope);
    }
}

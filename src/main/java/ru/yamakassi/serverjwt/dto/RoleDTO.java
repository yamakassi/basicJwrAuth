package ru.yamakassi.serverjwt.dto;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

@RequiredArgsConstructor
public enum RoleDTO implements GrantedAuthority {
    ADMIN("ADMIN"),

    USER("USER");

    private final String vale;


    @Override
    public String getAuthority() {
        return vale;
    }
}

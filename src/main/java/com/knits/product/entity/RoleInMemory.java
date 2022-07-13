package com.knits.product.entity;

import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Set;
import java.util.stream.Collectors;

public enum RoleInMemory {
    USER(Set.of(Permission.USER_READ)),
    ADMIN(Set.of(Permission.USER_WRITE, Permission.USER_READ));

    private final Set<Permission> permissions;

    RoleInMemory(Set<Permission> permissions) {
        this.permissions = permissions;
    }

    public Set<Permission> getPermissions() {
        return permissions;
    }

    public Set<SimpleGrantedAuthority> getAuthorities(){
        return getPermissions().stream()
                .map(permission -> new SimpleGrantedAuthority(permission.getPermission()))
                .collect(Collectors.toSet());
    }
}

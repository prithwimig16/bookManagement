package com.aaharan.bookManagement.user;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static com.aaharan.bookManagement.user.Permission.*;

@RequiredArgsConstructor
public enum Role {
//Info: ADMIN is State
    USER(Collections.emptySet()),
    ADMIN(
            Set.of(
                    ADMIN_READ,
                    ADMIN_UPDATE,
                    ADMIN_DELETE,
                    ADMIN_CREATE,
                    SCHOOL_READ,
                    SCHOOL_CREATE,
                    SCHOOL_UPDATE,
                    SCHOOL_DELETE,
                    DEO_READ,
                    DEO_UPDATE,
                    DEO_DELETE,
                    DEO_CREATE,
                    IS_READ,
                    IS_UPDATE,
                    IS_DELETE,
                    IS_CREATE
            )
    ),

    SCHOOL(
            Set.of(
                    SCHOOL_READ,
                    SCHOOL_UPDATE,
                    SCHOOL_DELETE,
                    SCHOOL_CREATE
            )
    ),

    DEO(
            Set.of(
                    DEO_READ,
                    DEO_UPDATE,
                    DEO_DELETE,
                    DEO_CREATE
            )
    ),
    IS(
            Set.of(
                    IS_READ,
                    IS_UPDATE,
                    IS_DELETE,
                    IS_CREATE
            )
    );

    @Getter
    private final Set<Permission> permissions;

    public List<SimpleGrantedAuthority> getAuthorities() {
        var authorities = getPermissions()
                .stream()
                .map(permission -> new SimpleGrantedAuthority(permission.getPermission()))
                .collect(Collectors.toList());
        authorities.add(new SimpleGrantedAuthority("ROLE_" + this.name()));
        return authorities;
    }
}

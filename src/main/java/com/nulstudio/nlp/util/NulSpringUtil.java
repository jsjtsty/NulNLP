package com.nulstudio.nlp.util;

import com.nulstudio.nlp.domain.cache.CachedRole;
import org.jetbrains.annotations.NotNull;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.HashSet;
import java.util.Set;

public final class NulSpringUtil {
    @NotNull
    public static Set<SimpleGrantedAuthority> extractSpringAuthorities(@NotNull CachedRole role) {
        final Set<SimpleGrantedAuthority> authorities = new HashSet<>();
        for (final String authority : role.getPermissions()) {
            authorities.add(new SimpleGrantedAuthority(authority));
        }
        authorities.add(new SimpleGrantedAuthority("ROLE_" + role.getName()));
        return authorities;
    }
}

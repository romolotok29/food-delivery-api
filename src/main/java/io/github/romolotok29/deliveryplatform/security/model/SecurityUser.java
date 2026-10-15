package io.github.romolotok29.deliveryplatform.security.model;

import io.github.romolotok29.deliveryplatform.account.entity.User;
import lombok.Getter;
import org.jspecify.annotations.Nullable;
import org.springframework.security.core.CredentialsContainer;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class SecurityUser implements UserDetails, CredentialsContainer {

    @Getter
    private final Long userId;
    @Getter
    private final String fullName;
    private final String emailAddress;
    private String password;
    private final boolean isEmailVerified;
    private final Instant accountLockedUntil;
    private final Collection<? extends GrantedAuthority> authorities;

    public SecurityUser(User user) {
        this(
                user.getId(),
                user.getFullName(),
                user.getEmailAddress(),
                user.getPassword(),
                user.isEmailVerified(),
                user.getAccountLockedUntil(),
                Collections.unmodifiableList(new ArrayList<>(
                        List.of(new SimpleGrantedAuthority("ROLE_" + user.getRole().name()))))
        );
    }

    public SecurityUser(
            Long userId,
            String fullName,
            String emailAddress,
            String password,
            boolean isEmailVerified,
            Instant accountLockedUntil,
            Collection<? extends GrantedAuthority> authorities
    ) {
        this.userId = userId;
        this.fullName = fullName;
        this.emailAddress = emailAddress;
        this.password = password;
        this.isEmailVerified = isEmailVerified;
        this.accountLockedUntil = accountLockedUntil;
        this.authorities = authorities;
    }

    @Override
    public String getUsername() {
        return emailAddress;
    }

    @Override
    public @Nullable String getPassword() {
        return password;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public boolean isEnabled() {
        return isEmailVerified;
    }

    @Override
    public boolean isAccountNonLocked() {
        return accountLockedUntil == null || !accountLockedUntil.isAfter(Instant.now());
    }

    @Override
    public void eraseCredentials() {
        this.password = null;
    }

}
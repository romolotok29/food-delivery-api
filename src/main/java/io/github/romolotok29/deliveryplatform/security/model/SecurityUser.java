package io.github.romolotok29.deliveryplatform.security.model;

import io.github.romolotok29.deliveryplatform.account.entity.User;
import lombok.Getter;
import org.jspecify.annotations.Nullable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.Instant;
import java.util.Collection;
import java.util.List;

public class SecurityUser implements UserDetails {

    @Getter
    private final Long userId;
    @Getter
    private final String fullName;
    private final String emailAddress;
    private final String password;
    private final boolean isEmailVerified;
    private final Instant accountLockedUntil;
    private final Collection<? extends GrantedAuthority> authorities;

    public SecurityUser(User user) {
        this.userId = user.getId();
        this.fullName = user.getFullName();
        this.emailAddress = user.getEmailAddress();
        this.password = user.getPassword();
        this.isEmailVerified = user.isEmailVerified();
        this.accountLockedUntil = user.getAccountLockedUntil();
        this.authorities = List.of(new SimpleGrantedAuthority("ROLE_" + user.getRole().name()));
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

}
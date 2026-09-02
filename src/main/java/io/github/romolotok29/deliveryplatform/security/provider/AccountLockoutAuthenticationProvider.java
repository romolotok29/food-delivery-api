package io.github.romolotok29.deliveryplatform.security.provider;

import io.github.romolotok29.deliveryplatform.authentication.service.IAccountLockoutService;
import org.jspecify.annotations.Nullable;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class AccountLockoutAuthenticationProvider extends DaoAuthenticationProvider {

    private final IAccountLockoutService accountLockoutService;

    public AccountLockoutAuthenticationProvider(
            UserDetailsService userDetailsService,
            IAccountLockoutService accountLockoutService,
            PasswordEncoder passwordEncoder
    ) {
        super(userDetailsService);
        this.accountLockoutService = accountLockoutService;
        setPasswordEncoder(passwordEncoder);
    }

    @Override
    public @Nullable Authentication authenticate(Authentication authentication) throws AuthenticationException {

        try {
            Authentication result = super.authenticate(authentication);

            accountLockoutService.resetLockout(authentication.getName());

            return result;

        } catch (BadCredentialsException ex) {

            accountLockoutService.recordFailedAttempts(
                    authentication.getName()
            );

            throw ex;
        }
    }

}
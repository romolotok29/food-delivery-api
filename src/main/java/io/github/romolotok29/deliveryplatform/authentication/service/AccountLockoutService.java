package io.github.romolotok29.deliveryplatform.authentication.service;

import io.github.romolotok29.deliveryplatform.account.entity.User;
import io.github.romolotok29.deliveryplatform.account.repository.UserRepository;
import io.github.romolotok29.deliveryplatform.exceptions.authentication.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.Instant;

@Service
@RequiredArgsConstructor
public class AccountLockoutService implements IAccountLockoutService {

    private static final int MAX_ATTEMPTS = 4;
    private static final Duration LOCK_DURATION = Duration.ofMinutes(15);
    private final UserRepository userRepository;

    @Override
    @Transactional
    public void recordFailedAttempts(String emailAddress) {

        User user = userRepository.findUserByEmailAddress(emailAddress).orElseThrow();

        int attempts = user.getFailedLoginAttempts() + 1;
        user.setFailedLoginAttempts(attempts);

        if (attempts >= MAX_ATTEMPTS) {
            user.setAccountLockedUntil(
                    Instant.now().plus(LOCK_DURATION)
            );
        }
    }

    @Override
    public void resetLockout(String emailAddress) {

        User user = userRepository
                .findUserByEmailAddress(emailAddress)
                .orElseThrow(UserNotFoundException::new);

        user.setFailedLoginAttempts(0);
        user.setAccountLockedUntil(null);
    }

}
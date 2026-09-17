package io.github.romolotok29.deliveryplatform.account.service;

import io.github.romolotok29.deliveryplatform.cache.AccountDetailsCache;
import io.github.romolotok29.deliveryplatform.account.entity.User;
import io.github.romolotok29.deliveryplatform.account.repository.UserRepository;
import io.github.romolotok29.deliveryplatform.cache.CacheNames;
import io.github.romolotok29.deliveryplatform.email_verification.repository.EmailVerificationTokenRepository;
import io.github.romolotok29.deliveryplatform.email_verification.service.EmailVerificationTokenService;
import io.github.romolotok29.deliveryplatform.exceptions.authentication.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AccountService implements IAccountService {

    private final ApplicationEventPublisher applicationEventPublisher;
    private final UserRepository userRepository;
    private final EmailVerificationTokenService tokenService;
    private final EmailVerificationTokenRepository tokenRepository;

    @Cacheable(cacheNames = CacheNames.USERS, key = "#userId")
    @Override
    public AccountDetailsCache getCurrentAccountDetails(Long userId) {

        User user = userRepository.findById(userId).orElseThrow(UserNotFoundException::new);

        return new AccountDetailsCache(
                user.getFullName(),
                user.getPhoneNumber(),
                user.getEmailAddress()
        );
    }

}
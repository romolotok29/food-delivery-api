package io.github.romolotok29.deliveryplatform.account.service;

import io.github.romolotok29.deliveryplatform.account.dto.EditAccountProfileRequest;
import io.github.romolotok29.deliveryplatform.cache.AccountDetailsCache;
import io.github.romolotok29.deliveryplatform.account.entity.User;
import io.github.romolotok29.deliveryplatform.account.repository.UserRepository;
import io.github.romolotok29.deliveryplatform.cache.CacheNames;
import io.github.romolotok29.deliveryplatform.email_verification.entity.EmailVerificationToken;
import io.github.romolotok29.deliveryplatform.email_verification.event.EmailChangedEvent;
import io.github.romolotok29.deliveryplatform.email_verification.repository.EmailVerificationTokenRepository;
import io.github.romolotok29.deliveryplatform.email_verification.service.EmailVerificationTokenService;
import io.github.romolotok29.deliveryplatform.exceptions.authentication.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    @Transactional
    @CachePut(cacheNames = CacheNames.USERS, key = "#userId") //#user.id?
    @Override
    public AccountDetailsCache editProfile(Long userId, EditAccountProfileRequest request) {

        User currentUser = userRepository.findById(userId).orElseThrow(UserNotFoundException::new);

        currentUser.setFullName(request.getFullName());
        currentUser.setPhoneNumber(request.getPhoneNumber());

        userRepository.save(currentUser);

        if (!request.getEmailAddress().isBlank() && !request.getEmailAddress().equals(currentUser.getEmailAddress())) {

            currentUser.setEmailAddress(request.getEmailAddress());

            userRepository.save(currentUser);

            String rawToken = tokenService.generateToken();

            EmailVerificationToken token = tokenService.createToken(currentUser, rawToken);

            tokenRepository.save(token);

            applicationEventPublisher.publishEvent(
                    new EmailChangedEvent(
                            currentUser.getEmailAddress(),
                            rawToken
                    )
            );
        }

        return new AccountDetailsCache(
                currentUser.getFullName(),
                currentUser.getPhoneNumber(),
                currentUser.getEmailAddress()
        );
    }

    @Transactional
    @CacheEvict(cacheNames = CacheNames.USERS, key = "#userId")
    @Override
    @PreAuthorize("hasRole = ('USER')")
    public void deleteAccount(Long userId) {

        User currentUser = userRepository.findById(userId).orElseThrow(UserNotFoundException::new);

        userRepository.deleteById(userId);

    }

}
package io.github.romolotok29.deliveryplatform.account.service;

import io.github.romolotok29.deliveryplatform.account.dto.EditAccountProfileRequest;
import io.github.romolotok29.deliveryplatform.cache.ProfileCache;
import io.github.romolotok29.deliveryplatform.account.entity.User;
import io.github.romolotok29.deliveryplatform.account.repository.UserRepository;
import io.github.romolotok29.deliveryplatform.cache.CacheNames;
import io.github.romolotok29.deliveryplatform.email_verification.entity.EmailVerificationToken;
import io.github.romolotok29.deliveryplatform.email_verification.event.AccountDeletedEvent;
import io.github.romolotok29.deliveryplatform.email_verification.event.EmailChangeRequestedEvent;
import io.github.romolotok29.deliveryplatform.email_verification.repository.EmailVerificationTokenRepository;
import io.github.romolotok29.deliveryplatform.email_verification.service.verification.EmailVerificationTokenService;
import io.github.romolotok29.deliveryplatform.exceptions.authentication.UserNotFoundException;
import io.github.romolotok29.deliveryplatform.exceptions.registration.UserAlreadyExistsException;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.ApplicationEventPublisher;
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
    public ProfileCache getCurrentAccountDetails(Long userId) {

        User user = userRepository.findById(userId).orElseThrow(UserNotFoundException::new);

        return new ProfileCache(
                user.getFullName(),
                user.getPhoneNumber(),
                user.getEmailAddress()
        );
    }

    @Transactional
    @CachePut(cacheNames = CacheNames.USERS, key = "#userId")
    @Override
    public ProfileCache editProfile(Long userId, EditAccountProfileRequest request) {

        User currentUser = userRepository.findById(userId).orElseThrow(UserNotFoundException::new);

        updateProfileFields(currentUser, request);

        requestEmailChange(currentUser, request.getEmailAddress());

        return new ProfileCache(
                currentUser.getFullName(),
                currentUser.getPhoneNumber(),
                currentUser.getEmailAddress()
        );
    }

    @Transactional
    @CacheEvict(cacheNames = CacheNames.USERS, key = "#userId")
    @Override
    public void deleteAccount(Long userId) {

        User currentUser = userRepository.findById(userId).orElseThrow(UserNotFoundException::new);

        userRepository.delete(currentUser);

        applicationEventPublisher.publishEvent(
                new AccountDeletedEvent(currentUser.getEmailAddress())
        );
    }

    private void updateProfileFields(User user, EditAccountProfileRequest request) {

        if (request.getFullName() != null && !request.getFullName().equals(user.getFullName())) {
            user.setFullName(request.getFullName());
        }

        if  (request.getPhoneNumber() != null && !request.getPhoneNumber().equals(user.getPhoneNumber())) {
            user.setPhoneNumber(request.getPhoneNumber());
        }
    }

    private void requestEmailChange(User user, String requestedNewEmail) {

        if (requestedNewEmail == null
                || requestedNewEmail.equalsIgnoreCase(user.getEmailAddress())) {
            return;
        }

        boolean emailAddressExists = userRepository
                .findUserByEmailAddress(requestedNewEmail)
                .isPresent();

        if (emailAddressExists) {
            throw new UserAlreadyExistsException();
        }

        String rawToken = tokenService.generateToken();

        EmailVerificationToken token = tokenService.createToken(user, rawToken);

        tokenRepository.save(token);

        applicationEventPublisher.publishEvent(
                new EmailChangeRequestedEvent(
                        requestedNewEmail,
                        rawToken
                )
        );
    }

}
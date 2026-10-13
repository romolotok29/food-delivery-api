package io.github.romolotok29.deliveryplatform.email_verification.service.verification;

import io.github.romolotok29.deliveryplatform.account.entity.User;
import io.github.romolotok29.deliveryplatform.account.repository.UserRepository;
import io.github.romolotok29.deliveryplatform.cache.CacheNames;
import io.github.romolotok29.deliveryplatform.email_verification.dto.ResendVerificationRequest;
import io.github.romolotok29.deliveryplatform.email_verification.entity.EmailVerificationToken;
import io.github.romolotok29.deliveryplatform.email_verification.event.EmailChangedEvent;
import io.github.romolotok29.deliveryplatform.email_verification.event.RegistrationEmailVerifiedEvent;
import io.github.romolotok29.deliveryplatform.email_verification.event.VerificationEmailResentEvent;
import io.github.romolotok29.deliveryplatform.email_verification.repository.EmailVerificationTokenRepository;
import io.github.romolotok29.deliveryplatform.exceptions.authentication.UserNotFoundException;
import io.github.romolotok29.deliveryplatform.exceptions.verification.EmailAddressAlreadyVerifiedException;
import io.github.romolotok29.deliveryplatform.exceptions.verification.InvalidVerificationTokenException;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class EmailVerificationService {

    private final UserRepository userRepository;
    private final EmailVerificationTokenRepository tokenRepository;
    private final EmailVerificationTokenService tokenService;
    private final ApplicationEventPublisher applicationEventPublisher;

    @Transactional
    public void verifyRegistrationEmail(String token) {

        EmailVerificationToken verificationToken = tokenService.verifyToken(token);

        User user = verificationToken.getUser();

        if (user.isEmailVerified()) {
            throw new EmailAddressAlreadyVerifiedException();
        }

        user.setEmailVerified(true);

        applicationEventPublisher.publishEvent(new RegistrationEmailVerifiedEvent(user.getEmailAddress()));
    }

    @Transactional
    @CacheEvict(cacheNames = CacheNames.USERS, key = "#userId")
    public void verifyEmailChange(String token, Long userId) {

        EmailVerificationToken verificationToken = tokenService.verifyToken(token);

        User user = verificationToken.getUser();

        if (!user.getId().equals(userId)) {
            throw new InvalidVerificationTokenException();
        }

        String pendingEmail = user.getPendingEmail();

        user.setEmailAddress(pendingEmail);
        user.setPendingEmail(null);

        applicationEventPublisher.publishEvent(new EmailChangedEvent(user.getEmailAddress()));
    }

    @Transactional
    public void resendVerificationToken(ResendVerificationRequest request) {

        User user = findUserForVerification(request.emailAddress());

        tokenRepository.deleteTokenByUser_Id(user.getId());

        String rawToken = tokenService.generateToken();

        EmailVerificationToken newToken = tokenService.createToken(user, rawToken);

        tokenRepository.save(newToken);

        applicationEventPublisher.publishEvent(new VerificationEmailResentEvent(
                        user.getEmailAddress(),
                        rawToken
                )
        );
    }

    public User findUserForVerification(String emailAddress) {

        User user = userRepository.findUserByEmailAddress(emailAddress)
                .orElseThrow(UserNotFoundException::new);

        if (user.isEmailVerified()) {
            throw new EmailAddressAlreadyVerifiedException();
        }

        return user;
    }

}
package io.github.romolotok29.deliveryplatform.email_verification.service;

import io.github.romolotok29.deliveryplatform.account.entity.User;
import io.github.romolotok29.deliveryplatform.account.repository.UserRepository;
import io.github.romolotok29.deliveryplatform.email_verification.dto.ResendConfirmationRequest;
import io.github.romolotok29.deliveryplatform.email_verification.entity.EmailVerificationToken;
import io.github.romolotok29.deliveryplatform.email_verification.event.VerificationEmailResentEvent;
import io.github.romolotok29.deliveryplatform.email_verification.repository.EmailVerificationTokenRepository;
import io.github.romolotok29.deliveryplatform.exceptions.authentication.UserNotFoundException;
import io.github.romolotok29.deliveryplatform.exceptions.verification.EmailAddressAlreadyVerifiedException;
import lombok.RequiredArgsConstructor;
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
    public void confirmEmailAddress(String token) {

        EmailVerificationToken verificationToken = tokenService.verifyToken(token);

        User user = verificationToken.getUser();
        user.setEmailVerified(true);

        userRepository.save(user);
    }

    @Transactional
    public void resendVerificationToken(ResendConfirmationRequest request) {

        User foundUser = userRepository.findUserByEmailAddress(request.emailAddress())
                .orElseThrow(UserNotFoundException::new);

        if (foundUser.isEmailVerified()) {
            throw new EmailAddressAlreadyVerifiedException();
        }

        Long userId = foundUser.getId();

        tokenRepository.deleteTokenByUser_Id(userId);

        String rawToken = tokenService.generateToken();

        EmailVerificationToken newToken = tokenService.createToken(foundUser, rawToken);

        tokenRepository.save(newToken);

        applicationEventPublisher.publishEvent(
                new VerificationEmailResentEvent(
                        foundUser.getEmailAddress(),
                        rawToken
                )
        );
    }

}
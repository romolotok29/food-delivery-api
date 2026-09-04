package io.github.romolotok29.deliveryplatform.reset_password.service;

import io.github.romolotok29.deliveryplatform.account.entity.User;
import io.github.romolotok29.deliveryplatform.account.repository.UserRepository;
import io.github.romolotok29.deliveryplatform.email_verification.entity.EmailVerificationToken;
import io.github.romolotok29.deliveryplatform.email_verification.repository.EmailVerificationTokenRepository;
import io.github.romolotok29.deliveryplatform.email_verification.service.EmailVerificationTokenService;
import io.github.romolotok29.deliveryplatform.exceptions.authentication.UserNotFoundException;
import io.github.romolotok29.deliveryplatform.reset_password.dto.ForgotPasswordRequest;
import io.github.romolotok29.deliveryplatform.reset_password.event.ForgotPasswordEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ForgotPasswordService {

    private final UserRepository userRepository;
    private final EmailVerificationTokenService tokenService;
    private final ApplicationEventPublisher applicationEventPublisher;

    private final EmailVerificationTokenRepository tokenRepository;

    @Transactional
    public void forgotPassword(ForgotPasswordRequest request) {

        String emailAddress = request.emailAddress();

        User user = userRepository.findUserByEmailAddress(emailAddress)
                .orElseThrow(UserNotFoundException::new);

        String rawToken = tokenService.generateToken();

        EmailVerificationToken token = tokenService.createToken(user, rawToken);

        tokenRepository.save(token);

        applicationEventPublisher.publishEvent(
                new ForgotPasswordEvent(
                        emailAddress,
                        rawToken
                )
        );
    }

}
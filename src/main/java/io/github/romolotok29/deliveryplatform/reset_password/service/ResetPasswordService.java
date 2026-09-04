package io.github.romolotok29.deliveryplatform.reset_password.service;

import io.github.romolotok29.deliveryplatform.account.entity.User;
import io.github.romolotok29.deliveryplatform.account.repository.UserRepository;
import io.github.romolotok29.deliveryplatform.email_verification.entity.EmailVerificationToken;
import io.github.romolotok29.deliveryplatform.email_verification.service.EmailVerificationTokenService;
import io.github.romolotok29.deliveryplatform.exceptions.verification.UnverifiedEmailAddressException;
import io.github.romolotok29.deliveryplatform.reset_password.dto.ResetPasswordRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ResetPasswordService {

    private final UserRepository userRepository;
    private final EmailVerificationTokenService tokenService;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public void resetPassword(ResetPasswordRequest request) {

        EmailVerificationToken resetPasswordToken = tokenService.verifyToken(request.verificationToken());

        User user = resetPasswordToken.getUser();

        if (!user.isEmailVerified()) {
            throw new UnverifiedEmailAddressException();
        }

        user.setPassword(passwordEncoder.encode(request.newPassword()));

        userRepository.save(user);
    }

}
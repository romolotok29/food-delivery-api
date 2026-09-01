package io.github.romolotok29.deliveryplatform.email_verification.service;

import io.github.romolotok29.deliveryplatform.account.entity.User;
import io.github.romolotok29.deliveryplatform.email_verification.entity.EmailVerificationToken;

public interface IEmailVerificationTokenService {

    String generateToken();

    EmailVerificationToken createToken(User user, String rawToken);

    String hashToken(String token);

    EmailVerificationToken verifyToken(String token);

}
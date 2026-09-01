package io.github.romolotok29.deliveryplatform.email_verification.service;

import io.github.romolotok29.deliveryplatform.account.entity.User;
import io.github.romolotok29.deliveryplatform.email_verification.entity.EmailVerificationToken;
import io.github.romolotok29.deliveryplatform.email_verification.repository.EmailVerificationTokenRepository;
import io.github.romolotok29.deliveryplatform.exceptions.verification.EmailAddressAlreadyVerifiedException;
import io.github.romolotok29.deliveryplatform.exceptions.verification.VerificationTokenExpiredException;
import io.github.romolotok29.deliveryplatform.exceptions.verification.VerificationTokenNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Base64;
import java.util.HexFormat;

@Service
@RequiredArgsConstructor
public class EmailVerificationTokenService implements IEmailVerificationTokenService {

    private final EmailVerificationTokenRepository emailVerificationTokenRepository;
    private final SecureRandom secureRandom = new SecureRandom();

    @Override
    public String generateToken() {

        byte[] randomBytes = new byte[32];
        secureRandom.nextBytes(randomBytes);

        return Base64
                .getUrlEncoder()
                .withoutPadding()
                .encodeToString(randomBytes);
    }

    @Override
    public EmailVerificationToken createToken(User user, String rawToken) {

        Instant now = Instant.now();

        return EmailVerificationToken.builder()
                .user(user)
                .tokenHash(hashToken(rawToken))
                .createdAt(now)
                .expiresAt(now.plus(15, ChronoUnit.MINUTES))
                .build();
    }

    @Override
    public String hashToken(String token) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hash = md.digest(token.getBytes(StandardCharsets.UTF_8));

            return HexFormat.of().formatHex(hash);

        } catch (NoSuchAlgorithmException e)  {
            throw new IllegalStateException("SHA-256 algorithm is not available", e);
        }

    }

    @Override
    @Transactional
    public EmailVerificationToken verifyToken(String token) {

        String tokenHash = hashToken(token);

        EmailVerificationToken foundToken = emailVerificationTokenRepository.findByTokenHash(tokenHash)
                .orElseThrow(VerificationTokenNotFoundException::new);

        Instant now = Instant.now();

        if (!foundToken.getExpiresAt().isAfter(now)) {
            throw new VerificationTokenExpiredException();
        }

        if (foundToken.getVerifiedAt() != null) {
            throw new EmailAddressAlreadyVerifiedException();
        }

        foundToken.setVerifiedAt(now);

        return emailVerificationTokenRepository.save(foundToken);
    }

}
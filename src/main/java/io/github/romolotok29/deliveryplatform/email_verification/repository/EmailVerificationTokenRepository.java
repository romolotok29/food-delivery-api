package io.github.romolotok29.deliveryplatform.email_verification.repository;

import io.github.romolotok29.deliveryplatform.email_verification.entity.EmailVerificationToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface EmailVerificationTokenRepository extends JpaRepository<EmailVerificationToken, UUID> {

    Optional<EmailVerificationToken> findByTokenHash(String token);

    void deleteTokenByUser_Id(Long userId);

}
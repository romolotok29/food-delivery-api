package io.github.romolotok29.deliveryplatform.email_verification.event;

public record VerificationEmailResentEvent(String emailAddress, String token) {}

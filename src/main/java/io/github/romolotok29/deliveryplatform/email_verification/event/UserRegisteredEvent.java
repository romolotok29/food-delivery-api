package io.github.romolotok29.deliveryplatform.email_verification.event;

public record UserRegisteredEvent (
    String emailAddress,
    String verificationToken
) {

}
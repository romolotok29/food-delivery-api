package io.github.romolotok29.deliveryplatform.email_verification.event;

public record EmailChangedEvent(String emailAddress, String verificationToken) {

}
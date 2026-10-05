package io.github.romolotok29.deliveryplatform.exceptions.verification;

public class InvalidVerificationTokenException extends RuntimeException {

    public InvalidVerificationTokenException() {
        super("Invalid verification token.");
    }

}
package io.github.romolotok29.deliveryplatform.exceptions.verification;

public class VerificationTokenAlreadyUsedException extends RuntimeException {

    public VerificationTokenAlreadyUsedException() {
        super("Verification token has already been used.");
    }

}
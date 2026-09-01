package io.github.romolotok29.deliveryplatform.exceptions.verification;

public class VerificationTokenNotFoundException extends RuntimeException {

    public VerificationTokenNotFoundException() {
        super("Verification token not found. Ensure that the registration process has been completed.");
    }

}
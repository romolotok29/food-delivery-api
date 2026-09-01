package io.github.romolotok29.deliveryplatform.exceptions.verification;

public class VerificationTokenExpiredException extends RuntimeException {

    public VerificationTokenExpiredException() {
        super("Verification token has expired. Please provide an email address to resend verification.");
    }

}
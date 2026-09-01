package io.github.romolotok29.deliveryplatform.exceptions.verification;

public class EmailAddressAlreadyVerifiedException extends RuntimeException {
    public EmailAddressAlreadyVerifiedException() {
        super("Provided email address has already been verified.");
    }

}
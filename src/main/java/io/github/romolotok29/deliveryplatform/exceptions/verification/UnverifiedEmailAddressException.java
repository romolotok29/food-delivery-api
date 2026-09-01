package io.github.romolotok29.deliveryplatform.exceptions.verification;

public class UnverifiedEmailAddressException extends RuntimeException {

    public UnverifiedEmailAddressException() {
        super("Provided email address has not been verified.");
    }

}
package io.github.romolotok29.deliveryplatform.exceptions.authentication;

public class InvalidCredentialsException extends RuntimeException {

    public InvalidCredentialsException() {
        super("Invalid email address or password.");
    }

}
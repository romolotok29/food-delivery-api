package io.github.romolotok29.deliveryplatform.exceptions.registration;

public class UserAlreadyExistsException extends RuntimeException {

    public UserAlreadyExistsException() {
        super("A user with these credentials already exists.");
    }

}
package io.github.romolotok29.deliveryplatform.exceptions.registration;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException() {
        super("A user with the provided email address wasn't found.");
    }

}
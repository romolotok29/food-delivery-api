package io.github.romolotok29.deliveryplatform.exceptions.authentication;

public class AccountTemporarilyLockedException extends RuntimeException {
    public AccountTemporarilyLockedException() {
        super("Account is temporarily locked.");
    }

}
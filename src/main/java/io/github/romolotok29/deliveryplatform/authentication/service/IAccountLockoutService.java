package io.github.romolotok29.deliveryplatform.authentication.service;

public interface IAccountLockoutService {

    void recordFailedAttempts(String emailAddress);

    void resetLockout(String emailAddress);
}
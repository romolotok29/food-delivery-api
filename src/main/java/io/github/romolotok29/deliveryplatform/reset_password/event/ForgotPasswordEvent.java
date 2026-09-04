package io.github.romolotok29.deliveryplatform.reset_password.event;

public record ForgotPasswordEvent(String emailAddress, String verificationToken) {}
package io.github.romolotok29.deliveryplatform.cache;

public record AccountDetailsCache (
        String fullName,
        String phoneNumber,
        String emailAddress
) {
}
package io.github.romolotok29.deliveryplatform.email_verification.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record ResendVerificationRequest(
        @NotBlank(message = "This field can not be blank.")
        @Email(message = "Invalid email address format.")
        String emailAddress
) {

}
package io.github.romolotok29.deliveryplatform.authentication.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record SignInRequest(
        @NotBlank(message = "Please provide an email address.")
        @Email(message = "Invalid email address format.")
        String emailAddress,

        @NotBlank(message = "Please provide a password.")
        String password
) {}
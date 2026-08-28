package io.github.romolotok29.deliveryplatform.authentication.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record SignInRequest(
        @NotBlank(message = "Please provide an email address.")
        @Email(message = "Invalid email address format.")
        @JsonProperty("email_address")
        String emailAddress,
        @NotBlank(message = "Please provide a password.")
        String password
) {}
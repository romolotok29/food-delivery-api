package io.github.romolotok29.deliveryplatform.reset_password.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record ForgotPasswordRequest(
        @NotBlank(message = "This field can not be blank.")
        @Email(message = "Invalid email address format.")
        @JsonProperty("email_address")
        String emailAddress
) {

}
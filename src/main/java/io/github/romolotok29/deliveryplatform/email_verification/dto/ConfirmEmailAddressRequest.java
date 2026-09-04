package io.github.romolotok29.deliveryplatform.email_verification.dto;

import jakarta.validation.constraints.NotBlank;

public record ConfirmEmailAddressRequest(@NotBlank(message = "This field can not be blank.") String token) {

}
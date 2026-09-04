package io.github.romolotok29.deliveryplatform.reset_password.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.github.romolotok29.deliveryplatform.validation.PasswordsMatch;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import tools.jackson.databind.PropertyNamingStrategies;
import tools.jackson.databind.annotation.JsonNaming;

@JsonNaming(value = PropertyNamingStrategies.SnakeCaseStrategy.class)
@PasswordsMatch(password = "newPassword", confirmPassword = "confirmNewPassword")
public record ResetPasswordRequest(
        @NotBlank(message = "This field can't be blank.")
        @JsonProperty("verification_token")
        String verificationToken,

        @NotBlank(message = "This field can't be blank.")
        @Size(min = 8, max = 20, message = "Password must be 8-18 characters long.")
        @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#$%^&*+=]).*$",
                message = """
                    Password must contain at least one digit,\s
                    one lowercase letter, one uppercase letter and one special character.""")
        @JsonProperty("new_password")
        String newPassword,

        @NotBlank(message = "This field can't be blank.")
        @JsonProperty("confirm_new_password")
        String confirmNewPassword
) {

}
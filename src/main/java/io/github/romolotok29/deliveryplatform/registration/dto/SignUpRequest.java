package io.github.romolotok29.deliveryplatform.registration.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.github.romolotok29.deliveryplatform.validation.PasswordsMatch;
import jakarta.validation.constraints.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@PasswordsMatch(
        password = "password",
        confirmPassword = "confirmPassword")
public class SignUpRequest {

    @NotBlank(message = "This field is required to be filled in.")
    @Size(min = 2, max = 50, message = "Full name must be 2-50 characters long.")
    @JsonProperty("full_name")
    private String fullName;

    @NotBlank(message = "This field can not be blank.")
    @Email(message = "Invalid email address format.")
    @JsonProperty("email_address")
    private String emailAddress;

    @NotBlank(message = "This field can not be blank.")
    @JsonProperty("phone_number")
    @Pattern(
            regexp = "^\\+[1-9]\\d{8,14}$",
            message = "Invalid phone number format.")
    private String phoneNumber;

    @NotBlank(message = "This field can't be blank.")
    @Size(min = 8, max = 20, message = "Password must be 8-18 characters long.")
    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#$%^&*+=]).*$",
            message = """
                    Password must contain at least one digit,\s
                    one lowercase letter, one uppercase letter and one special character.""")
    private String password;


    @NotBlank(message = "This field can't be blank.")
    @JsonProperty("confirm_password")
    private String confirmPassword;

}
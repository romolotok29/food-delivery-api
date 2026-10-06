package io.github.romolotok29.deliveryplatform.account.dto;

import io.github.romolotok29.deliveryplatform.validation.NotBlankIfPresent;
import jakarta.validation.constraints.Email;

import jakarta.validation.constraints.Pattern;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class EditAccountProfileRequest {

    @NotBlankIfPresent
    private String fullName;

    @NotBlankIfPresent
    @Pattern(
            regexp = "^\\+?\\(?\\d{1,4}\\)?[\\s\\-]?\\(?\\d{1,4}\\)?[\\s\\-]?\\d{1,4}[\\s\\-]?\\d{1,4}[\\s\\-]?\\d{1,4}$",
            message = "Invalid phone number format.")
    private String phoneNumber;

    @NotBlankIfPresent
    @Email(message = "Invalid email address format.")
    private String emailAddress;

}
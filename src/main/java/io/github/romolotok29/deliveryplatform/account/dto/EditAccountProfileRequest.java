package io.github.romolotok29.deliveryplatform.account.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
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
    @JsonProperty("full_name")
    private String fullName;

    @NotBlankIfPresent
    @Pattern(
            regexp = "^\\+?\\(?\\d{1,4}\\)?[\\s\\-]?\\(?\\d{1,4}\\)?[\\s\\-]?\\d{1,4}[\\s\\-]?\\d{1,4}[\\s\\-]?\\d{1,4}$",
            message = "Invalid phone number format.")
    @JsonProperty("phone_number")
    private String phoneNumber;

    @NotBlankIfPresent
    @Email(message = "Invalid email address format.")
    @JsonProperty("email_address")
    private String emailAddress;

}
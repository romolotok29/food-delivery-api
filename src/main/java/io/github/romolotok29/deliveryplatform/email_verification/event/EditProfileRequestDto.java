package io.github.romolotok29.deliveryplatform.email_verification.event;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class EditProfileRequestDto {

    @NotBlank(message = "This field can't be blank.")
    @JsonProperty("first_name")
    private String firstName;

    @NotBlank(message = "This field can't be blank.")
    @JsonProperty("last_name")
    private String lastName;

    @NotBlank(message = "This field can't be blank.")
    @Pattern(
            regexp = "^\\+?\\(?\\d{1,4}\\)?[\\s\\-]?\\(?\\d{1,4}\\)?[\\s\\-]?\\d{1,4}[\\s\\-]?\\d{1,4}[\\s\\-]?\\d{1,4}$",
            message = "Invalid phone number format.")
    @JsonProperty("phone_number")
    private String phoneNumber;

    @NotBlank(message = "This field can't be blank.")
    @Email(message = "Invalid email address format.")
    @JsonProperty("email_address")
    private String email;

}
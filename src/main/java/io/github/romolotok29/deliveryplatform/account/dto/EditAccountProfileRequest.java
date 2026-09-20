package io.github.romolotok29.deliveryplatform.account.dto;

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
public class EditAccountProfileRequest {

    @NotBlank(message = "This field can't be blank.")
    @JsonProperty("first_name")
    private String fullName;

    @NotBlank(message = "This field can't be blank.")
    @Pattern(
            regexp = "^\\+?\\(?\\d{1,4}\\)?[\\s\\-]?\\(?\\d{1,4}\\)?[\\s\\-]?\\d{1,4}[\\s\\-]?\\d{1,4}[\\s\\-]?\\d{1,4}$",
            message = "Invalid phone number format.")
    @JsonProperty("phone_number")
    private String phoneNumber;

    @NotBlank(message = "This field can't be blank.")
    @Email(message = "Invalid email address format.")
    @JsonProperty("email_address")
    private String emailAddress;

}
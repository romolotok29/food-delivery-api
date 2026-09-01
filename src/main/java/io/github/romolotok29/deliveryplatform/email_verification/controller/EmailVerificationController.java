package io.github.romolotok29.deliveryplatform.email_verification.controller;

import io.github.romolotok29.deliveryplatform.email_verification.dto.ConfirmEmailAddressRequest;
import io.github.romolotok29.deliveryplatform.email_verification.dto.ResendConfirmationRequest;
import io.github.romolotok29.deliveryplatform.email_verification.service.EmailVerificationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class EmailVerificationController {

    private final EmailVerificationService emailVerificationService;

    @PostMapping("/confirm-email")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void confirmVerificationToken(@RequestBody ConfirmEmailAddressRequest request) {
        emailVerificationService.confirmEmailAddress(request.token());
    }

    @PostMapping("/resend-confirmation")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void resendVerificationToken(@Valid @RequestBody ResendConfirmationRequest request) {
        emailVerificationService.resendVerificationToken(request);
    }

}
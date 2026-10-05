package io.github.romolotok29.deliveryplatform.email_verification.controller;

import io.github.romolotok29.deliveryplatform.email_verification.dto.ConfirmEmailRequest;
import io.github.romolotok29.deliveryplatform.email_verification.dto.ResendVerificationRequest;
import io.github.romolotok29.deliveryplatform.email_verification.service.verification.EmailVerificationService;
import io.github.romolotok29.deliveryplatform.security.model.SecurityUser;
import io.github.romolotok29.deliveryplatform.security.service.SecurityLogoutService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/email")
@RequiredArgsConstructor
public class EmailVerificationController {

    private final EmailVerificationService emailVerificationService;
    private final SecurityLogoutService securityLogoutService;

    @PostMapping("/verify")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void verifyRegistrationEmail(@Valid @RequestBody ConfirmEmailRequest request) {
        emailVerificationService.verifyRegistrationEmail(request.token());
    }

    @PostMapping("/change/verify")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void verifyEmailChange(
            @AuthenticationPrincipal SecurityUser securityUser,
            @Valid @RequestBody ConfirmEmailRequest request,
            HttpServletRequest servletRequest,
            HttpServletResponse servletResponse,
            Authentication authentication
    ) {
        emailVerificationService.verifyEmailChange(request.token(), securityUser.getUserId());

        securityLogoutService.logout(servletRequest, servletResponse, authentication);
    }

    @PostMapping("/verification/resend")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void resendVerificationToken(@Valid @RequestBody ResendVerificationRequest request) {
        emailVerificationService.resendVerificationToken(request);
    }

}
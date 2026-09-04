package io.github.romolotok29.deliveryplatform.reset_password.controller;

import io.github.romolotok29.deliveryplatform.reset_password.dto.ForgotPasswordRequest;
import io.github.romolotok29.deliveryplatform.reset_password.service.ForgotPasswordService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth/forgot-password")
@RequiredArgsConstructor
public class ForgotPasswordController {

    private final ForgotPasswordService forgotPasswordService;

    @PostMapping
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void forgotPassword(@Valid @RequestBody ForgotPasswordRequest request) {
        forgotPasswordService.forgotPassword(request);
    }

}
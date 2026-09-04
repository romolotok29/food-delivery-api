package io.github.romolotok29.deliveryplatform.reset_password.controller;

import io.github.romolotok29.deliveryplatform.reset_password.dto.ResetPasswordRequest;
import io.github.romolotok29.deliveryplatform.reset_password.service.ResetPasswordService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth/reset-password")
@RequiredArgsConstructor
public class ResetPasswordController {

    private final ResetPasswordService resetPasswordService;

    @PatchMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void resetPassword(@Valid @RequestBody ResetPasswordRequest request) {
        resetPasswordService.resetPassword(request);
    }

}
package io.github.romolotok29.deliveryplatform.registration.controller;

import io.github.romolotok29.deliveryplatform.registration.dto.SignUpRequest;
import io.github.romolotok29.deliveryplatform.registration.dto.SignUpResponse;
import io.github.romolotok29.deliveryplatform.registration.service.ISignUpService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth/sign-up")
@RequiredArgsConstructor
public class SignUpController {

    private final ISignUpService signUpService;

    @PostMapping
    public ResponseEntity<SignUpResponse> signUp(@Valid @RequestBody SignUpRequest request) {

        signUpService.signUp(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new SignUpResponse(
                                "Please check your email and click the confirmation link to complete your registration."
                        )
                );
    }

}
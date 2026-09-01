package io.github.romolotok29.deliveryplatform.authentication.controller;

import io.github.romolotok29.deliveryplatform.authentication.dto.SignInRequest;
import io.github.romolotok29.deliveryplatform.authentication.dto.SignInResponse;
import io.github.romolotok29.deliveryplatform.authentication.service.ISignInService;
import io.github.romolotok29.deliveryplatform.security.model.SecurityUser;
import io.github.romolotok29.deliveryplatform.security.context.SecurityContextManager;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class SignInController {

    private final ISignInService signInService;
    private final SecurityContextManager securityContextManager;

    @PostMapping("/sign-in")
    public ResponseEntity<SignInResponse> signIn(
            @Valid @RequestBody SignInRequest request,
            HttpServletRequest httpServletRequest,
            HttpServletResponse httpServletResponse
    ) {

        Authentication authentication = signInService.signIn(request.emailAddress(), request.password());

        securityContextManager.authenticate(authentication, httpServletRequest, httpServletResponse);

        SecurityUser securityUser = (SecurityUser) authentication.getPrincipal();

        return ResponseEntity.status(HttpStatus.OK).body(
                new SignInResponse("Welcome, %s".formatted(securityUser.getFullName()))
        );
    }

}
package io.github.romolotok29.deliveryplatform.security.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/csrf")
public class CsrfTokenController {

    @GetMapping
    public ResponseEntity<Void> getCsrfToken(CsrfToken csrfToken) {

        return ResponseEntity.ok()
                .header(
                        csrfToken.getHeaderName(),
                        csrfToken.getToken()
                )
                .build();
    }

}
package io.github.romolotok29.deliveryplatform.account.controller;

import io.github.romolotok29.deliveryplatform.account.dto.AccountDto;
import io.github.romolotok29.deliveryplatform.account.service.AccountServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/account")
@RequiredArgsConstructor
public class AccountController {

    private AccountServiceImpl accountServiceImpl;

    @GetMapping //@RequestParam only for search, filter & pagination
    public ResponseEntity<Map<String, Object>> getCurrentAccountDetails() {

        AccountDto accountInfoResponseDto = accountServiceImpl.getCurrentAccountDetails();

        Map<String, Object> response = new LinkedHashMap<>();
        response.put("account", accountInfoResponseDto);
        response.put("message", "Information retrieved successfully.");
        response.put("action_date", LocalDateTime.now());

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

}

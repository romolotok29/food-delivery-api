package io.github.romolotok29.deliveryplatform.account.controller;

import io.github.romolotok29.deliveryplatform.account.dto.AccountDto;
import io.github.romolotok29.deliveryplatform.account.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/account")
@RequiredArgsConstructor
public class AccountController {

    private final AccountService accountService;

    @GetMapping("{id}") //@RequestParam only for search, filter & pagination
    public ResponseEntity<AccountDto> getCurrentAccountDetails(@PathVariable Long id) {

        AccountDto accountResponse = accountService.getCurrentAccountDetails(id);

        return ResponseEntity.status(HttpStatus.OK).body(accountResponse);
    }

}
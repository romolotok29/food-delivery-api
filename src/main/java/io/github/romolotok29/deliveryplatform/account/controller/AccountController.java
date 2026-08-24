package io.github.romolotok29.deliveryplatform.account.controller;

import io.github.romolotok29.deliveryplatform.account.UserMapper;
import io.github.romolotok29.deliveryplatform.account.dto.AccountResponse;
import io.github.romolotok29.deliveryplatform.account.entity.User;
import io.github.romolotok29.deliveryplatform.account.service.IAccountService;
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

    private final IAccountService accountService;
    private final UserMapper userMapper;

    @GetMapping("{id}") //@RequestParam only for search, filter & pagination
    public ResponseEntity<AccountResponse> getCurrentAccountDetails(@PathVariable Long id) {

        User currentUser = accountService.getCurrentAccountDetails(id);

        return ResponseEntity.status(HttpStatus.OK).body(userMapper.toAccountResponse(currentUser));
    }

}
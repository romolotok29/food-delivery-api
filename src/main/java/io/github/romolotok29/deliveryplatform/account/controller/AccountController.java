package io.github.romolotok29.deliveryplatform.account.controller;

import io.github.romolotok29.deliveryplatform.account.UserMapper;
import io.github.romolotok29.deliveryplatform.account.dto.AccountResponse;
import io.github.romolotok29.deliveryplatform.account.entity.User;
import io.github.romolotok29.deliveryplatform.account.service.IAccountService;
import io.github.romolotok29.deliveryplatform.security.model.SecurityUser;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/account")
@RequiredArgsConstructor
public class AccountController {

    private final IAccountService accountService;
    private final UserMapper userMapper;

    @GetMapping //@RequestParam only for search, filter & pagination
    public ResponseEntity<AccountResponse> getCurrentAccountDetails(@AuthenticationPrincipal SecurityUser securityUser) {

        User currentUser = accountService.getCurrentAccountDetails(securityUser.getUserId());

        return ResponseEntity.status(HttpStatus.OK).body(userMapper.toAccountResponse(currentUser));
    }

}
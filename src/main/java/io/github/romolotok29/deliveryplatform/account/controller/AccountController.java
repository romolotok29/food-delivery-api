package io.github.romolotok29.deliveryplatform.account.controller;

import io.github.romolotok29.deliveryplatform.account.dto.EditAccountProfileRequest;
import io.github.romolotok29.deliveryplatform.account.mapper.UserMapper;
import io.github.romolotok29.deliveryplatform.cache.AccountDetailsCache;
import io.github.romolotok29.deliveryplatform.account.dto.AccountDetailsResponse;
import io.github.romolotok29.deliveryplatform.account.service.IAccountService;
import io.github.romolotok29.deliveryplatform.security.model.SecurityUser;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/account")
@RequiredArgsConstructor
public class AccountController {

    private final IAccountService accountService;
    private final UserMapper userMapper;

    @GetMapping //@RequestParam only for search, filter & pagination
    public ResponseEntity<AccountDetailsResponse> getCurrentAccountDetails(@AuthenticationPrincipal SecurityUser securityUser) {

        AccountDetailsCache currentUser = accountService.getCurrentAccountDetails(securityUser.getUserId());

        return ResponseEntity.status(HttpStatus.OK).body(userMapper.toAccountResponse(currentUser));
    }

    @PatchMapping("/edit-profile")
    public ResponseEntity<AccountDetailsResponse> editAccountProfile(
            @AuthenticationPrincipal SecurityUser securityUser,
            @Valid EditAccountProfileRequest request
    ) {

        AccountDetailsCache currentUser = accountService.editProfile(securityUser.getUserId(), request);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(userMapper.toAccountResponse(currentUser));
    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteAccount(@AuthenticationPrincipal SecurityUser securityUser) {

        accountService.deleteAccount(securityUser.getUserId());
    }

}
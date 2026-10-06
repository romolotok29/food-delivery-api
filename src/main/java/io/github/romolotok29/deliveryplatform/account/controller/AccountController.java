package io.github.romolotok29.deliveryplatform.account.controller;

import io.github.romolotok29.deliveryplatform.account.dto.AccountDetailsResponse;
import io.github.romolotok29.deliveryplatform.account.dto.AccountDetailsResponseEnvelope;
import io.github.romolotok29.deliveryplatform.account.dto.EditAccountProfileRequest;
import io.github.romolotok29.deliveryplatform.account.mapper.UserMapper;
import io.github.romolotok29.deliveryplatform.cache.ProfileCache;
import io.github.romolotok29.deliveryplatform.security.service.SecurityLogoutService;
import io.github.romolotok29.deliveryplatform.account.service.IAccountService;
import io.github.romolotok29.deliveryplatform.security.model.SecurityUser;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/account")
@RequiredArgsConstructor
public class AccountController {

    private final IAccountService accountService;
    private final SecurityLogoutService securityLogoutService;
    private final UserMapper userMapper;

    @GetMapping //@RequestParam only for search, filter & pagination
    public ResponseEntity<AccountDetailsResponseEnvelope> getCurrentAccountDetails(@AuthenticationPrincipal SecurityUser securityUser) {

        ProfileCache currentUser = accountService.getCurrentAccountDetails(securityUser.getUserId());

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new AccountDetailsResponseEnvelope(
                                userMapper.toAccountResponse(currentUser)
                        )
                );
    }

    @PatchMapping("/edit-profile")
    public ResponseEntity<AccountDetailsResponse> editAccountProfile(
            @AuthenticationPrincipal SecurityUser securityUser,
            @Valid @RequestBody EditAccountProfileRequest request
    ) {

        ProfileCache currentUser = accountService.editProfile(securityUser.getUserId(), request);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new AccountDetailsResponse(
                                userMapper.toProfileResponse(currentUser)
                        )
                );
    }

    @DeleteMapping("/delete")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteAccount(
            @AuthenticationPrincipal SecurityUser securityUser,
            HttpServletRequest request,
            HttpServletResponse response,
            Authentication authentication
    ) {

        accountService.deleteAccount(securityUser.getUserId());
        securityLogoutService.logout(request, response, authentication);
    }

}
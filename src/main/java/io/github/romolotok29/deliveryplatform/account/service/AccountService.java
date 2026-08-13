package io.github.romolotok29.deliveryplatform.account.service;

import io.github.romolotok29.deliveryplatform.account.dto.AccountDto;

public interface AccountService {

    AccountDto getCurrentAccountDetails(Long userId);
}

package io.github.romolotok29.deliveryplatform.account.service;

import io.github.romolotok29.deliveryplatform.account.dto.AccountDto;

public interface IAccountService {

    AccountDto getCurrentAccountDetails(Long userId);

}
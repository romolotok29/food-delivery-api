package io.github.romolotok29.deliveryplatform.account.service;

import io.github.romolotok29.deliveryplatform.cache.AccountDetailsCache;

public interface IAccountService {

    AccountDetailsCache getCurrentAccountDetails(Long userId);

}
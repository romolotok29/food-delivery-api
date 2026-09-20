package io.github.romolotok29.deliveryplatform.account.service;

import io.github.romolotok29.deliveryplatform.account.dto.EditAccountProfileRequest;
import io.github.romolotok29.deliveryplatform.cache.AccountDetailsCache;

public interface IAccountService {

    AccountDetailsCache getCurrentAccountDetails(Long userId);
    AccountDetailsCache editProfile(Long userId, EditAccountProfileRequest request);
    void deleteAccount(Long userId);

}
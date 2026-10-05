package io.github.romolotok29.deliveryplatform.account.service;

import io.github.romolotok29.deliveryplatform.account.dto.EditAccountProfileRequest;
import io.github.romolotok29.deliveryplatform.cache.ProfileCache;

public interface IAccountService {

    ProfileCache getCurrentAccountDetails(Long userId);
    ProfileCache editProfile(Long userId, EditAccountProfileRequest request);
    void deleteAccount(Long userId);

}
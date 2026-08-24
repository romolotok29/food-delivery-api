package io.github.romolotok29.deliveryplatform.account.service;

import io.github.romolotok29.deliveryplatform.account.entity.User;

public interface IAccountService {

    User getCurrentAccountDetails(Long userId);

}
package io.github.romolotok29.deliveryplatform.registration.service;

import io.github.romolotok29.deliveryplatform.account.entity.User;
import io.github.romolotok29.deliveryplatform.registration.dto.SignUpRequest;

public interface ISignUpService {

    User signUp(SignUpRequest request);

}
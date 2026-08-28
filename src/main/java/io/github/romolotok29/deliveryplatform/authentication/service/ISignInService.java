package io.github.romolotok29.deliveryplatform.authentication.service;

import org.springframework.security.core.Authentication;

public interface ISignInService {

    Authentication signIn(String email, String password);

}
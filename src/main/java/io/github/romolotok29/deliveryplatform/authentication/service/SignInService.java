package io.github.romolotok29.deliveryplatform.authentication.service;

import io.github.romolotok29.deliveryplatform.exceptions.InvalidCredentialsException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SignInService implements ISignInService {

    private final AuthenticationManager authenticationManager;

    @Override
    public Authentication signIn(String emailAddress, String password) {

        var authenticationToken = UsernamePasswordAuthenticationToken.unauthenticated(emailAddress, password);

        try {
            return authenticationManager.authenticate(authenticationToken);
        } catch (AuthenticationException ex) {
            throw new InvalidCredentialsException();
        }
    }

//    @Override
//    public Authentication signIn(String emailAddress, String password) {
//
//        try {
//            UsernamePasswordAuthenticationToken token =
//                    new UsernamePasswordAuthenticationToken(emailAddress, password);
//
//            return authenticationManager.authenticate(token);
//
//        } catch (AuthenticationException ex) {
//            throw new InvalidCredentialsException();
//        }
//    }

}
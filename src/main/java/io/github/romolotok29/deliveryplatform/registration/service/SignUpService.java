package io.github.romolotok29.deliveryplatform.registration.service;

import io.github.romolotok29.deliveryplatform.account.UserMapper;
import io.github.romolotok29.deliveryplatform.account.entity.Role;
import io.github.romolotok29.deliveryplatform.account.entity.User;
import io.github.romolotok29.deliveryplatform.account.repository.UserRepository;
import io.github.romolotok29.deliveryplatform.email_verification.entity.EmailVerificationToken;
import io.github.romolotok29.deliveryplatform.email_verification.event.UserRegisteredEvent;
import io.github.romolotok29.deliveryplatform.email_verification.repository.EmailVerificationTokenRepository;
import io.github.romolotok29.deliveryplatform.email_verification.service.EmailVerificationTokenService;
import io.github.romolotok29.deliveryplatform.exceptions.registration.UserAlreadyExistsException;
import io.github.romolotok29.deliveryplatform.registration.dto.SignUpRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class SignUpService implements ISignUpService {

    private final UserRepository userRepository;

    private final UserMapper userMapper;

    private final PasswordEncoder passwordEncoder;

    private final EmailVerificationTokenService tokenService;

    private final EmailVerificationTokenRepository tokenRepository;

    private final ApplicationEventPublisher applicationEventPublisher;

    @Override
    @Transactional
    public User signUp(SignUpRequest request) {

        checkIfUserExists(request.getPhoneNumber(), request.getEmailAddress());

        User user = createNewUser(request);

        String rawToken = tokenService.generateToken();

        EmailVerificationToken token = tokenService.createToken(user, rawToken);

        userRepository.save(user);
        tokenRepository.save(token);

        applicationEventPublisher.publishEvent(
                new UserRegisteredEvent(
                        user.getEmailAddress(),
                        rawToken
                )
        );

        return user;
    }

    private void checkIfUserExists(String phoneNumber, String emailAddress) {
        if (userRepository.existsByPhoneNumberOrEmailAddress(
                phoneNumber,
                emailAddress
        )) {
            throw new UserAlreadyExistsException();
        }
    }

    private User createNewUser(SignUpRequest request) {

        User user = userMapper.toEntity(request);

        user.setRole(Role.USER);
        user.setEmailVerified(false);
        encodePassword(user);

        return user;
    }

    private void encodePassword(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
    }

}
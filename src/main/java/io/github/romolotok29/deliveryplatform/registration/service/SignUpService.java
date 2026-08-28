package io.github.romolotok29.deliveryplatform.registration.service;

import io.github.romolotok29.deliveryplatform.account.UserMapper;
import io.github.romolotok29.deliveryplatform.account.entity.Role;
import io.github.romolotok29.deliveryplatform.account.entity.User;
import io.github.romolotok29.deliveryplatform.account.repository.UserRepository;
import io.github.romolotok29.deliveryplatform.exceptions.UserAlreadyExistsException;
import io.github.romolotok29.deliveryplatform.registration.dto.SignUpRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class SignUpService implements ISignUpService {

    private final UserRepository userRepository;

    private final UserMapper userMapper;

    private final PasswordEncoder passwordEncoder;

    @Transactional
    @Override
    public User signUp(SignUpRequest request) {

        checkIfUserExists(request.getPhoneNumber(), request.getEmailAddress());

        User user = createNewUser(request);

        return userRepository.save(user);
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
        encodePassword(user);
        return user;
    }

    private void encodePassword(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
    }

}
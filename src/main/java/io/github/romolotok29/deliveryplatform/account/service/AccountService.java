package io.github.romolotok29.deliveryplatform.account.service;

import io.github.romolotok29.deliveryplatform.account.entity.User;
import io.github.romolotok29.deliveryplatform.account.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AccountService implements IAccountService {

    private final UserRepository userRepository;

    @Override
    public User getCurrentAccountDetails(Long userId) {

        return userRepository.findById(userId).orElseThrow(
                () -> new RuntimeException("User with id [%s] not found".formatted(userId)));

    }

}
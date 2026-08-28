package io.github.romolotok29.deliveryplatform.account.repository;

import io.github.romolotok29.deliveryplatform.account.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findUserByEmailAddress(String emailAddress);

    boolean existsByPhoneNumberOrEmailAddress(String phoneNumber, String emailAddress);

}
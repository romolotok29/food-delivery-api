package io.github.romolotok29.deliveryplatform.account.repository;

import io.github.romolotok29.deliveryplatform.account.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

    boolean existsByPhoneNumberOrEmailAddress(String phoneNumber, String emailAddress);

}
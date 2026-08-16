package io.github.romolotok29.deliveryplatform.account.repository;

import io.github.romolotok29.deliveryplatform.account.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    @Query(
            value = "SELECT u FROM User u WHERE u.username = :username"
    )
    Optional<User> findUserByUsername(String username);

    @Query(
            value = "SELECT u FROM User u WHERE u.phone_number = :phone_number"
    )
    Optional<User> findUserByPhoneNumber(@Param("phone_number") String phoneNumber);

    @Query("""
                SELECT u
                FROM User u
                WHERE u.email = :email
                AND u.enabled = true
            """)
    Optional<User> findActiveUserByEmail(@Param("email_address") String email);

}
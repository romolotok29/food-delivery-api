package io.github.romolotok29.deliveryplatform.util;

import io.github.romolotok29.deliveryplatform.account.entity.Role;
import io.github.romolotok29.deliveryplatform.account.entity.User;
import net.datafaker.Faker;

public final class TestDataFactory {

    public static final Faker FAKER = new Faker();

    public static User createValidUserEntity() {

        return User.builder()
                .fullName(FAKER.name().fullName())
                .emailAddress(FAKER.internet().safeEmailAddress())
                .password(FAKER.credentials().password())
                .phoneNumber(FAKER.phoneNumber().phoneNumberNational())
                .role(Role.USER)
                .build();
    }

}

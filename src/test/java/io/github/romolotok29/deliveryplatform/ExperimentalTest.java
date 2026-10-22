package io.github.romolotok29.deliveryplatform;

import io.github.romolotok29.deliveryplatform.account.entity.User;
import io.github.romolotok29.deliveryplatform.base.AbstractDataJpaTest;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static io.github.romolotok29.deliveryplatform.testcontainers.PostgresContainerConfig.postgresContainer;
import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ExperimentalTest extends AbstractDataJpaTest {

    @Test
    public void test() {
        assertThat(postgresContainer.isCreated()).isTrue();
        assertThat(postgresContainer.isRunning()).isTrue();
        assertTrue(postgresContainer.isHealthy());
    }

    @Test
    public void test2() {

        User user = User.builder()
                .fullName("")
                .build();

        em.persist(user);
        em.flush();

        Optional<User> foundUser = Optional.ofNullable(em.find(User.class, user.getId()));

        assertTrue(foundUser.isPresent());
        assertThat(foundUser.get().getId()).isEqualTo(user.getId());
        assertThat(foundUser.get().getFullName()).isNotNull();
    }

}
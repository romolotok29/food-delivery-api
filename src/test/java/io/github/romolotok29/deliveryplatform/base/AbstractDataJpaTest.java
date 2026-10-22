package io.github.romolotok29.deliveryplatform.base;

import io.github.romolotok29.deliveryplatform.account.repository.UserRepository;
import io.github.romolotok29.deliveryplatform.email_verification.repository.EmailVerificationTokenRepository;
import io.github.romolotok29.deliveryplatform.testcontainers.PostgresContainerConfig;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.jdbc.test.autoconfigure.AutoConfigureTestDatabase;
import org.springframework.boot.jpa.test.autoconfigure.TestEntityManager;
import org.springframework.boot.testcontainers.context.ImportTestcontainers;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlMergeMode;

import static org.springframework.boot.jdbc.test.autoconfigure.AutoConfigureTestDatabase.Replace.NONE;

@Tag("slice")
@ActiveProfiles("test")
@DataJpaTest
@AutoConfigureTestDatabase(replace = NONE)
@ImportTestcontainers(PostgresContainerConfig.class)
@SqlMergeMode(value = SqlMergeMode.MergeMode.MERGE)
@Sql()
public abstract class AbstractDataJpaTest {

    @Autowired
    protected TestEntityManager em;

    @Autowired
    protected UserRepository userRepository;

    @Autowired
    protected EmailVerificationTokenRepository tokenRepository;

    @BeforeEach
    public void setup() {

    }

}

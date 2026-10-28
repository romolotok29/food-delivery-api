package io.github.romolotok29.deliveryplatform.base;

import io.github.romolotok29.deliveryplatform.testcontainers.PostgresTestContainer;
import io.github.romolotok29.deliveryplatform.testcontainers.RedisTestContainer;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.resttestclient.autoconfigure.AutoConfigureRestTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.testcontainers.context.ImportTestcontainers;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.client.RestTestClient;

import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@Tag("flow")
@ActiveProfiles("test")
@SpringBootTest(webEnvironment = RANDOM_PORT)
@AutoConfigureRestTestClient
@ImportTestcontainers({PostgresTestContainer.class, RedisTestContainer.class})
public abstract class AbstractFlowTest {

    @Autowired
    protected RestTestClient client;

    @Autowired
    protected JdbcTemplate jdbc;

    @Autowired
    protected StringRedisTemplate redis;

    @BeforeAll
    static void beforeAll() {

    }

    @BeforeEach
    void beforeEach() {
        jdbc.execute("TRUNCATE TABLE email_verification_tokens, users RESTART IDENTITY CASCADE");
        redis.execute((RedisCallback<Void>) c -> {c.serverCommands().flushAll(); return null;});
    }

}

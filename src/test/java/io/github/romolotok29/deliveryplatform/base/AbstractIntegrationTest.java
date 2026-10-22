package io.github.romolotok29.deliveryplatform.base;

import io.github.romolotok29.deliveryplatform.testcontainers.RedisContainerConfig;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.testcontainers.context.ImportTestcontainers;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.assertj.MockMvcTester;

@Tag("integration")
@ActiveProfiles("test")
@SpringBootTest
@AutoConfigureMockMvc
@ImportTestcontainers(RedisContainerConfig.class)
public abstract class AbstractIntegrationTest {

    @Autowired
    protected JdbcTemplate jdbc;

    @Autowired
    protected StringRedisTemplate redis;

    @Autowired
    protected MockMvcTester testMvc;

    @BeforeEach
    public void cleanup() {
        jdbc.execute("TRUNCATE TABLE email_verification_tokens, users RESTART IDENTITY CASCADE");
        redis.execute((RedisCallback<Void>)  c -> {c.serverCommands().flushAll(); return null;});
    }

}

package io.github.romolotok29.deliveryplatform.testcontainers;

import com.redis.testcontainers.RedisContainer;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.testcontainers.utility.DockerImageName;

public interface RedisTestContainer {

    @ServiceConnection(name = "redis")
    RedisContainer redisContainer = new RedisContainer(DockerImageName.parse("redis:7.4-alpine"));

}

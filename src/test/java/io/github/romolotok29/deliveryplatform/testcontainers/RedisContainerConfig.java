package io.github.romolotok29.deliveryplatform.testcontainers;

import com.redis.testcontainers.RedisContainer;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.testcontainers.utility.DockerImageName;

import static io.github.romolotok29.deliveryplatform.testcontainers.PostgresContainerConfig.postgresContainer;

public interface RedisContainerConfig {

    @ServiceConnection(name = "redis")
    RedisContainer redisContainer = new RedisContainer(DockerImageName.parse("redis:7-alpine"))
                .withExposedPorts(6379)
                .dependsOn(postgresContainer);

}

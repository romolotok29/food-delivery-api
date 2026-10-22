package io.github.romolotok29.deliveryplatform.testcontainers;

import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.testcontainers.postgresql.PostgreSQLContainer;
import org.testcontainers.utility.DockerImageName;

public interface PostgresContainerConfig {

    @ServiceConnection
    PostgreSQLContainer postgresContainer =
            new PostgreSQLContainer(DockerImageName.parse("postgres:16-alpine"));

}

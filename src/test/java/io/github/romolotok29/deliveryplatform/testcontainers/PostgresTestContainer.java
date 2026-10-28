package io.github.romolotok29.deliveryplatform.testcontainers;

import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.testcontainers.postgresql.PostgreSQLContainer;
import org.testcontainers.utility.DockerImageName;

import java.util.Map;

public interface PostgresTestContainer {

    @ServiceConnection
    PostgreSQLContainer postgresContainer =
            new PostgreSQLContainer(DockerImageName.parse("postgres:16.3-alpine"))
                    .withDatabaseName("food_delivery_test")
                    .withUsername("test")
                    .withPassword("test")
                    .withTmpFs(Map.of("/var/lib/postgresql/data", "rw"));

}

package io.github.romolotok29.deliveryplatform;

import io.github.romolotok29.deliveryplatform.base.AbstractDataJpaTest;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

public class DatabaseSmokeTest extends AbstractDataJpaTest {

    @Test
    public void shouldUseRealPostgresTest() {

        Object version = em.getEntityManager()
                .createNativeQuery("select version()")
                .getSingleResult();

        assertThat(version.toString()).contains("PostgreSQL");
    }

}
package io.github.romolotok29.deliveryplatform.app_configs;

import io.github.romolotok29.deliveryplatform.account.mapper.RedisJsonMapper;
import io.github.romolotok29.deliveryplatform.security.model.SecurityUser;
import io.github.romolotok29.deliveryplatform.security.model.SecurityUserMixin;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.serializer.GenericJacksonJsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.security.jackson.SecurityJacksonModules;
import tools.jackson.databind.jsontype.BasicPolymorphicTypeValidator;

@Configuration
public class RedisSessionConfig {

    @Bean("springSessionDefaultRedisSerializer")
    public RedisSerializer<Object> redisSerializer() {

        BasicPolymorphicTypeValidator.Builder polymorphicTypeValidator = BasicPolymorphicTypeValidator
                        .builder()
                        .allowIfSubType("io.github.romolotok29.deliveryplatform.");

        ClassLoader classLoader = getClass().getClassLoader();

        return GenericJacksonJsonRedisSerializer
                .builder(RedisJsonMapper::newBuilder)
                .customize(builder -> builder
                        .addModules(SecurityJacksonModules.getModules(classLoader, polymorphicTypeValidator))
                        .addMixIn(SecurityUser.class, SecurityUserMixin.class)
                )
                .build();
    }

}
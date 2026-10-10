package io.github.romolotok29.deliveryplatform.app_configs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.serializer.GenericJacksonJsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;
import tools.jackson.databind.json.JsonMapper;
import tools.jackson.databind.jsontype.BasicPolymorphicTypeValidator;

@Configuration
public class RedisSessionConfig {

    @Bean("springSessionDefaultRedisSerializer")
    public RedisSerializer<Object> redisSerializer(JsonMapper jsonMapper) {

        BasicPolymorphicTypeValidator polymorphicTypeValidator =
                BasicPolymorphicTypeValidator
                        .builder()
                        .allowIfSubType("io.github.romolotok29.deliveryplatform.")
                        .allowIfSubType("org.springframework.security.")
                        .build();

        return GenericJacksonJsonRedisSerializer
                .builder(jsonMapper::rebuild)
                .enableDefaultTyping(polymorphicTypeValidator)
                .build();
    }

}
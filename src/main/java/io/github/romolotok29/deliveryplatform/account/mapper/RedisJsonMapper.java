package io.github.romolotok29.deliveryplatform.account.mapper;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import tools.jackson.databind.DeserializationFeature;
import tools.jackson.databind.cfg.DateTimeFeature;
import tools.jackson.databind.json.JsonMapper;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class RedisJsonMapper {

    public static JsonMapper.Builder newBuilder() {
        return JsonMapper.builder()
                .disable(DateTimeFeature.WRITE_DATES_AS_TIMESTAMPS)
                .enable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
    }

}
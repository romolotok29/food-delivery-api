package io.github.romolotok29.deliveryplatform.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class JsonPropertyUtils {

    public static String toSnakeCase(String value) {
        return value
                .replaceAll("([a-z])([A-Z]+)", "$1_$2")
                .toLowerCase();
    }

}
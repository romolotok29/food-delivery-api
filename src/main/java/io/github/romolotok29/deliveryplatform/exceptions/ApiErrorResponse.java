package io.github.romolotok29.deliveryplatform.exceptions;

import java.time.Instant;

public record ApiErrorResponse(
        String code,
        String message,
        String path,
        Instant timestamp
) {

}
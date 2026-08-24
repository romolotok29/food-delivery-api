package io.github.romolotok29.deliveryplatform.exceptions;

import java.time.LocalDateTime;

public record ApiErrorResponse(
        String code,
        String message,
        String path,
        LocalDateTime timestamp
) {}
package io.github.romolotok29.deliveryplatform.account.dto;

import tools.jackson.databind.PropertyNamingStrategies;
import tools.jackson.databind.annotation.JsonNaming;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record AccountDetailsResponse(String fullName, String phoneNumber, String emailAddress) {}
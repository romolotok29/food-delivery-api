package io.github.romolotok29.deliveryplatform.account.dto;

import com.fasterxml.jackson.annotation.JsonRootName;
import tools.jackson.databind.PropertyNamingStrategies;
import tools.jackson.databind.annotation.JsonNaming;

//@JsonRootName("account")
@JsonNaming(value = PropertyNamingStrategies.SnakeCaseStrategy.class)
public record AccountResponse(String fullName, String phoneNumber, String emailAddress) {}
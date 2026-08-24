package io.github.romolotok29.deliveryplatform.account;

import io.github.romolotok29.deliveryplatform.account.dto.AccountResponse;
import io.github.romolotok29.deliveryplatform.account.entity.User;
import io.github.romolotok29.deliveryplatform.registration.dto.SignUpRequest;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {

    AccountResponse toAccountResponse(User user);

    User toEntity(SignUpRequest signUpRequestDto);

}
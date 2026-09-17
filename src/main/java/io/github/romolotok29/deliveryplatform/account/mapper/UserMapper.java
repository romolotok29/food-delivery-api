package io.github.romolotok29.deliveryplatform.account.mapper;

import io.github.romolotok29.deliveryplatform.account.dto.AccountDetailsResponse;
import io.github.romolotok29.deliveryplatform.cache.AccountDetailsCache;
import io.github.romolotok29.deliveryplatform.account.entity.User;
import io.github.romolotok29.deliveryplatform.registration.dto.SignUpRequest;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {

    AccountDetailsResponse toAccountResponse(AccountDetailsCache user);

    User toEntity(SignUpRequest signUpRequestDto);

}
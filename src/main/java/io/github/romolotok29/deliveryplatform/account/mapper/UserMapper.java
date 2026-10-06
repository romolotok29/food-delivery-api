package io.github.romolotok29.deliveryplatform.account.mapper;

import io.github.romolotok29.deliveryplatform.account.dto.AccountDetailsResponse;
import io.github.romolotok29.deliveryplatform.account.dto.ProfileResponse;
import io.github.romolotok29.deliveryplatform.cache.ProfileCache;
import io.github.romolotok29.deliveryplatform.account.entity.User;
import io.github.romolotok29.deliveryplatform.registration.dto.SignUpRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(target = "profile", source = ".")
    AccountDetailsResponse toAccountResponse(ProfileCache user);

    ProfileResponse toProfileResponse(ProfileCache user);

    User toEntity(SignUpRequest signUpRequestDto);

}
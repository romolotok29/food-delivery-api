package io.github.romolotok29.deliveryplatform.account;

import io.github.romolotok29.deliveryplatform.account.dto.AccountDto;
import io.github.romolotok29.deliveryplatform.account.entity.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {

    AccountDto toDto(User user);

    User toEntity(AccountDto accountDto);

}

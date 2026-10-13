package io.github.romolotok29.deliveryplatform.security.model;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import org.springframework.security.core.GrantedAuthority;

import java.time.Instant;
import java.util.Collection;

@JsonTypeInfo(use = JsonTypeInfo.Id.CLASS, property = "@class")
@JsonAutoDetect(
        fieldVisibility = JsonAutoDetect.Visibility.ANY,
        getterVisibility = JsonAutoDetect.Visibility.NONE,
        isGetterVisibility = JsonAutoDetect.Visibility.NONE,
        setterVisibility = JsonAutoDetect.Visibility.NONE,
        creatorVisibility = JsonAutoDetect.Visibility.ANY
)
public abstract class SecurityUserMixin {

    @JsonCreator
    SecurityUserMixin(
            @JsonProperty("userId") Long userId,
            @JsonProperty("fullName") String fullName,
            @JsonProperty("emailAddress") String emailAddress,
            @JsonProperty("password") String password,
            @JsonProperty("isEmailVerified") boolean isEmailVerified,
            @JsonProperty("accountLockedUntil") Instant accountLockedUntil,
            @JsonProperty("authorities") Collection<? extends GrantedAuthority> authorities
    ) {
    }

}
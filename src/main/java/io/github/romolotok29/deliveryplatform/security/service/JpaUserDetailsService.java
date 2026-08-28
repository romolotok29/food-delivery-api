package io.github.romolotok29.deliveryplatform.security.service;

import io.github.romolotok29.deliveryplatform.account.repository.UserRepository;
import io.github.romolotok29.deliveryplatform.security.model.SecurityUser;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.NullMarked;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@NullMarked
public class JpaUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String emailAddress) throws UsernameNotFoundException {
        return userRepository.findUserByEmailAddress(emailAddress)
                .map(SecurityUser::new)
                .orElseThrow(() -> new UsernameNotFoundException("User with the provided email wasn't found."));
    }

}
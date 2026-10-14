package io.github.romolotok29.deliveryplatform.app_configs;

import io.github.romolotok29.deliveryplatform.security.provider.LockoutAuthenticationProvider;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.HttpStatusAccessDeniedHandler;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.authentication.session.*;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.security.web.csrf.*;
import org.springframework.security.web.session.HttpSessionEventPublisher;

import java.util.List;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(
            HttpSecurity httpSecurity,
            CsrfTokenRepository csrfTokenRepository,
            SessionAuthenticationStrategy sessionAuthenticationStrategy,
            AuthenticationManager authenticationManager
    ) {

        return httpSecurity
                .csrf(csrf -> csrf
                        .csrfTokenRepository(csrfTokenRepository)
                        .ignoringRequestMatchers(
                                "/api/v1/auth/sign-up",
                                "/api/v1/email/verify",
                                "/api/v1/email/verification/resend",
                                "/api/v1/auth/forgot-password",
                                "/api/v1/auth/reset-password"
                        )
                )
                .exceptionHandling(ex -> ex
                        .authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED))
                        .accessDeniedHandler(new HttpStatusAccessDeniedHandler(HttpStatus.FORBIDDEN))
                )
                .logout(logout -> logout
                        .logoutUrl("/api/v1/auth/sign-out")
                        .invalidateHttpSession(true)
                        .clearAuthentication(true)
                        .deleteCookies("SESSION")
                        .logoutSuccessHandler(
                                (request, response, authentication) ->
                                        response.setStatus(HttpServletResponse.SC_NO_CONTENT)
                        )
                )
                .httpBasic(AbstractHttpConfigurer::disable)
                .sessionManagement(
                        session -> session
                                .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
                                .sessionAuthenticationStrategy(sessionAuthenticationStrategy)
                )
                .authenticationManager(authenticationManager)
                .authorizeHttpRequests(request -> request
                        .requestMatchers("/error").permitAll()
                        .requestMatchers("/api/v1/auth/sign-up").permitAll()
                        .requestMatchers("/api/v1/email/verify").permitAll()
                        .requestMatchers("/api/v1/email/change/verify").permitAll()
                        .requestMatchers("/api/v1/email/verification/resend").permitAll()
                        .requestMatchers("/api/v1/auth/forgot-password").permitAll()
                        .requestMatchers("/api/v1/auth/reset-password").permitAll()
                        .requestMatchers("/api/v1/auth/sign-in").permitAll()
                        .requestMatchers("/api/v1/csrf").permitAll()
                        .anyRequest().authenticated()
                )
                .build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public CsrfTokenRepository csrfTokenRepository() {
        return new HttpSessionCsrfTokenRepository();
    }

    @Bean
        public SessionAuthenticationStrategy sessionAuthenticationStrategy(
            CsrfTokenRepository csrfTokenRepository,
            SessionRegistry sessionRegistry
    ) {

        var concurrentSessionControl =
                new ConcurrentSessionControlAuthenticationStrategy(sessionRegistry);

        concurrentSessionControl.setMaximumSessions(1);
        concurrentSessionControl.setExceptionIfMaximumExceeded(true);

        return new CompositeSessionAuthenticationStrategy(
                List.of(
                        concurrentSessionControl,
                        new ChangeSessionIdAuthenticationStrategy(),
                        new CsrfAuthenticationStrategy(csrfTokenRepository),
                        new RegisterSessionAuthenticationStrategy(sessionRegistry)
                )
        );
    }

    @Bean
    public SessionRegistry sessionRegistry() {
        return new SessionRegistryImpl();
    }

    @Bean
    public HttpSessionEventPublisher httpSessionEventPublisher() {
        return new HttpSessionEventPublisher();
    }

    @Bean
    public SecurityContextRepository securityContextRepository() {
        return new HttpSessionSecurityContextRepository();
    }

    @Bean
    public AuthenticationManager authenticationManager(
            LockoutAuthenticationProvider authenticationProvider
    ) {

        return new ProviderManager(authenticationProvider);
    }

}
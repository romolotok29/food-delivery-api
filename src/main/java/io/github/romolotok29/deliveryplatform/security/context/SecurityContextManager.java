package io.github.romolotok29.deliveryplatform.security.context;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.session.SessionAuthenticationStrategy;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SecurityContextManager {

    private final SecurityContextRepository repository;
    private final SessionAuthenticationStrategy sessionAuthenticationStrategy;

    public void authenticate(
            Authentication authentication,
            HttpServletRequest request,
            HttpServletResponse response
    ) {

        sessionAuthenticationStrategy.onAuthentication(authentication, request, response);

        SecurityContext context = SecurityContextHolder.createEmptyContext();
        context.setAuthentication(authentication);
        SecurityContextHolder.setContext(context);

        repository.saveContext(context, request, response);
    }

}
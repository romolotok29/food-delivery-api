package io.github.romolotok29.deliveryplatform.security.service;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.CompositeLogoutHandler;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.security.web.csrf.CsrfLogoutHandler;
import org.springframework.security.web.csrf.CsrfTokenRepository;
import org.springframework.stereotype.Component;

@Component
public class SecurityLogoutService {

    private final LogoutHandler logoutHandler;

    public SecurityLogoutService(
            CsrfTokenRepository csrfTokenRepository
    ) {
        this.logoutHandler = new CompositeLogoutHandler(
                new CsrfLogoutHandler(csrfTokenRepository),
                new SecurityContextLogoutHandler()
        );
    }

    public void logout(
            HttpServletRequest request,
            HttpServletResponse response,
            Authentication authentication
    ) {

        logoutHandler.logout(request, response, authentication);
    }

}
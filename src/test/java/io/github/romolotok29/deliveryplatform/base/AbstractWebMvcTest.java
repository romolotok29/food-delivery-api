package io.github.romolotok29.deliveryplatform.base;

import io.github.romolotok29.deliveryplatform.account.service.AccountService;
import io.github.romolotok29.deliveryplatform.app_configs.SecurityConfig;
import io.github.romolotok29.deliveryplatform.authentication.service.SignInService;
import io.github.romolotok29.deliveryplatform.email_verification.service.EmailNotificationService;
import io.github.romolotok29.deliveryplatform.email_verification.service.verification.EmailVerificationService;
import io.github.romolotok29.deliveryplatform.email_verification.service.verification.EmailVerificationTokenService;
import io.github.romolotok29.deliveryplatform.registration.service.SignUpService;
import io.github.romolotok29.deliveryplatform.security.service.JpaUserDetailsService;
import io.github.romolotok29.deliveryplatform.security.service.SecurityLogoutService;
import org.junit.jupiter.api.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.assertj.MockMvcTester;

@Tag("slice")
@WebMvcTest
@Import({SecurityConfig.class})
public class AbstractWebMvcTest {

    @Autowired protected MockMvcTester mockMvcTester;

    @MockitoBean protected AccountService accountService;

    @MockitoBean protected SignUpService signUpService;

    @MockitoBean protected SignInService signInService;

    @MockitoBean protected EmailVerificationService emailVerificationService;

    @MockitoBean protected EmailVerificationTokenService emailVerificationTokenService;

    @MockitoBean protected EmailNotificationService emailNotificationService;

    @MockitoBean protected JpaUserDetailsService jpaUserDetailsService;

    @MockitoBean protected SecurityLogoutService securityLogoutService;

}
package io.github.romolotok29.deliveryplatform.base;

import io.github.romolotok29.deliveryplatform.account.service.AccountService;
import io.github.romolotok29.deliveryplatform.app_configs.SecurityConfig;
import io.github.romolotok29.deliveryplatform.authentication.service.SignInService;
import io.github.romolotok29.deliveryplatform.registration.service.SignUpService;
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

    @Autowired
    private MockMvcTester mockMvcTester;

    @MockitoBean
    private SignUpService signUpService;

    @MockitoBean
    private AccountService accountService;

    @MockitoBean
    private SignInService signInService;

}
package io.github.romolotok29.deliveryplatform.email_verification.event_listener;

import io.github.romolotok29.deliveryplatform.email_verification.event.RegistrationEmailVerifiedEvent;
import io.github.romolotok29.deliveryplatform.email_verification.service.EmailNotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
@RequiredArgsConstructor
public class RegistrationEmailVerifiedEventListener {

    private final EmailNotificationService emailNotificationService;

    @Async
    @TransactionalEventListener
    public void handleRegistrationEmailVerifiedEvent(RegistrationEmailVerifiedEvent event) {

        emailNotificationService.sendRegistrationEmailVerifiedEmail(
                event.emailAddress()
        );
    }

}

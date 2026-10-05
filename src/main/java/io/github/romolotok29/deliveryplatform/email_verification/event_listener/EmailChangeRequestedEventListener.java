package io.github.romolotok29.deliveryplatform.email_verification.event_listener;

import io.github.romolotok29.deliveryplatform.email_verification.event.EmailChangeRequestedEvent;
import io.github.romolotok29.deliveryplatform.email_verification.service.EmailNotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
@RequiredArgsConstructor
public class EmailChangeRequestedEventListener {

    private final EmailNotificationService emailNotificationService;

    @Async
    @TransactionalEventListener
    public void handleEmailChangeRequestedEvent(EmailChangeRequestedEvent event) {

        emailNotificationService.sendEmailChangeRequestedVerificationEmail(
                event.emailAddress(),
                event.verificationToken()
        );
    }

}
package io.github.romolotok29.deliveryplatform.email_verification.event;

import io.github.romolotok29.deliveryplatform.email_verification.service.EmailNotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
@RequiredArgsConstructor
public class EmailChangedEventListener {

    private final EmailNotificationService emailNotificationService;

    @Async
    @TransactionalEventListener
    public void handleEmailChangedEvent(EmailChangedEvent event) {

        emailNotificationService.sendEmailAddressResetVerificationEmail(event.emailAddress(), event.verificationToken());
    }

}

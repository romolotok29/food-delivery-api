package io.github.romolotok29.deliveryplatform.email_verification.event_listener;

import io.github.romolotok29.deliveryplatform.email_verification.event.AccountDeletedEvent;
import io.github.romolotok29.deliveryplatform.email_verification.service.EmailNotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
@RequiredArgsConstructor
public class AccountDeletedEventListener {

    private final EmailNotificationService emailNotificationService;

    @Async
    @TransactionalEventListener
    public void accountDeletedEvent(AccountDeletedEvent event) {

        emailNotificationService.sendAccountDeletedNotification(event.emailAddress());
    }

}
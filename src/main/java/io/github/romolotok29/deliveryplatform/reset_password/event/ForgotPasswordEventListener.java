package io.github.romolotok29.deliveryplatform.reset_password.event;

import io.github.romolotok29.deliveryplatform.email_verification.service.EmailNotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
@RequiredArgsConstructor
public class ForgotPasswordEventListener {

    private final EmailNotificationService emailNotificationService;

    @Async
    @TransactionalEventListener
    public void handleUserRegisteredEvent(ForgotPasswordEvent event) {

        emailNotificationService.sendPasswordResetEmail(event.emailAddress(), event.verificationToken());
    }

}

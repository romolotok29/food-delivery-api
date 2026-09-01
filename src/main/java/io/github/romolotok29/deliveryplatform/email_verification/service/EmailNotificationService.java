package io.github.romolotok29.deliveryplatform.email_verification.service;

import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailNotificationService {

    private final JavaMailSender mailSender;

    @Async
    public void sendVerificationEmail(String to, String token) {

        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(to);
            message.setFrom("roland.from.laptop@gmail.com");
            message.setSubject("Confirm your email address.");

            String messageBody = """
                    Thank you for registration! 🎊
                    
                    Your email verification token:
                    
                    %s
                    """.formatted(token);

            message.setText(messageBody);
            mailSender.send(message);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
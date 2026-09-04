package io.github.romolotok29.deliveryplatform.email_verification.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmailNotificationService {

    private final JavaMailSender mailSender;
    private static final String EMAIL_FROM = "roland.from.laptop@gmail.com";

    @Async
    public void sendVerificationEmail(String to, String token) {
        send(
                to,
                "Confirm your email address",
                """
                Thank you for creating an account with us! 🎊
    
                Please verify your email address to complete your registration.
                
                You verification token:
    
                %s
                
                This token will expire in 15 minutes.
                
                Best regards,
                
                The Food Delivery Team
                """.formatted(token)
        );
    }

    @Async
    public void sendPasswordResetEmail(String to, String token) {
        send(
                to,
                "Reset your password",
                """
                We received a request to reset your password.
    
                Please verify your email address using the following token:
    
                %s
                
                This token will expire in 15 minutes.
    
                If you did not request a password reset, you can safely ignore this email.
                
                Best regards,
                
                The Food Delivery Team
                """.formatted(token)
        );
    }

    private void send(String to, String subject, String text) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(to);
            message.setFrom(EMAIL_FROM);
            message.setSubject(subject);
            message.setText(text);

            mailSender.send(message);

        } catch (MailException e) {

//            log.error("Failed to send email to {}", to, e);
        }
    }

}
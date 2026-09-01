package io.github.romolotok29.deliveryplatform.exceptions;

import io.github.romolotok29.deliveryplatform.exceptions.authentication.InvalidCredentialsException;
import io.github.romolotok29.deliveryplatform.exceptions.registration.UserAlreadyExistsException;
import io.github.romolotok29.deliveryplatform.exceptions.verification.EmailAddressAlreadyVerifiedException;
import io.github.romolotok29.deliveryplatform.exceptions.verification.UnverifiedEmailAddressException;
import io.github.romolotok29.deliveryplatform.exceptions.verification.VerificationTokenExpiredException;
import io.github.romolotok29.deliveryplatform.exceptions.verification.VerificationTokenNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<ApiErrorResponse> handleUserAlreadyExistsException(
            UserAlreadyExistsException ex,
            HttpServletRequest request
    ) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(new ApiErrorResponse(
                        "USER_ALREADY_EXISTS",
                        ex.getMessage(),
                        request.getRequestURI(),
                        LocalDateTime.now()
                )
        );
    }

    @ExceptionHandler(UnverifiedEmailAddressException.class)
    public ResponseEntity<ApiErrorResponse> handleUnverifiedEmailAddressException(
            UnverifiedEmailAddressException ex,
            HttpServletRequest request
    ) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(
                new ApiErrorResponse(
                        "UNVERIFIED_EMAIL_ADDRESS",
                        ex.getMessage(),
                        request.getRequestURI(),
                        LocalDateTime.now()
                )
        );
    }

    @ExceptionHandler(EmailAddressAlreadyVerifiedException.class)
    public ResponseEntity<ApiErrorResponse> handleEmailAddressAlreadyVerifiedException(
            EmailAddressAlreadyVerifiedException ex,
            HttpServletRequest request
    ) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(
                new ApiErrorResponse(
                        "EMAIL_ALREADY_VERIFIED",
                        ex.getMessage(),
                        request.getRequestURI(),
                        LocalDateTime.now()
                )
        );
    }

    @ExceptionHandler(VerificationTokenExpiredException.class)
    public ResponseEntity<ApiErrorResponse> handleVerificationTokenExpiredException(
            VerificationTokenExpiredException ex,
            HttpServletRequest request
    ) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                new ApiErrorResponse(
                        "VERIFICATION_TOKEN_EXPIRED",
                        ex.getMessage(),
                        request.getRequestURI(),
                        LocalDateTime.now()
                )
        );
    }

    @ExceptionHandler(VerificationTokenNotFoundException.class)
    public ResponseEntity<ApiErrorResponse> handleVerificationTokenNotFoundException(
            VerificationTokenNotFoundException ex,
            HttpServletRequest request
    ) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                new ApiErrorResponse(
                        "VERIFICATION_TOKEN_NOT_FOUND",
                        ex.getMessage(),
                        request.getRequestURI(),
                        LocalDateTime.now()
                )
        );
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, String> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {

        Map<String, String> errors = new HashMap<>();

        ex.getBindingResult().getFieldErrors().forEach(fieldError -> {

                    String fieldName = fieldError.getField();
                    String message = fieldError.getDefaultMessage();
                    errors.put(fieldName, message);
                }
        );
        return errors;
    }

    @ExceptionHandler(InvalidCredentialsException.class)
    public ResponseEntity<ApiErrorResponse> handleInvalidCredentialsException(
            InvalidCredentialsException ex,
            HttpServletRequest request
    ) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(
                new ApiErrorResponse(
                        "UNAUTHORIZED",
                        ex.getMessage(),
                        request.getRequestURI(),
                        LocalDateTime.now()
                )
        );
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiErrorResponse> handleException(HttpServletRequest request) {

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                new ApiErrorResponse(
                        "INTERNAL_SERVER_ERROR",
                        "Unexpected internal error.",
                        request.getRequestURI(),
                        LocalDateTime.now()
                )
        );
    }

}
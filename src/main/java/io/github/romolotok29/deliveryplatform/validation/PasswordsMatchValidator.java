package io.github.romolotok29.deliveryplatform.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.stereotype.Component;

@Component
public class PasswordsMatchValidator implements ConstraintValidator<PasswordsMatch, Object> {

    private String passwordField;
    private String confirmPasswordField;

    @Override
    public void initialize(PasswordsMatch annotation) {
        this.passwordField = annotation.password();
        this.confirmPasswordField = annotation.confirmPassword();
    }

    // Добавить ExceptionHandler
    @Override
    public boolean isValid(Object object, ConstraintValidatorContext context) {

        if (object == null) {
            return true;
        }

        BeanWrapper beanWrapper = new BeanWrapperImpl(object);

        Object password = beanWrapper.getPropertyValue(passwordField);
        Object confirmPassword = beanWrapper.getPropertyValue(confirmPasswordField);

        if (password == null || confirmPassword == null) {
            return true;
        }

        if (password.equals(confirmPassword)) {
            return true;
        }

        context.disableDefaultConstraintViolation();
        context.buildConstraintViolationWithTemplate("Passwords do not match!")
                .addPropertyNode("confirmPassword")
                .addConstraintViolation();

        return false;
    }

}
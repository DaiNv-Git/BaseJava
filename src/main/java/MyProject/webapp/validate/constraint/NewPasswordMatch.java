package MyProject.webapp.validate.constraint;

import MyProject.webapp.validate.NewPasswordMatchValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.TYPE, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = NewPasswordMatchValidator.class)
public @interface NewPasswordMatch {
    String message() default "repeat password must not match new password";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}


package MyProject.webapp.validate;

import MyProject.webapp.modle.request.ChangePasswordForm;
import MyProject.webapp.validate.constraint.NewPasswordMatch;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class NewPasswordMatchValidator implements ConstraintValidator<NewPasswordMatch, ChangePasswordForm> {

    @Override
    public void initialize(NewPasswordMatch constraintAnnotation) {
    }

    @Override
    public boolean isValid(ChangePasswordForm form, ConstraintValidatorContext context) {
        return !form.getNewPassword().equals(form.getRepeatPassword());
    }
}


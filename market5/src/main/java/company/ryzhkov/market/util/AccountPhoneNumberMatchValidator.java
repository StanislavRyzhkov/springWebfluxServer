package company.ryzhkov.market.util;

import org.springframework.beans.BeanWrapperImpl;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Pattern;

public class AccountPhoneNumberMatchValidator implements ConstraintValidator<AccountPhoneNumberMatch, Object> {
    private String fieldName;
    private String message;

    @Override
    public void initialize(AccountPhoneNumberMatch constraintAnnotation) {
        fieldName = constraintAnnotation.fieldName();
        message = constraintAnnotation.message();
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext constraintValidatorContext) {
        boolean valid = true;
        try {
            final Object obj = new BeanWrapperImpl(value).getPropertyValue(fieldName);
            Pattern p = Pattern.compile("\\+[0-9]+-[0-9]{3}-[0-9]{3}-[0-9]{4}");
            valid = obj == null || obj.equals("") || p.matcher((CharSequence) obj).matches();
        } catch (Exception ignore) {}
        if (!valid) {
            constraintValidatorContext
                    .buildConstraintViolationWithTemplate(message)
                    .addPropertyNode(fieldName)
                    .addConstraintViolation()
                    .disableDefaultConstraintViolation();
        }
        return valid;
    }
}

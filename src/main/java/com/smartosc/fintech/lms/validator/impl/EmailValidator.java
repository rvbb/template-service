package com.smartosc.fintech.lms.validator.impl;

import com.smartosc.fintech.lms.validator.EmailConstraint;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class EmailValidator implements ConstraintValidator<EmailConstraint,String> {
    @Override
    public void initialize(EmailConstraint constraintAnnotation) {
        /*not hava body*/
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {

        String regrex = "^\\w+[\\w-\\.]*\\@\\w+((-\\w+)|(\\w*))\\.[a-z]{2,3}$";
        return value != null
                && value.matches(regrex)
                && (value.length() > 0)
                && (value.length() < 64);
    }
}

package org.gmalliaris.m222proj2gmalliaris.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class DateValidator implements ConstraintValidator<ValidDate, String> {

    private static final String DATE_PATTERN = "yyyy-MM-dd hh:mm:ss";

    @Override
    public boolean isValid(String stringDateField, ConstraintValidatorContext constraintValidatorContext) {

        var sdf = new SimpleDateFormat(DATE_PATTERN);
        try
        {
            sdf.parse(stringDateField);
            return true;
        } catch (ParseException e) {
            return false;
        }
    }
}

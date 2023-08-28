package rs.ac.bg.fon.silab.mock_exam.domain.pricelist.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import rs.ac.bg.fon.silab.mock_exam.domain.pricelist.annotation.ValidYear;

import java.time.Year;

public class ValidYearValidator implements ConstraintValidator<ValidYear, Year> {
    @Override
    public boolean isValid(Year year, ConstraintValidatorContext constraintValidatorContext) {

        int currentYear = Year.now().getValue();
        int minYear = 2023;
        int maxYear = 2100;

        return year.getValue() >= minYear && year.getValue() <= maxYear;
    }
}

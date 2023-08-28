package rs.ac.bg.fon.silab.mock_exam.domain.pricelist.annotation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import rs.ac.bg.fon.silab.mock_exam.domain.pricelist.validator.ValidYearValidator;

import java.lang.annotation.*;

@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = ValidYearValidator.class)
@Documented
public @interface ValidYear {
    String message() default "Invalid year";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}

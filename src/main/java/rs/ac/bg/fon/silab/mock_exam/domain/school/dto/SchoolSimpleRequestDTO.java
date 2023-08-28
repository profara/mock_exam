package rs.ac.bg.fon.silab.mock_exam.domain.school.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record SchoolSimpleRequestDTO(
        @NotNull(message = "School code is mandatory")
        @Max(value = 9999999, message = "School code can't have more than 7 digits")
        @Min(value = 100000, message = "School code can't have less than 6 digits")
        Long code
) {
}

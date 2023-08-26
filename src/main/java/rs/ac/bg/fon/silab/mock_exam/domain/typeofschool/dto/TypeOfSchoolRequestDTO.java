package rs.ac.bg.fon.silab.mock_exam.domain.typeofschool.dto;

import jakarta.validation.constraints.NotBlank;

public record TypeOfSchoolRequestDTO(
        @NotBlank(message = "Name is mandatory")
        String name
) {
}

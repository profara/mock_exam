package rs.ac.bg.fon.silab.mock_exam.domain.school.dto;

import jakarta.validation.constraints.NotBlank;

public record SchoolUpdateRequestDTO(
        @NotBlank(message = "Name is mandatory")
        String name
) {
}

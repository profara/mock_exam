package rs.ac.bg.fon.silab.mock_exam.domain.exam.dto;

import jakarta.validation.constraints.NotBlank;

public record ExamRequestDTO(
        @NotBlank(message = "Name is mandatory")
        String name
) {
}

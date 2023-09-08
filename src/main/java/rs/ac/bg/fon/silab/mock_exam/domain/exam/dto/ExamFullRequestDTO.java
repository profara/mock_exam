package rs.ac.bg.fon.silab.mock_exam.domain.exam.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record ExamFullRequestDTO(
        @NotNull(message = "Id is mandatory")
        Long id,
        @NotBlank(message = "Name is mandatory")
        String name
) {
}

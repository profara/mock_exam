package rs.ac.bg.fon.silab.mock_exam.domain.userrole.dto;

import jakarta.validation.constraints.NotBlank;

public record UserRoleRequestDTO(
        @NotBlank(message = "Name is mandatory")
        String name) {
}

package rs.ac.bg.fon.silab.mock_exam.domain.user.profile.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import rs.ac.bg.fon.silab.mock_exam.domain.user.role.dto.UserRoleRequestDTO;

public record UserProfileUpdateRoleRequestDTO(
        @NotNull(message = "User role is mandatory")
        @Valid
        UserRoleRequestDTO userRole
) {
}

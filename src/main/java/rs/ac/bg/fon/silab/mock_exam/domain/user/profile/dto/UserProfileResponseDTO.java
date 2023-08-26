package rs.ac.bg.fon.silab.mock_exam.domain.user.profile.dto;

import rs.ac.bg.fon.silab.mock_exam.domain.user.role.dto.UserRoleResponseDTO;

public record UserProfileResponseDTO(
        Long id,
        String email,
        UserRoleResponseDTO userRole
) {
}

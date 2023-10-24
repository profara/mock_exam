package rs.ac.bg.fon.silab.mock_exam.domain.userprofile.dto;

import rs.ac.bg.fon.silab.mock_exam.domain.userrole.dto.UserRoleResponseDTO;

public record UserProfileResponseDTO(
        Long id,
        String email,
        boolean enabled,
        UserRoleResponseDTO userRole
) {
}

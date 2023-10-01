package rs.ac.bg.fon.silab.mock_exam.infrastructure.security.auth;

import rs.ac.bg.fon.silab.mock_exam.domain.userprofile.dto.UserProfileResponseDTO;

public record AuthenticationResponseDTO(
        String token,
        UserProfileResponseDTO userProfileDTO
) {
}

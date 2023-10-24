package rs.ac.bg.fon.silab.mock_exam.domain.userprofile.dto;

public record RegistrationResponseDTO(
        UserProfileResponseDTO userProfileDTO,
        String jwtToken
) {
}

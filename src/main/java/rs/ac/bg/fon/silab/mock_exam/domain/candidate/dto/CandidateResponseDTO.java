package rs.ac.bg.fon.silab.mock_exam.domain.candidate.dto;

import rs.ac.bg.fon.silab.mock_exam.domain.school.dto.SchoolResponseDTO;
import rs.ac.bg.fon.silab.mock_exam.domain.userprofile.dto.UserProfileResponseDTO;

public record CandidateResponseDTO(
        Long id,
        String name,
        String surname,
        String address,
        boolean attendedPreparation,
        UserProfileResponseDTO userProfile,
        SchoolResponseDTO school
) {
}

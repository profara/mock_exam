package rs.ac.bg.fon.silab.mock_exam.domain.candidate.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import rs.ac.bg.fon.silab.mock_exam.domain.school.dto.SchoolSimpleRequestDTO;
import rs.ac.bg.fon.silab.mock_exam.domain.userprofile.dto.UserProfileSimpleRequestDTO;

public record CandidateRequestDTO(
        @NotBlank(message = "Name is mandatory")
        String name,
        @NotBlank(message = "Surname is mandatory")
        String surname,
        @NotNull(message = "Attended preparation is mandatory")
        boolean attendedPreparation,
        @Valid
        @NotNull
        UserProfileSimpleRequestDTO userProfile,
        @Valid
        @NotNull
        SchoolSimpleRequestDTO school

) {
}

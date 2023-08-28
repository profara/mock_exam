package rs.ac.bg.fon.silab.mock_exam.domain.candidate.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CandidateUpdateRequestDTO(
        @NotBlank(message = "Name is mandatory")
        String name,
        @NotBlank(message = "Surname is mandatory")
        String surname,
        @NotNull(message = "Attended preparation is mandatory")
        boolean attendedPreparation
) {
    public boolean hasAttendedPreparation(){
        return attendedPreparation;
    }
}

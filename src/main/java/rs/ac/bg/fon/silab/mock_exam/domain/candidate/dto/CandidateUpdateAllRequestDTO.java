package rs.ac.bg.fon.silab.mock_exam.domain.candidate.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CandidateUpdateAllRequestDTO(
        @NotBlank(message = "Name is mandatory")
        String name,
        @NotBlank(message = "Surname is mandatory")
        String surname,
        @NotNull(message = "Attended preparation is mandatory")
        boolean attendedPreparation,
        @NotBlank(message = "Address is mandatory")
        String address,
        @NotNull(message = "School code is mandatory")
        @Max(value = 9999999, message = "School code can't have more than 7 digits")
        @Min(value = 100000, message = "School code can't have less than 6 digits")
        Long school
) {
}

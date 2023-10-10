package rs.ac.bg.fon.silab.mock_exam.domain.candidate.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import rs.ac.bg.fon.silab.mock_exam.domain.school.dto.SchoolSimpleRequestDTO;
import rs.ac.bg.fon.silab.mock_exam.domain.userprofile.dto.UserProfileSimpleRequestDTO;

public record CandidateRequestDTO(
        @NotBlank(message = "Name is mandatory")
        String name,
        @NotBlank(message = "Surname is mandatory")
        String surname,
        @NotBlank(message = "Address is mandatory")
        String address,
        @NotNull(message = "Attended preparation is mandatory")
        boolean attendedPreparation,
        @Valid
        @NotNull
        UserProfileSimpleRequestDTO userProfile,
        @NotNull(message = "School code is mandatory")
        @Max(value = 9999999, message = "School code can't have more than 7 digits")
        @Min(value = 100000, message = "School code can't have less than 6 digits")
        Long school,
        @NotNull(message = "City is mandatory")
        @Max(value = 99999,message = "Zip code must consist of exactly 5 digits")
        @Min(value = 10000,message = "Zip code must consist of exactly 5 digits")
        Long city

) {
}

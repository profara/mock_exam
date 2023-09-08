package rs.ac.bg.fon.silab.mock_exam.domain.candidate.dto;

import jakarta.validation.constraints.NotNull;

public record CandidateSimpleRequestDTO(
        @NotNull(message = "Candidate id is mandatory")
        Long id
) {
}

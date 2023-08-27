package rs.ac.bg.fon.silab.mock_exam.domain.city.dto;

import jakarta.validation.constraints.NotBlank;

public record CityRequestUpdateDTO(
        @NotBlank(message = "Name is mandatory")
        String name
) {
}

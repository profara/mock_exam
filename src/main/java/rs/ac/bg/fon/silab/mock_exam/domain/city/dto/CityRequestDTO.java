package rs.ac.bg.fon.silab.mock_exam.domain.city.dto;

import jakarta.validation.constraints.*;

public record CityRequestDTO(
        @NotNull(message = "Zip code is mandatory")
        @Max(value = 99999,message = "Zip code must consist of exactly 5 digits")
        @Min(value = 10000,message = "Zip code must consist of exactly 5 digits")
        Long zipCode,
        @NotBlank(message = "Name is mandatory")
        String name
) {
}

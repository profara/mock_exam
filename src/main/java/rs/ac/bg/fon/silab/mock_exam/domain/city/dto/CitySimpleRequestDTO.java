package rs.ac.bg.fon.silab.mock_exam.domain.city.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record CitySimpleRequestDTO(
        @NotNull(message = "Zip code is mandatory")
        @Max(value = 99999,message = "Zip code must consist of exactly 5 digits")
        @Min(value = 10000,message = "Zip code must consist of exactly 5 digits")
        Long zipCode
) {
}

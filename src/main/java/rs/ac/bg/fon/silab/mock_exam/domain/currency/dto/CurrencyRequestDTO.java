package rs.ac.bg.fon.silab.mock_exam.domain.currency.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CurrencyRequestDTO(
        @NotBlank(message = "Name is mandatory")
        String name,
        @NotBlank(message = "Currency code is mandatory")
        @Size(min = 3,max = 3, message = "Currency code must have 3 characters")
        String code
) {
}

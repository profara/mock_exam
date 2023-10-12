package rs.ac.bg.fon.silab.mock_exam.domain.pricelistitem.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.Year;

public record PriceListItemCriteriaRequestDTO(
        @NotNull(message = "Year is mandatory")
        Year year,
        @NotNull(message = "Privileged is mandatory")
        Boolean privileged,
        @NotBlank(message = "Exam name is mandatory")
        String examName,
        @NotBlank(message = "Currency code is mandatory")
        String currencyCode
) {
}

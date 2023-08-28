package rs.ac.bg.fon.silab.mock_exam.domain.pricelist.dto;

import jakarta.validation.constraints.NotNull;
import rs.ac.bg.fon.silab.mock_exam.domain.pricelist.annotation.ValidYear;

import java.time.Year;

public record PriceListRequestDTO(
        @ValidYear
        @NotNull(message = "Year is mandatory")
        Year year

) {
}

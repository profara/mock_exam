package rs.ac.bg.fon.silab.mock_exam.domain.pricelist.dto;

import java.time.Year;

public record PriceListResponseDTO(
        Long id,
        Year year
) {
}

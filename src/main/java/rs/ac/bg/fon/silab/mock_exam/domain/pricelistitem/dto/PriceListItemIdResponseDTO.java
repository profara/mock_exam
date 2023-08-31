package rs.ac.bg.fon.silab.mock_exam.domain.pricelistitem.dto;

import rs.ac.bg.fon.silab.mock_exam.domain.pricelist.dto.PriceListResponseDTO;

public record PriceListItemIdResponseDTO(
        Long id,
        PriceListResponseDTO priceList
) {
}

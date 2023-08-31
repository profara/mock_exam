package rs.ac.bg.fon.silab.mock_exam.domain.pricelistitem.service;

import rs.ac.bg.fon.silab.mock_exam.domain.pricelistitem.dto.PriceListItemRequestDTO;
import rs.ac.bg.fon.silab.mock_exam.domain.pricelistitem.dto.PriceListItemResponseDTO;

public interface PriceListItemService {
    PriceListItemResponseDTO save(PriceListItemRequestDTO priceListItemDTO);
}

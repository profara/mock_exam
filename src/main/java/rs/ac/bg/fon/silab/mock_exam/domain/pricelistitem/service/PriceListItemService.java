package rs.ac.bg.fon.silab.mock_exam.domain.pricelistitem.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import rs.ac.bg.fon.silab.mock_exam.domain.pricelistitem.dto.PriceListItemRequestDTO;
import rs.ac.bg.fon.silab.mock_exam.domain.pricelistitem.dto.PriceListItemResponseDTO;
import rs.ac.bg.fon.silab.mock_exam.domain.pricelistitem.entity.PriceListItem;

import java.util.List;

public interface PriceListItemService {
    PriceListItemResponseDTO save(PriceListItemRequestDTO priceListItemDTO);

    PriceListItemResponseDTO getById(Long id);

    Page<PriceListItemResponseDTO> get(Pageable pageable);

    void delete(Long id);

    PriceListItemResponseDTO update(Long id, PriceListItemRequestDTO priceListItemDTO);

    List<PriceListItem> findByPriceList(Long id);
}

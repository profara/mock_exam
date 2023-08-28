package rs.ac.bg.fon.silab.mock_exam.domain.pricelist.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import rs.ac.bg.fon.silab.mock_exam.domain.pricelist.dto.PriceListRequestDTO;
import rs.ac.bg.fon.silab.mock_exam.domain.pricelist.dto.PriceListResponseDTO;

public interface PriceListService {
    PriceListResponseDTO save(PriceListRequestDTO priceListDTO);

    PriceListResponseDTO getById(Long id);

    Page<PriceListResponseDTO> get(Pageable pageable);

    void delete(Long id);

    PriceListResponseDTO update(Long id, PriceListRequestDTO priceListDTO);
}

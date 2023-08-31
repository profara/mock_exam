package rs.ac.bg.fon.silab.mock_exam.domain.pricelist.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import rs.ac.bg.fon.silab.mock_exam.domain.pricelist.dto.PriceListRequestDTO;
import rs.ac.bg.fon.silab.mock_exam.domain.pricelist.dto.PriceListResponseDTO;
import rs.ac.bg.fon.silab.mock_exam.domain.pricelist.entity.PriceList;

import java.time.Year;

public interface PriceListService {

    PriceList findByYear(Year year);
    PriceListResponseDTO save(PriceListRequestDTO priceListDTO);

    PriceListResponseDTO getById(Long id);

    Page<PriceListResponseDTO> get(Pageable pageable);

    void delete(Long id);

    PriceListResponseDTO update(Long id, PriceListRequestDTO priceListDTO);
}

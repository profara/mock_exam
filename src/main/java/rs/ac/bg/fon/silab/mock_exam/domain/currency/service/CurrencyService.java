package rs.ac.bg.fon.silab.mock_exam.domain.currency.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import rs.ac.bg.fon.silab.mock_exam.domain.currency.dto.CurrencyRequestDTO;
import rs.ac.bg.fon.silab.mock_exam.domain.currency.dto.CurrencyResponseDTO;

public interface CurrencyService {
    CurrencyResponseDTO save(CurrencyRequestDTO currencyDTO);

    CurrencyResponseDTO getById(Long id);

    Page<CurrencyResponseDTO> get(Pageable pageable);

    void delete(Long id);

    CurrencyResponseDTO update(Long id, CurrencyRequestDTO currencyDTO);
}

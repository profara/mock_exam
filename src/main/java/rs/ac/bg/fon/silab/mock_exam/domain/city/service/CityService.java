package rs.ac.bg.fon.silab.mock_exam.domain.city.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import rs.ac.bg.fon.silab.mock_exam.domain.city.dto.CityRequestDTO;
import rs.ac.bg.fon.silab.mock_exam.domain.city.dto.CityRequestUpdateDTO;
import rs.ac.bg.fon.silab.mock_exam.domain.city.dto.CityResponseDTO;

public interface CityService {
    CityResponseDTO save(CityRequestDTO cityDTO);

    CityResponseDTO getByZipCode(Long zipCode);

    Page<CityResponseDTO> get(Pageable pageable);

    void delete(Long zipCode);

    CityResponseDTO update(Long zipCode, CityRequestUpdateDTO cityDTO);
}

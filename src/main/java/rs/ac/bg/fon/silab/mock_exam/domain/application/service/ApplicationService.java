package rs.ac.bg.fon.silab.mock_exam.domain.application.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import rs.ac.bg.fon.silab.mock_exam.domain.application.dto.ApplicationRequestDTO;
import rs.ac.bg.fon.silab.mock_exam.domain.application.dto.ApplicationResponseDTO;
import rs.ac.bg.fon.silab.mock_exam.domain.application.entity.Application;

public interface ApplicationService {

    Application findById(Long id);
    ApplicationResponseDTO save(ApplicationRequestDTO applicationDTO);

    ApplicationResponseDTO getById(Long id);

    Page<ApplicationResponseDTO> get(Pageable pageable);

    void delete(Long id);
}

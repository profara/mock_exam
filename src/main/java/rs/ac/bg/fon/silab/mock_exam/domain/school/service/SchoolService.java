package rs.ac.bg.fon.silab.mock_exam.domain.school.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import rs.ac.bg.fon.silab.mock_exam.domain.school.dto.SchoolRequestDTO;
import rs.ac.bg.fon.silab.mock_exam.domain.school.dto.SchoolResponseDTO;
import rs.ac.bg.fon.silab.mock_exam.domain.school.dto.SchoolUpdateRequestDTO;
import rs.ac.bg.fon.silab.mock_exam.domain.school.entity.School;

public interface SchoolService {

    School findById(Long code);
    SchoolResponseDTO save(SchoolRequestDTO schoolDTO);

    SchoolResponseDTO getById(Long code);

    Page<SchoolResponseDTO> get(Pageable pageable);

    void delete(Long code);

    SchoolResponseDTO update(Long code, SchoolUpdateRequestDTO schoolDTO);
}

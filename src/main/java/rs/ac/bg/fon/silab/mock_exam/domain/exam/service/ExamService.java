package rs.ac.bg.fon.silab.mock_exam.domain.exam.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import rs.ac.bg.fon.silab.mock_exam.domain.exam.dto.ExamRequestDTO;
import rs.ac.bg.fon.silab.mock_exam.domain.exam.dto.ExamResponseDTO;
import rs.ac.bg.fon.silab.mock_exam.domain.exam.entity.Exam;

public interface ExamService {

    Exam findByName(String name);

    Exam findById(Long id);
    ExamResponseDTO save(ExamRequestDTO examDTO);

    ExamResponseDTO getById(Long id);

    Page<ExamResponseDTO> get(Pageable pageable);

    void delete(Long id);

    ExamResponseDTO update(Long id, ExamRequestDTO examDTO);
}

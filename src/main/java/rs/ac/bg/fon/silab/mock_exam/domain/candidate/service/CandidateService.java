package rs.ac.bg.fon.silab.mock_exam.domain.candidate.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import rs.ac.bg.fon.silab.mock_exam.domain.candidate.dto.CandidateRequestDTO;
import rs.ac.bg.fon.silab.mock_exam.domain.candidate.dto.CandidateResponseDTO;
import rs.ac.bg.fon.silab.mock_exam.domain.candidate.dto.CandidateUpdateRequestDTO;

public interface CandidateService {
    CandidateResponseDTO save(CandidateRequestDTO candidateDTO);

    CandidateResponseDTO getById(Long id);

    Page<CandidateResponseDTO> get(Pageable pageable);

    void delete(Long id);

    CandidateResponseDTO update(Long id, CandidateUpdateRequestDTO candidateDTO);
}

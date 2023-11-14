package rs.ac.bg.fon.silab.mock_exam.domain.candidate.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import rs.ac.bg.fon.silab.mock_exam.domain.candidate.dto.CandidateRequestDTO;
import rs.ac.bg.fon.silab.mock_exam.domain.candidate.dto.CandidateResponseDTO;
import rs.ac.bg.fon.silab.mock_exam.domain.candidate.entity.Candidate;

import java.util.List;

public interface CandidateService {

    Candidate findById(Long id);
    CandidateResponseDTO save(CandidateRequestDTO candidateDTO);

    CandidateResponseDTO getById(Long id);

    Page<CandidateResponseDTO> get(Pageable pageable);

    void delete(Long id);

    CandidateResponseDTO update(Long id, CandidateRequestDTO candidateDTO);

    CandidateResponseDTO getByEmail(String email);

    Page<CandidateResponseDTO> getByAppointmentId(Long appointmentId, Long zipCode, Long schoolCode, Boolean attendedPreparation, Pageable pageable);

    List<CandidateResponseDTO> getAllByAppointmentId(Long appointmentId, Long zipCode, Long schoolCode, Boolean attendedPreparation);

    boolean existsById(Long candidateId);

    Page<CandidateResponseDTO> filterCandidates(Long zipCode, Long schoolCode, Boolean attendedPreparation, Pageable pageable);

    Page<CandidateResponseDTO> filterByAppointmentId(Long appointmentId, Long zipCode, Long schoolCode, Boolean attendedPreparation, Pageable pageable);
}

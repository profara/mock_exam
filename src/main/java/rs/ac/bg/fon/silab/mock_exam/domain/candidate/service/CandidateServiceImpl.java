package rs.ac.bg.fon.silab.mock_exam.domain.candidate.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import rs.ac.bg.fon.silab.mock_exam.domain.appointment.entity.Appointment;
import rs.ac.bg.fon.silab.mock_exam.domain.appointment.service.AppointmentService;
import rs.ac.bg.fon.silab.mock_exam.domain.candidate.dto.CandidateRequestDTO;
import rs.ac.bg.fon.silab.mock_exam.domain.candidate.dto.CandidateResponseDTO;
import rs.ac.bg.fon.silab.mock_exam.domain.candidate.entity.Candidate;
import rs.ac.bg.fon.silab.mock_exam.domain.candidate.mapper.CandidateMapper;
import rs.ac.bg.fon.silab.mock_exam.domain.candidate.repository.CandidateRepository;
import rs.ac.bg.fon.silab.mock_exam.infrastructure.exception.EntityNotFoundException;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Implementation of the CandidateService interface.
 * Handles business logic related to candidate management, such as finding, saving, updating, and deleting candidates.
 */
@Service
public class CandidateServiceImpl implements CandidateService{

    private final CandidateRepository candidateRepository;
    private final CandidateMapper mapper;
    private final AppointmentService appointmentService;

    /**
     * Constructs a new CandidateServiceImpl with necessary dependencies.
     *
     * @param candidateRepository the repository for candidate data access
     * @param mapper the mapper for converting between entity and DTO
     * @param appointmentService the service for managing appointments
     */
    public CandidateServiceImpl(CandidateRepository candidateRepository, CandidateMapper mapper, AppointmentService appointmentService) {
        this.candidateRepository = candidateRepository;
        this.mapper = mapper;
        this.appointmentService = appointmentService;
    }

    @Override
    @Transactional(readOnly = true)
    public Candidate findById(Long id) {
        return candidateRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(Candidate.class.getSimpleName(),"id", id));

    }

    @Override
    @Transactional
    public CandidateResponseDTO save(CandidateRequestDTO candidateDTO) {
        Candidate candidate = mapper.map(candidateDTO);

        candidateRepository.save(candidate);

        return mapper.map(candidate);
    }

    @Override
    @Transactional(readOnly = true)
    public CandidateResponseDTO getById(Long id) {
        var candidate = candidateRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(Candidate.class.getSimpleName(),"id", id));

        return mapper.map(candidate);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<CandidateResponseDTO> get(Pageable pageable) {
        return candidateRepository.findAll(pageable).map(mapper::map);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        if(!candidateRepository.existsById(id)){
            throw new EntityNotFoundException(Candidate.class.getSimpleName(),"id",id);
        }

        candidateRepository.deleteById(id);
    }

    @Override
    @Transactional
    public CandidateResponseDTO update(Long id, CandidateRequestDTO candidateDTO) {
        var candidate = candidateRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(Candidate.class.getSimpleName(), "id", id));

        mapper.update(candidate, candidateDTO);

        candidateRepository.save(candidate);

        return mapper.map(candidate);
    }

    @Override
    @Transactional(readOnly = true)
    public CandidateResponseDTO getByEmail(String email) {
        var candidate = candidateRepository.findByUserProfile_Email(email)
                .orElseThrow(() -> new EntityNotFoundException(Candidate.class.getSimpleName(), "email", email));

        return mapper.map(candidate);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<CandidateResponseDTO> getByAppointmentId(Long appointmentId, Long zipCode, Long schoolCode, Boolean attendedPreparation, Pageable pageable) {
        if(!appointmentService.existsById(appointmentId)){
            throw new EntityNotFoundException(Appointment.class.getSimpleName(), "id", appointmentId);
        }

        Page<Candidate> candidates = candidateRepository.findByAppointmentId(appointmentId, zipCode, schoolCode, attendedPreparation, pageable);

        return candidates.map(mapper::map);
    }

    @Override
    @Transactional(readOnly = true)
    public List<CandidateResponseDTO> getAllByAppointmentId(Long appointmentId, Long zipCode, Long schoolCode, Boolean attendedPreparation) {
        if(!appointmentService.existsById(appointmentId)){
            throw new EntityNotFoundException(Appointment.class.getSimpleName(), "id", appointmentId);
        }

        List<Candidate> candidates = candidateRepository.findAllByAppointmentId(appointmentId, zipCode, schoolCode, attendedPreparation);

        return candidates.stream()
                .map(mapper::map)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existsById(Long candidateId) {
        return candidateRepository.existsById(candidateId);
    }

    @Override
    public Page<CandidateResponseDTO> filterCandidates(Long zipCode, Long schoolCode, Boolean attendedPreparation, Pageable pageable) {
        Page<Candidate> candidates = candidateRepository.filterByCriteria(zipCode, schoolCode, attendedPreparation, pageable);

        return candidates.map(mapper::map);
    }

    @Override
    public Page<CandidateResponseDTO> filterByAppointmentId(Long appointmentId, Long zipCode, Long schoolCode, Boolean attendedPreparation, Pageable pageable) {
        if(!appointmentService.existsById(appointmentId)){
            throw new EntityNotFoundException(Appointment.class.getSimpleName(), "id", appointmentId);
        }

        Page<Candidate> candidates = candidateRepository.filterByAppointmentIdAndCriteria(appointmentId, zipCode,schoolCode, attendedPreparation, pageable);

        return candidates.map(mapper::map);
    }

}

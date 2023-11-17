package rs.ac.bg.fon.silab.mock_exam.domain.appointment.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import rs.ac.bg.fon.silab.mock_exam.domain.appointment.dto.AppointmentRequestDTO;
import rs.ac.bg.fon.silab.mock_exam.domain.appointment.dto.AppointmentResponseDTO;
import rs.ac.bg.fon.silab.mock_exam.domain.appointment.entity.Appointment;
import rs.ac.bg.fon.silab.mock_exam.domain.appointment.mapper.AppointmentMapper;
import rs.ac.bg.fon.silab.mock_exam.domain.appointment.repository.AppointmentRepository;
import rs.ac.bg.fon.silab.mock_exam.domain.candidate.entity.Candidate;
import rs.ac.bg.fon.silab.mock_exam.domain.candidate.repository.CandidateRepository;
import rs.ac.bg.fon.silab.mock_exam.infrastructure.exception.EntityNotFoundException;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Implementation of the AppointmentService interface.
 * Handles business logic related to appointment management, such as finding, saving, updating, and deleting appointments.
 */
@Service
public class AppointmentServiceImpl implements AppointmentService{

    private final AppointmentRepository appointmentRepository;
    private final AppointmentMapper mapper;
    private final CandidateRepository candidateRepository;

    /**
     * Constructs a new AppointmentServiceImpl with necessary dependencies.
     *
     * @param appointmentRepository the repository for appointment data access
     * @param mapper the mapper for converting between entity and DTO
     * @param candidateRepository the repository for accessing candidate data
     */
    public AppointmentServiceImpl(AppointmentRepository appointmentRepository, AppointmentMapper mapper, CandidateRepository candidateRepository) {
        this.appointmentRepository = appointmentRepository;
        this.mapper = mapper;
        this.candidateRepository = candidateRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public Appointment getById(Long id) {
        return appointmentRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(Appointment.class.getSimpleName(), "id", id));

    }

    @Override
    @Transactional
    public AppointmentResponseDTO save(AppointmentRequestDTO appointmentRequestDTO) {
        Appointment appointment = mapper.map(appointmentRequestDTO);

        appointmentRepository.save(appointment);

        return mapper.map(appointment);
    }

    @Override
    @Transactional(readOnly = true)
    public AppointmentResponseDTO findById(Long id) {
        var appointment = appointmentRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(Appointment.class.getSimpleName(), "id", id));

        return mapper.map(appointment);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<AppointmentResponseDTO> get(Pageable pageable) {
        return appointmentRepository.findAll(pageable).map(mapper::map);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        if(!appointmentRepository.existsById(id)){
            throw new EntityNotFoundException(Appointment.class.getSimpleName(), "id", id);
        }

        appointmentRepository.deleteById(id);
    }

    @Override
    @Transactional
    public AppointmentResponseDTO update(Long id, AppointmentRequestDTO appointmentRequestDTO) {
        var appointment = appointmentRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(Appointment.class.getSimpleName(), "id", id));

        Appointment tempAppointment = mapper.map(appointmentRequestDTO);

        mapper.update(appointment, tempAppointment);

        appointmentRepository.save(appointment);

        return mapper.map(appointment);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existsById(Long id) {
        return appointmentRepository.existsById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<AppointmentResponseDTO> getByCandidateId(Long candidateId, Pageable pageable) {
        if(!candidateRepository.existsById(candidateId)){
            throw new EntityNotFoundException(Candidate.class.getSimpleName(), "id", candidateId);
        }

        Page<Appointment> appointments = appointmentRepository.findByCandidateId(candidateId, pageable);

        return appointments.map(mapper::map);
    }

    @Override
    @Transactional(readOnly = true)
    public List<AppointmentResponseDTO> getAllSorted() {
        List<Appointment> sortedAppointments = appointmentRepository.findAllByOrderByAppointmentDateAscExamNameAsc();

        return sortedAppointments.stream()
                .map(mapper::map)
                .collect(Collectors.toList());
    }

    @Override
    public Page<AppointmentResponseDTO> getByCandidateIdNotSigned(Long candidateId, Pageable pageable) {
        if(!candidateRepository.existsById(candidateId)){
            throw new EntityNotFoundException(Candidate.class.getSimpleName(), "id", candidateId);
        }

        Page<Appointment> appointments = appointmentRepository.findByCandidateIdNotSigned(candidateId, pageable);

        return appointments.map(mapper::map);
    }


}

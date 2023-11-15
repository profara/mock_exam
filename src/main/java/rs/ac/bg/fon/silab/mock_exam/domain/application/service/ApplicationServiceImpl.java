package rs.ac.bg.fon.silab.mock_exam.domain.application.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import rs.ac.bg.fon.silab.mock_exam.domain.application.dto.ApplicationRequestDTO;
import rs.ac.bg.fon.silab.mock_exam.domain.application.dto.ApplicationResponseDTO;
import rs.ac.bg.fon.silab.mock_exam.domain.application.entity.Application;
import rs.ac.bg.fon.silab.mock_exam.domain.application.exception.DuplicateAppointmentApplicationException;
import rs.ac.bg.fon.silab.mock_exam.domain.application.mapper.ApplicationMapper;
import rs.ac.bg.fon.silab.mock_exam.domain.application.repository.ApplicationRepository;
import rs.ac.bg.fon.silab.mock_exam.domain.appointment.entity.Appointment;
import rs.ac.bg.fon.silab.mock_exam.domain.appointment.service.AppointmentService;
import rs.ac.bg.fon.silab.mock_exam.domain.candidate.dto.CandidateResponseDTO;
import rs.ac.bg.fon.silab.mock_exam.domain.candidate.entity.Candidate;
import rs.ac.bg.fon.silab.mock_exam.domain.candidate.service.CandidateService;
import rs.ac.bg.fon.silab.mock_exam.infrastructure.exception.EntityNotFoundException;

import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * Implementation of the ApplicationService.
 * This class handles business logic for managing applications, including operations like finding, saving, deleting...
 */
@Service
public class ApplicationServiceImpl implements ApplicationService {

    private final ApplicationRepository applicationRepository;
    private final ApplicationMapper mapper;
    private final AppointmentService appointmentService;
    private final CandidateService candidateService;

    /**
     * Constructs a new ApplicationServiceImpl with required dependencies.
     *
     * @param applicationRepository the repository for application data access
     * @param mapper the mapper for converting between entity and DTO
     * @param appointmentService the service for managing appointments
     * @param candidateService the service for managing candidates
     */
    public ApplicationServiceImpl(ApplicationRepository applicationRepository, ApplicationMapper mapper, AppointmentService appointmentService, CandidateService candidateService) {
        this.applicationRepository = applicationRepository;
        this.mapper = mapper;
        this.appointmentService = appointmentService;
        this.candidateService = candidateService;
    }

    @Override
    @Transactional(readOnly = true)
    public Application findById(Long id) {
        return applicationRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(Application.class.getSimpleName(), "id", id));
    }

    @Override
    @Transactional
    public ApplicationResponseDTO save(ApplicationRequestDTO applicationDTO) {
        Application application = mapper.map(applicationDTO);

        List<Application> existingApplications = applicationRepository.findByCandidateId(application.getCandidate().getId());

        for (Appointment newAppointment : application.getAppointments()) {
            for (Application existingApplication : existingApplications) {
                if (existingApplication.getAppointments().stream().anyMatch(app -> app.getId().equals(newAppointment.getId()))) {
                    throw new DuplicateAppointmentApplicationException("Ne mozete se prijaviti za jedan termin vise puta");
                }
            }
        }

        applicationRepository.save(application);

        return mapper.map(application);
    }

    @Override
    @Transactional(readOnly = true)
    public ApplicationResponseDTO getById(Long id) {
        var application = applicationRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(Application.class.getSimpleName(), "id", id));

        return mapper.map(application);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ApplicationResponseDTO> get(Pageable pageable) {
        return applicationRepository.findAll(pageable).map(mapper::map);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        if (!applicationRepository.existsById(id)) {
            throw new EntityNotFoundException(Application.class.getSimpleName(), "id", id);
        }

        applicationRepository.deleteById(id);
    }

    @Override
    @Transactional
    public void deleteAppointment(Long candidateId, Long appointmentId) {

        if(!candidateService.existsById(candidateId)){
            throw new EntityNotFoundException(Candidate.class.getSimpleName(), "id", candidateId);
        }

        List<Application> candidateApplications = applicationRepository.findByCandidateId(candidateId);

        boolean isAppointmentRemoved = false;



        if (!candidateApplications.isEmpty()) {

            for (Application application : candidateApplications) {
                Appointment targetAppointment = null;
                for (Appointment appointment : application.getAppointments()) {
                    if (appointment.getId().equals(appointmentId)) {
                        targetAppointment = appointment;
                        break;
                    }
                }

                if (targetAppointment != null) {
                    application.removeAppointment(targetAppointment);
                    applicationRepository.save(application);
                    isAppointmentRemoved = true;
                    break;
                }
            }
        }

        if (!isAppointmentRemoved) {
            throw new EntityNotFoundException(Appointment.class.getSimpleName(), "id", appointmentId);
        }


    }

    @Override
    @Transactional
    public ApplicationResponseDTO createApplicationWithAppointment(Long candidateId, Long appointmentId) {
        if (!candidateService.existsById(candidateId)) {
            throw new EntityNotFoundException(Candidate.class.getSimpleName(), "id", candidateId);
        }

        Candidate candidate = candidateService.findById(candidateId);

        Appointment appointment = appointmentService.getById(appointmentId);

        Application application = new Application(new Date(), candidate.isAttendedPreparation(), candidate);

        application.addAppointment(appointment);

        applicationRepository.save(application);

        return mapper.map(application);
    }

}

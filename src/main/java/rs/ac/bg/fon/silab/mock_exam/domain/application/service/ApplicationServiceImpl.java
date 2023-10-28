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
import rs.ac.bg.fon.silab.mock_exam.infrastructure.exception.EntityNotFoundException;

import java.util.List;

@Service
public class ApplicationServiceImpl implements ApplicationService{

    private final ApplicationRepository applicationRepository;
    private final ApplicationMapper mapper;
    private final AppointmentService appointmentService;

    public ApplicationServiceImpl(ApplicationRepository applicationRepository, ApplicationMapper mapper,AppointmentService appointmentService) {
        this.applicationRepository = applicationRepository;
        this.mapper = mapper;
        this.appointmentService = appointmentService;
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
        if(!applicationRepository.existsById(id)){
            throw new EntityNotFoundException(Application.class.getSimpleName(), "id", id);
        }

        applicationRepository.deleteById(id);
    }
}

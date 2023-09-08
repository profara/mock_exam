package rs.ac.bg.fon.silab.mock_exam.domain.application.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import rs.ac.bg.fon.silab.mock_exam.domain.application.dto.ApplicationRequestDTO;
import rs.ac.bg.fon.silab.mock_exam.domain.application.dto.ApplicationResponseDTO;
import rs.ac.bg.fon.silab.mock_exam.domain.application.entity.Application;
import rs.ac.bg.fon.silab.mock_exam.domain.application.mapper.ApplicationMapper;
import rs.ac.bg.fon.silab.mock_exam.domain.application.repository.ApplicationRepository;
import rs.ac.bg.fon.silab.mock_exam.domain.appointment.service.AppointmentService;
import rs.ac.bg.fon.silab.mock_exam.infrastructure.exception.EntityNotFoundException;

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
    public ApplicationResponseDTO save(ApplicationRequestDTO applicationDTO) {
        Application application = mapper.map(applicationDTO);


        applicationRepository.save(application);

        return mapper.map(application);
    }

    @Override
    public ApplicationResponseDTO getById(Long id) {
        var application = applicationRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(Application.class.getSimpleName(), "id", id));

        return mapper.map(application);
    }

    @Override
    public Page<ApplicationResponseDTO> get(Pageable pageable) {
        return applicationRepository.findAll(pageable).map(mapper::map);
    }

    @Override
    public void delete(Long id) {
        if(!applicationRepository.existsById(id)){
            throw new EntityNotFoundException(Application.class.getSimpleName(), "id", id);
        }

        applicationRepository.deleteById(id);
    }
}

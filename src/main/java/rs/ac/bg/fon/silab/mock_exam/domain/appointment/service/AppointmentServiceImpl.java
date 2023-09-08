package rs.ac.bg.fon.silab.mock_exam.domain.appointment.service;

import org.springframework.stereotype.Service;
import rs.ac.bg.fon.silab.mock_exam.domain.appointment.entity.Appointment;
import rs.ac.bg.fon.silab.mock_exam.domain.appointment.mapper.AppointmentMapper;
import rs.ac.bg.fon.silab.mock_exam.domain.appointment.repository.AppointmentRepository;
import rs.ac.bg.fon.silab.mock_exam.infrastructure.exception.EntityNotFoundException;

@Service
public class AppointmentServiceImpl implements AppointmentService{

    private final AppointmentRepository appointmentRepository;
    private final AppointmentMapper mapper;

    public AppointmentServiceImpl(AppointmentRepository appointmentRepository, AppointmentMapper mapper) {
        this.appointmentRepository = appointmentRepository;
        this.mapper = mapper;
    }

    @Override
    public Appointment getById(Long id) {
        return appointmentRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(Appointment.class.getSimpleName(), "id", id));

    }
}

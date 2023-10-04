package rs.ac.bg.fon.silab.mock_exam.domain.application.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import rs.ac.bg.fon.silab.mock_exam.domain.application.dto.ApplicationRequestDTO;
import rs.ac.bg.fon.silab.mock_exam.domain.application.dto.ApplicationResponseDTO;
import rs.ac.bg.fon.silab.mock_exam.domain.application.entity.Application;
import rs.ac.bg.fon.silab.mock_exam.domain.appointment.entity.Appointment;
import rs.ac.bg.fon.silab.mock_exam.domain.appointment.mapper.AppointmentMapper;
import rs.ac.bg.fon.silab.mock_exam.domain.appointment.service.AppointmentService;
import rs.ac.bg.fon.silab.mock_exam.domain.candidate.service.CandidateService;

import java.util.List;

@Mapper(componentModel = "spring", uses = {CandidateService.class, AppointmentService.class})
public interface ApplicationMapper {
    @Mapping(source = "appointmentIds", target = "appointments")
    @Mapping(source = "candidate", target = "candidate")
    Application map(ApplicationRequestDTO applicationDTO);

    List<Appointment> map(List<Long> appointmentIds);

    ApplicationResponseDTO map(Application application);
}



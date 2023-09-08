package rs.ac.bg.fon.silab.mock_exam.domain.appointment.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import rs.ac.bg.fon.silab.mock_exam.domain.appointment.dto.AppointmentRequestDTO;
import rs.ac.bg.fon.silab.mock_exam.domain.appointment.entity.Appointment;
import rs.ac.bg.fon.silab.mock_exam.domain.appointment.service.AppointmentService;
import rs.ac.bg.fon.silab.mock_exam.domain.exam.service.ExamService;

import java.util.List;

@Mapper(componentModel = "spring", uses = {ExamService.class, AppointmentService.class})
public interface AppointmentMapper {
//
//    List<Appointment> map(List<AppointmentRequestDTO> appointmentsDTO);
//
//    @Mapping(source = "exam.name", target = "exam")
//    Appointment map(AppointmentRequestDTO appointmentDTO);







    }


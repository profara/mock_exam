package rs.ac.bg.fon.silab.mock_exam.domain.appointment.mapper;

import org.mapstruct.Mapper;

import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import rs.ac.bg.fon.silab.mock_exam.domain.appointment.dto.AppointmentRequestDTO;
import rs.ac.bg.fon.silab.mock_exam.domain.appointment.dto.AppointmentResponseDTO;
import rs.ac.bg.fon.silab.mock_exam.domain.appointment.entity.Appointment;
import rs.ac.bg.fon.silab.mock_exam.domain.appointment.service.AppointmentService;
import rs.ac.bg.fon.silab.mock_exam.domain.exam.service.ExamService;

@Mapper(componentModel = "spring", uses = {ExamService.class, AppointmentService.class})
public interface AppointmentMapper {

    @Mapping(source = "examId", target = "exam")
    Appointment map(AppointmentRequestDTO appointmentRequestDTO);

    AppointmentResponseDTO map(Appointment appointment);

    @Mapping(target = "id", ignore = true)
    void update(@MappingTarget Appointment appointment, Appointment tempAppointment);
}


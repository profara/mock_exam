package rs.ac.bg.fon.silab.mock_exam.domain.appointment.dto;

import rs.ac.bg.fon.silab.mock_exam.domain.exam.dto.ExamResponseDTO;

import java.time.LocalDateTime;
import java.util.Date;

public record AppointmentResponseDTO(
        Long id,
        ExamResponseDTO exam,
        LocalDateTime appointmentDate
) {
}

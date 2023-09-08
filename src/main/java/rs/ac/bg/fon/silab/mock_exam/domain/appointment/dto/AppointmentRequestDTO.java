package rs.ac.bg.fon.silab.mock_exam.domain.appointment.dto;

import jakarta.validation.constraints.NotNull;

import java.util.Date;

public record AppointmentRequestDTO(
        @NotNull(message = "Exam id is mandatory")
        Long examId,
        @NotNull(message = "Date is mandatory")
        Date appointmentDate

) {
}

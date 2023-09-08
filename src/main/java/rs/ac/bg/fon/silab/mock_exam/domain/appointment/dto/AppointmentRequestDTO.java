package rs.ac.bg.fon.silab.mock_exam.domain.appointment.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import rs.ac.bg.fon.silab.mock_exam.domain.exam.dto.ExamFullRequestDTO;

import java.util.Date;

public record AppointmentRequestDTO(
        @NotNull(message = "Exam is mandatory")
        @Valid
        ExamFullRequestDTO exam,
        @NotNull(message = "Date is mandatory")
        Date appointmentDate

) {
}

package rs.ac.bg.fon.silab.mock_exam.domain.application.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import rs.ac.bg.fon.silab.mock_exam.domain.appointment.dto.AppointmentRequestDTO;
import rs.ac.bg.fon.silab.mock_exam.domain.candidate.dto.CandidateSimpleRequestDTO;

import java.util.Date;
import java.util.List;

public record ApplicationRequestDTO(
        @NotNull(message = "Application date is mandatory")
        Date applicationDate,
        @NotNull(message = "Privileged is mandatory")
        boolean privileged,
        @NotNull(message = "Candidate id is mandatory")
        Long candidate,
        @NotNull(message = "Appointment ids are mandatory")
        List<Long> appointmentIds

) {
}

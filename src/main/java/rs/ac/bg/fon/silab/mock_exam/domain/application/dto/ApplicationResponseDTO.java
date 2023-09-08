package rs.ac.bg.fon.silab.mock_exam.domain.application.dto;

import rs.ac.bg.fon.silab.mock_exam.domain.appointment.dto.AppointmentResponseDTO;
import rs.ac.bg.fon.silab.mock_exam.domain.candidate.dto.CandidateResponseDTO;

import java.util.Date;
import java.util.List;

public record ApplicationResponseDTO(
        Long id,
        Date applicationDate,
        boolean privileged,
        CandidateResponseDTO candidate,
        List<AppointmentResponseDTO> appointments
) {
}

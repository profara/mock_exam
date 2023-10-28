package rs.ac.bg.fon.silab.mock_exam.domain.appointment.service;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import rs.ac.bg.fon.silab.mock_exam.domain.appointment.dto.AppointmentRequestDTO;
import rs.ac.bg.fon.silab.mock_exam.domain.appointment.dto.AppointmentResponseDTO;
import rs.ac.bg.fon.silab.mock_exam.domain.appointment.entity.Appointment;
import rs.ac.bg.fon.silab.mock_exam.domain.candidate.dto.CandidateResponseDTO;

public interface AppointmentService {

    Appointment getById(Long id);

    AppointmentResponseDTO save(AppointmentRequestDTO appointmentRequestDTO);

    AppointmentResponseDTO findById(Long id);

    Page<AppointmentResponseDTO> get(Pageable pageable);

    void delete(Long id);

    AppointmentResponseDTO update(Long id, AppointmentRequestDTO appointmentRequestDTO);

    Page<CandidateResponseDTO> getCandidates(Long id, Pageable pageable);
}

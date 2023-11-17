package rs.ac.bg.fon.silab.mock_exam.domain.appointment.service;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import rs.ac.bg.fon.silab.mock_exam.domain.appointment.dto.AppointmentRequestDTO;
import rs.ac.bg.fon.silab.mock_exam.domain.appointment.dto.AppointmentResponseDTO;
import rs.ac.bg.fon.silab.mock_exam.domain.appointment.entity.Appointment;

import java.util.List;

/**
 * Service interface for managing appointments.
 * Offers functionalities such as retrieving, creating, updating, and deleting appointments.
 */
public interface AppointmentService {

    /**
     * Retrieves an appointment by its ID.
     *
     * @param id the ID of the appointment to retrieve
     * @return the found Appointment entity
     * @throws rs.ac.bg.fon.silab.mock_exam.infrastructure.exception.EntityNotFoundException if the Appointment is not found
     */
    Appointment getById(Long id);

    /**
     * Saves a new appointment or updates an existing one based on the provided DTO.
     *
     * @param appointmentRequestDTO the DTO containing appointment details
     * @return the saved or updated appointment as a DTO
     */
    AppointmentResponseDTO save(AppointmentRequestDTO appointmentRequestDTO);

    /**
     * Finds an appointment by its ID and returns it as a DTO.
     *
     * @param id the ID of the appointment to find
     * @return the appointment as a DTO
     * @throws rs.ac.bg.fon.silab.mock_exam.infrastructure.exception.EntityNotFoundException if the appointment is not found
     */
    AppointmentResponseDTO findById(Long id);

    /**
     * Retrieves a paginated list of appointments.
     *
     * @param pageable the pagination information
     * @return a page of AppointmentResponseDTO objects
     */
    Page<AppointmentResponseDTO> get(Pageable pageable);

    /**
     * Deletes an appointment by its ID.
     *
     * @param id the ID of the appointment to delete
     * @throws rs.ac.bg.fon.silab.mock_exam.infrastructure.exception.EntityNotFoundException if Appointment with entered ID doesn't exist
     */
    void delete(Long id);

    /**
     * Updates an existing appointment with new details provided in the DTO.
     *
     * @param id the ID of the appointment to update
     * @param appointmentRequestDTO the DTO containing updated details
     * @return the updated appointment as a DTO
     * @throws rs.ac.bg.fon.silab.mock_exam.infrastructure.exception.EntityNotFoundException if Appointment with entered ID doesn't exist
     */
    AppointmentResponseDTO update(Long id, AppointmentRequestDTO appointmentRequestDTO);

    /**
     * Checks if an appointment exists by its ID.
     *
     * @param id the ID of the appointment to check
     * @return
     * <ul>
     *     <li>{@code true} - if the appointment exists</li>
     *     <li>{@code false} - otherwise</li>
     * </ul>
     */
    boolean existsById(Long id);

    /**
     * Retrieves a paginated list of appointments associated with a specific candidate.
     *
     * @param candidateId the ID of the candidate
     * @param pageable the pagination information
     * @return a page of AppointmentResponseDTO objects
     * @throws rs.ac.bg.fon.silab.mock_exam.infrastructure.exception.EntityNotFoundException if Candidate with entered ID doesn't exist
     */
    Page<AppointmentResponseDTO> getByCandidateId(Long candidateId, Pageable pageable);

    /**
     * Retrieves a sorted list of all appointments.
     *
     * @return a list of AppointmentResponseDTO objects, sorted by criteria
     */
    List<AppointmentResponseDTO> getAllSorted();

    /**
     * Retrieves a paginated list of appointments that a specific candidate has not signed up for.
     *
     * @param candidateId the ID of the candidate
     * @param pageable the pagination information
     * @return a page of AppointmentResponseDTO objects
     * @throws rs.ac.bg.fon.silab.mock_exam.infrastructure.exception.EntityNotFoundException if Candidate with entered ID doesn't exist
     */
    Page<AppointmentResponseDTO> getByCandidateIdNotSigned(Long candidateId, Pageable pageable);
}

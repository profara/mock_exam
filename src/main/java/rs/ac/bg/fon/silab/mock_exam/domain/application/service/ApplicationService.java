package rs.ac.bg.fon.silab.mock_exam.domain.application.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import rs.ac.bg.fon.silab.mock_exam.domain.application.dto.ApplicationRequestDTO;
import rs.ac.bg.fon.silab.mock_exam.domain.application.dto.ApplicationResponseDTO;
import rs.ac.bg.fon.silab.mock_exam.domain.application.entity.Application;

/**
 * Service interface for managing applications.
 * Provides functionality for creating, retrieving, updating, and deleting application records.
 */
public interface ApplicationService {

    /**
     * Finds an application by its ID.
     *
     * @param id the ID of the application to find
     * @return the found Application
     * @throws rs.ac.bg.fon.silab.mock_exam.infrastructure.exception.EntityNotFoundException if Application not found
     */
    Application findById(Long id);

    /**
     * Saves an application provided as a DTO and returns the persisted state.
     *
     * @param applicationDTO the application data transfer object
     * @return the persisted state of the application as a DTO
     */
    ApplicationResponseDTO save(ApplicationRequestDTO applicationDTO);

    /**
     * Retrieves an application by its ID and returns it as a DTO.
     *
     * @param id the ID of the application to retrieve
     * @return the application as a DTO
     * @throws rs.ac.bg.fon.silab.mock_exam.infrastructure.exception.EntityNotFoundException if Application not found
     */
    ApplicationResponseDTO getById(Long id);

    /**
     * Retrieves a paginated list of application.
     *
     * @param pageable the pagination information
     * @return a page of ApplicationResponseDTO objects
     */
    Page<ApplicationResponseDTO> get(Pageable pageable);

    /**
     * Deletes an application by its ID.
     *
     * @param id the ID of the application to delete
     */
    void delete(Long id);

    /**
     * Deletes an appointment from a candidate's application.
     *
     * @param candidateId the ID of the candidate
     * @param appointmentId the ID of the appointment to be removed
     * @throws rs.ac.bg.fon.silab.mock_exam.infrastructure.exception.EntityNotFoundException if application with that ID doesn't exist
     */
    void deleteAppointment(Long candidateId, Long appointmentId);

    /**
     * Creates an application with a specific appointment.
     *
     * @param candidateId the ID of the candidate
     * @param appointmentId the ID of the appointment to be included in the application
     * @return the created application as a DTO
     * @throws rs.ac.bg.fon.silab.mock_exam.infrastructure.exception.EntityNotFoundException if Candidate with entered candidateId doesn't exists
     */
    ApplicationResponseDTO createApplicationWithAppointment(Long candidateId, Long appointmentId);
}

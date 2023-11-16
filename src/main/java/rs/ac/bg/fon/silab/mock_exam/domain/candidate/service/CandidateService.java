package rs.ac.bg.fon.silab.mock_exam.domain.candidate.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import rs.ac.bg.fon.silab.mock_exam.domain.candidate.dto.CandidateRequestDTO;
import rs.ac.bg.fon.silab.mock_exam.domain.candidate.dto.CandidateResponseDTO;
import rs.ac.bg.fon.silab.mock_exam.domain.candidate.entity.Candidate;

import java.util.List;

/**
 * Service interface for managing candidates.
 * Provides functionality for creating, retrieving, updating, and deleting candidate records.
 */
public interface CandidateService {

    /**
     * Finds a candidate by their ID.
     *
     * @param id the ID of the candidate to find
     * @return the found Candidate entity
     * @throws rs.ac.bg.fon.silab.mock_exam.infrastructure.exception.EntityNotFoundException if the Candidate is not found
     */
    Candidate findById(Long id);

    /**
     * Saves a new candidate or updates an existing one based on the provided DTO.
     *
     * @param candidateDTO the DTO containing candidate details
     * @return the saved or updated candidate as a DTO
     */
    CandidateResponseDTO save(CandidateRequestDTO candidateDTO);

    /**
     * Retrieves a candidate by their ID and returns it as a DTO.
     *
     * @param id the ID of the candidate to retrieve
     * @return the candidate as a DTO
     * @throws rs.ac.bg.fon.silab.mock_exam.infrastructure.exception.EntityNotFoundException if the candidate is not found
     */
    CandidateResponseDTO getById(Long id);

    /**
     * Retrieves a paginated list of candidates.
     *
     * @param pageable the pagination information
     * @return a page of CandidateResponseDTO objects
     */
    Page<CandidateResponseDTO> get(Pageable pageable);

    /**
     * Deletes a candidate by their ID.
     *
     * @param id the ID of the candidate to delete
     * @throws rs.ac.bg.fon.silab.mock_exam.infrastructure.exception.EntityNotFoundException if the candidate does not exist
     */
    void delete(Long id);

    /**
     * Updates an existing candidate with new details provided in the DTO.
     *
     * @param id the ID of the candidate to update
     * @param candidateDTO the DTO containing updated details
     * @return the updated candidate as a DTO
     * @throws rs.ac.bg.fon.silab.mock_exam.infrastructure.exception.EntityNotFoundException if the candidate does not exist
     */
    CandidateResponseDTO update(Long id, CandidateRequestDTO candidateDTO);

    /**
     * Retrieves a candidate by their email address and returns it as a DTO.
     *
     * @param email the email address of the candidate
     * @return the candidate as a DTO
     * @throws rs.ac.bg.fon.silab.mock_exam.infrastructure.exception.EntityNotFoundException if the candidate with the specified email does not exist
     */
    CandidateResponseDTO getByEmail(String email);

    /**
     * Retrieves a list of all candidates based on given criteria and associated with a specific appointment.
     *
     * @param appointmentId the ID of the appointment
     * @param zipCode the zip code for filtering candidates
     * @param schoolCode the school code for filtering candidates
     * @param attendedPreparation flag indicating whether the candidate attended preparation classes
     * @return a list of CandidateResponseDTO objects
     * @throws rs.ac.bg.fon.silab.mock_exam.infrastructure.exception.EntityNotFoundException if Appointment with the entered ID does not exist
     */
    Page<CandidateResponseDTO> getByAppointmentId(Long appointmentId, Long zipCode, Long schoolCode, Boolean attendedPreparation, Pageable pageable);

    /**
     * Retrieves a list of all candidates based on given criteria and associated with a specific appointment.
     *
     * @param appointmentId the ID of the appointment
     * @param zipCode the zip code for filtering candidates
     * @param schoolCode the school code for filtering candidates
     * @param attendedPreparation flag indicating whether the candidate attended preparation classes
     * @return a list of CandidateResponseDTO objects
     * @throws rs.ac.bg.fon.silab.mock_exam.infrastructure.exception.EntityNotFoundException if Appointment with the entered ID does not exist
     */
    List<CandidateResponseDTO> getAllByAppointmentId(Long appointmentId, Long zipCode, Long schoolCode, Boolean attendedPreparation);

    /**
     * Checks if a candidate exists by their ID.
     *
     * @param candidateId the ID of the candidate to check
     * @return true if the candidate exists, false otherwise
     */
    boolean existsById(Long candidateId);

    /**
     * Filters candidates based on given criteria.
     *
     * @param zipCode the zip code for filtering candidates
     * @param schoolCode the school code for filtering candidates
     * @param attendedPreparation flag indicating whether the candidate attended preparation classes
     * @param pageable the pagination information
     * @return a page of CandidateResponseDTO objects
     */
    Page<CandidateResponseDTO> filterCandidates(Long zipCode, Long schoolCode, Boolean attendedPreparation, Pageable pageable);

    /**
     * Filters candidates based on given criteria and association with a specific appointment.
     *
     * @param appointmentId the ID of the appointment
     * @param zipCode the zip code for filtering candidates
     * @param schoolCode the school code for filtering candidates
     * @param attendedPreparation flag indicating whether the candidate attended preparation classes
     * @param pageable the pagination information
     * @return a page of CandidateResponseDTO objects
     * @throws rs.ac.bg.fon.silab.mock_exam.infrastructure.exception.EntityNotFoundException if Appointment with entered ID does not exist
     */
    Page<CandidateResponseDTO> filterByAppointmentId(Long appointmentId, Long zipCode, Long schoolCode, Boolean attendedPreparation, Pageable pageable);
}

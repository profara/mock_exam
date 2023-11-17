package rs.ac.bg.fon.silab.mock_exam.domain.exam.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import rs.ac.bg.fon.silab.mock_exam.domain.exam.dto.ExamRequestDTO;
import rs.ac.bg.fon.silab.mock_exam.domain.exam.dto.ExamResponseDTO;
import rs.ac.bg.fon.silab.mock_exam.domain.exam.entity.Exam;

/**
 * Service interface for managing exams.
 * Offers functionalities such as retrieving, creating, updating, and deleting exams.
 */
public interface ExamService {

    /**
     * Retrieves an exam by its name.
     *
     * @param name the name of the exam to retrieve
     * @return the found Exam entity
     */
    Exam findByName(String name);

    /**
     * Retrieves an exam by its ID.
     *
     * @param id the ID of the exam to retrieve
     * @return the found Exam entity
     * @throws rs.ac.bg.fon.silab.mock_exam.infrastructure.exception.EntityNotFoundException if the Exam is not found
     */
    Exam findById(Long id);

    /**
     * Saves a new exam or updates an existing one based on the provided DTO.
     *
     * @param examDTO the DTO containing exam details
     * @return the saved or updated exam as a DTO
     */
    ExamResponseDTO save(ExamRequestDTO examDTO);

    /**
     * Retrieves an exam by its ID and returns it as a DTO.
     *
     * @param id the ID of the exam to retrieve
     * @return the exam as a DTO
     * @throws rs.ac.bg.fon.silab.mock_exam.infrastructure.exception.EntityNotFoundException if the exam is not found
     */
    ExamResponseDTO getById(Long id);

    /**
     * Retrieves a paginated list of exams.
     *
     * @param pageable the pagination information
     * @return a page of ExamResponseDTO objects
     */
    Page<ExamResponseDTO> get(Pageable pageable);

    /**
     * Deletes an exam by its ID.
     *
     * @param id the ID of the exam to delete
     * @throws rs.ac.bg.fon.silab.mock_exam.infrastructure.exception.EntityNotFoundException if the exam with entered ID does not exist
     */
    void delete(Long id);

    /**
     * Updates an existing exam with new details provided in the DTO.
     *
     * @param id the ID of the exam to update
     * @param examDTO the DTO containing updated details
     * @return the updated exam as a DTO
     * @throws rs.ac.bg.fon.silab.mock_exam.infrastructure.exception.EntityNotFoundException if the exam with entered ID does not exist
     */
    ExamResponseDTO update(Long id, ExamRequestDTO examDTO);
}

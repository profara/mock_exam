package rs.ac.bg.fon.silab.mock_exam.domain.school.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import rs.ac.bg.fon.silab.mock_exam.domain.school.dto.SchoolRequestDTO;
import rs.ac.bg.fon.silab.mock_exam.domain.school.dto.SchoolResponseDTO;
import rs.ac.bg.fon.silab.mock_exam.domain.school.dto.SchoolUpdateRequestDTO;
import rs.ac.bg.fon.silab.mock_exam.domain.school.entity.School;

import java.util.List;

/**
 * Service interface for managing schools.
 * Offers functionalities such as retrieving, creating, updating, and deleting schools.
 */
public interface SchoolService {

    /**
     * Retrieves a school by its code.
     *
     * @param code the code of the school to retrieve
     * @return the found School entity
     * @throws rs.ac.bg.fon.silab.mock_exam.infrastructure.exception.EntityNotFoundException if the School is not found
     */
    School findById(Long code);

    /**
     * Saves a new school or updates an existing one based on the provided DTO.
     *
     * @param schoolDTO the DTO containing school details
     * @return the saved or updated school as a DTO
     */
    SchoolResponseDTO save(SchoolRequestDTO schoolDTO);

    /**
     * Finds a school by its code and returns it as a DTO.
     *
     * @param code the code of the school to find
     * @return the school as a DTO
     * @throws rs.ac.bg.fon.silab.mock_exam.infrastructure.exception.EntityNotFoundException if the school is not found
     */
    SchoolResponseDTO getById(Long code);

    /**
     * Retrieves a paginated list of schools.
     *
     * @param pageable the pagination information
     * @return a page of SchoolResponseDTO objects
     */
    Page<SchoolResponseDTO> get(Pageable pageable);

    /**
     * Deletes a school by its code.
     *
     * @param code the code of the school to delete
     * @throws rs.ac.bg.fon.silab.mock_exam.infrastructure.exception.EntityNotFoundException if School with the entered code doesn't exist
     */
    void delete(Long code);

    /**
     * Updates an existing school with new details provided in the DTO.
     *
     * @param code the code of the school to update
     * @param schoolDTO the DTO containing updated details
     * @return the updated school as a DTO
     * @throws rs.ac.bg.fon.silab.mock_exam.infrastructure.exception.EntityNotFoundException if School with the entered code doesn't exist
     */
    SchoolResponseDTO update(Long code, SchoolUpdateRequestDTO schoolDTO);

    /**
     * Retrieves a list of all schools.
     *
     * @return a list of SchoolResponseDTO objects
     */
    List<SchoolResponseDTO> getAll();
}

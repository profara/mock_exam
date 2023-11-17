package rs.ac.bg.fon.silab.mock_exam.domain.typeofschool.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import rs.ac.bg.fon.silab.mock_exam.domain.typeofschool.dto.TypeOfSchoolRequestDTO;
import rs.ac.bg.fon.silab.mock_exam.domain.typeofschool.dto.TypeOfSchoolResponseDTO;
import rs.ac.bg.fon.silab.mock_exam.domain.typeofschool.entity.TypeOfSchool;

/**
 * Service interface for managing types of schools.
 * Provides functionalities such as retrieving, creating, updating, and deleting types of schools.
 */
public interface TypeOfSchoolService {

    /**
     * Finds a type of school by its name.
     *
     * @param name the name of the type of school to find
     * @return the found TypeOfSchool entity
     * @throws rs.ac.bg.fon.silab.mock_exam.infrastructure.exception.EntityNotFoundException if the TypeOfSchool is not found
     */
    TypeOfSchool findByName(String name);

    /**
     * Saves a new type of school or updates an existing one based on the provided DTO.
     *
     * @param typeOfSchoolDTO the DTO containing type of school details
     * @return the saved or updated type of school as a DTO
     */
    TypeOfSchoolResponseDTO save(TypeOfSchoolRequestDTO typeOfSchoolDTO);

    /**
     * Retrieves a type of school by its ID.
     *
     * @param id the ID of the type of school to retrieve
     * @return the found TypeOfSchool entity as a DTO
     * @throws rs.ac.bg.fon.silab.mock_exam.infrastructure.exception.EntityNotFoundException if the TypeOfSchool is not found
     */
    TypeOfSchoolResponseDTO getById(Long id);

    /**
     * Retrieves a paginated list of types of schools.
     *
     * @param pageable the pagination information
     * @return a page of TypeOfSchoolResponseDTO objects
     */
    Page<TypeOfSchoolResponseDTO> get(Pageable pageable);

    /**
     * Deletes a type of school by its ID.
     *
     * @param id the ID of the type of school to delete
     * @throws rs.ac.bg.fon.silab.mock_exam.infrastructure.exception.EntityNotFoundException if TypeOfSchool with entered ID doesn't exist
     */
    void delete(Long id);

    /**
     * Updates an existing type of school with new details provided in the DTO.
     *
     * @param id the ID of the type of school to update
     * @param typeOfSchoolDTO the DTO containing updated details
     * @return the updated type of school as a DTO
     * @throws rs.ac.bg.fon.silab.mock_exam.infrastructure.exception.EntityNotFoundException if TypeOfSchool with entered ID doesn't exist
     */
    TypeOfSchoolResponseDTO update(Long id, TypeOfSchoolRequestDTO typeOfSchoolDTO);
}

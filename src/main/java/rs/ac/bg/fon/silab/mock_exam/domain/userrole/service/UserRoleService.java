package rs.ac.bg.fon.silab.mock_exam.domain.userrole.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import rs.ac.bg.fon.silab.mock_exam.domain.userrole.dto.UserRoleRequestDTO;
import rs.ac.bg.fon.silab.mock_exam.domain.userrole.dto.UserRoleResponseDTO;
import rs.ac.bg.fon.silab.mock_exam.domain.userrole.entity.UserRole;

/**
 * Service interface for managing user roles.
 * Provides functionality to retrieve, create, update, and delete user roles.
 */
public interface UserRoleService {

    /**
     * Finds a user role by its name.
     *
     * @param name the name of the user role
     * @return the user role entity if found
     */
    UserRole findByName(String name);

    /**
     * Saves a new user role or updates an existing one based on the provided DTO.
     *
     * @param userRoleDTO the DTO containing user role details
     * @return the saved or updated user role as a DTO
     */
    UserRoleResponseDTO save(UserRoleRequestDTO userRoleDTO);

    /**
     * Retrieves a user role by its ID.
     *
     * @param id the ID of the user role
     * @return the user role as a DTO
     * @throws rs.ac.bg.fon.silab.mock_exam.infrastructure.exception.EntityNotFoundException if the user role is not found
     */
    UserRoleResponseDTO getById(Long id);

    /**
     * Retrieves a paginated list of user roles.
     *
     * @param pageable the pagination information
     * @return a page of UserRoleResponseDTO objects
     */
    Page<UserRoleResponseDTO> get(Pageable pageable);

    /**
     * Deletes a user role by its ID.
     *
     * @param id the ID of the user role to delete
     * @throws rs.ac.bg.fon.silab.mock_exam.infrastructure.exception.EntityNotFoundException if the user role with the given ID doesn't exist
     */
    void delete(Long id);

    /**
     * Updates an existing user role with new details provided in the DTO.
     *
     * @param id the ID of the user role to update
     * @param userRoleDTO the DTO containing updated details
     * @return the updated user role as a DTO
     * @throws rs.ac.bg.fon.silab.mock_exam.infrastructure.exception.EntityNotFoundException if the user role with the given ID doesn't exist
     */
    UserRoleResponseDTO update(Long id, UserRoleRequestDTO userRoleDTO);
}

package rs.ac.bg.fon.silab.mock_exam.domain.userprofile.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import rs.ac.bg.fon.silab.mock_exam.domain.userprofile.dto.RegistrationResponseDTO;
import rs.ac.bg.fon.silab.mock_exam.domain.userprofile.dto.UserProfileResponseDTO;
import rs.ac.bg.fon.silab.mock_exam.domain.userprofile.dto.UserProfileUpdateRoleRequestDTO;
import rs.ac.bg.fon.silab.mock_exam.domain.userprofile.dto.UserProfileRequestUpdateDTO;
import rs.ac.bg.fon.silab.mock_exam.domain.userprofile.entity.UserProfile;

/**
 * Service interface for managing user profiles.
 * Offers functionalities such as retrieving, creating, updating, and enabling user profiles.
 */
public interface UserProfileService {

    /**
     * Retrieves a user profile by its email.
     *
     * @param email the email of the user profile to retrieve
     * @return the found UserProfile entity
     * @throws rs.ac.bg.fon.silab.mock_exam.infrastructure.exception.EntityNotFoundException if the UserProfile is not found
     */
    UserProfile findByEmail(String email);

    /**
     * Registers a new user profile or enables an existing one based on the provided DTO.
     *
     * @param userProfileDTO the DTO containing user profile registration details
     * @return the registration response with a token for email confirmation
     * @throws rs.ac.bg.fon.silab.mock_exam.domain.userprofile.exception.DuplicateUserException if user with entered email already exists
     */
    RegistrationResponseDTO save(UserProfileRequestUpdateDTO userProfileDTO);

    /**
     * Retrieves a user profile by its ID and returns it as a DTO.
     *
     * @param id the ID of the user profile to find
     * @return the user profile as a DTO
     * @throws rs.ac.bg.fon.silab.mock_exam.infrastructure.exception.EntityNotFoundException if the user profile is not found
     */
    UserProfileResponseDTO getById(Long id);

    /**
     * Retrieves a paginated list of user profiles.
     *
     * @param pageable the pagination information
     * @return a page of UserProfileResponseDTO objects
     */
    Page<UserProfileResponseDTO> get(Pageable pageable);

    /**
     * Deletes a user profile by its ID.
     *
     * @param id the ID of the user profile to delete
     * @throws rs.ac.bg.fon.silab.mock_exam.infrastructure.exception.EntityNotFoundException if UserProfile with entered ID doesn't exist
     */
    void delete(Long id);

    /**
     * Updates the role of an existing user profile with details provided in the DTO.
     *
     * @param id the ID of the user profile to update
     * @param userProfileDTO the DTO containing updated role details
     * @return the updated user profile as a DTO
     * @throws rs.ac.bg.fon.silab.mock_exam.infrastructure.exception.EntityNotFoundException if UserProfile with entered ID doesn't exist
     */
    UserProfileResponseDTO updateUserRole(Long id, UserProfileUpdateRoleRequestDTO userProfileDTO);

    /**
     * Updates an existing user profile with details provided in the DTO.
     *
     * @param id the ID of the user profile to update
     * @param userProfileDTO the DTO containing updated user profile details
     * @return the updated user profile as a DTO
     * @throws rs.ac.bg.fon.silab.mock_exam.infrastructure.exception.EntityNotFoundException if UserProfile with entered ID doesn't exist
     */
    UserProfileResponseDTO update(Long id, UserProfileRequestUpdateDTO userProfileDTO);

    /**
     * Retrieves a user profile by its email and returns it as a DTO.
     *
     * @param email the email of the user profile to find
     * @return the user profile as a DTO
     * @throws rs.ac.bg.fon.silab.mock_exam.infrastructure.exception.EntityNotFoundException if the user profile is not found
     */
    UserProfileResponseDTO getByEmail(String email);

    /**
     * Enables a user profile based on a provided token.
     *
     * @param token the token for confirming the user profile
     * @return the enabled user profile as a DTO
     */
    UserProfileResponseDTO enableProfile(String token);
}

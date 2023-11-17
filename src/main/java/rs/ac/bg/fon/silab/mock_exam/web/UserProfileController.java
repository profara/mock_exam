package rs.ac.bg.fon.silab.mock_exam.web;

import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rs.ac.bg.fon.silab.mock_exam.domain.userprofile.dto.RegistrationResponseDTO;
import rs.ac.bg.fon.silab.mock_exam.domain.userprofile.dto.UserProfileRequestUpdateDTO;
import rs.ac.bg.fon.silab.mock_exam.domain.userprofile.dto.UserProfileResponseDTO;
import rs.ac.bg.fon.silab.mock_exam.domain.userprofile.dto.UserProfileUpdateRoleRequestDTO;
import rs.ac.bg.fon.silab.mock_exam.domain.userprofile.service.UserProfileService;


/**
 * REST controller for managing user profile operations.
 * Provides endpoints for creating, retrieving, updating, deleting, and confirming user profiles.
 */
@RestController
@RequestMapping("/api/userProfiles")
public class UserProfileController {

    private final UserProfileService userProfileService;

    /**
     * Constructs a UserProfileController with necessary UserProfileService and JWTUtil dependencies.
     *
     * @param userProfileService The service handling user profile operations.
     */
    public UserProfileController(UserProfileService userProfileService) {
        this.userProfileService = userProfileService;
    }

    /**
     * Endpoint to save a new user profile.
     *
     * @param userProfileDTO DTO containing the user profile details.
     * @return ResponseEntity with the JWT token in the header.
     */
    @PostMapping
    public ResponseEntity<?> save(@Valid @RequestBody UserProfileRequestUpdateDTO userProfileDTO){
        RegistrationResponseDTO registrationResponseDTO = userProfileService.save(userProfileDTO);
        String jwtToken = registrationResponseDTO.jwtToken();

        return ResponseEntity.ok()
                .header(HttpHeaders.AUTHORIZATION, jwtToken)
                .build();
    }


    /**
     * Endpoint to retrieve a user profile by its ID.
     *
     * @param id The ID of the user profile to retrieve.
     * @return ResponseEntity with OK status and the found UserProfileResponseDTO.
     */
    @GetMapping("/{id}")
    public ResponseEntity<UserProfileResponseDTO> getById(@PathVariable Long id){
        return ResponseEntity.ok(userProfileService.getById(id));
    }

    /**
     * Endpoint to retrieve a paginated list of user profiles.
     *
     * @param pageable Pagination information.
     * @return ResponseEntity with OK status and a page of UserProfileResponseDTOs.
     */
    @GetMapping
    public ResponseEntity<Page<UserProfileResponseDTO>> get(Pageable pageable){
        return ResponseEntity.ok(userProfileService.get(pageable));
    }

    /**
     * Endpoint to retrieve a user profile by email.
     *
     * @param email The email of the user profile to retrieve.
     * @return ResponseEntity with OK status and the found UserProfileResponseDTO.
     */
    @GetMapping(params = "email")
    public ResponseEntity<UserProfileResponseDTO> getByEmail(@RequestParam String email){
        return ResponseEntity.ok(userProfileService.getByEmail(email));
    }

    /**
     * Endpoint to confirm a user profile based on the provided token.
     *
     * @param token The token used for confirmation.
     * @return ResponseEntity with OK status and the confirmed UserProfileResponseDTO.
     */
    @GetMapping("/confirm")
    public ResponseEntity<UserProfileResponseDTO> confirm(@RequestParam("token") String token){
        return ResponseEntity.ok(userProfileService.enableProfile(token));
    }

    /**
     * Endpoint to delete a user profile by its ID.
     *
     * @param id The ID of the user profile to delete.
     */
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id){
        userProfileService.delete(id);
    }

    /**
     * Endpoint to update the role of a user profile.
     *
     * @param id The ID of the user profile to update.
     * @param userProfileDTO DTO containing the new role information.
     * @return ResponseEntity with OK status and the updated UserProfileResponseDTO.
     */
    @PatchMapping("/changeRole/{id}")
    public ResponseEntity<UserProfileResponseDTO> updateUserRole(@PathVariable Long id, @Valid @RequestBody UserProfileUpdateRoleRequestDTO userProfileDTO){
        return ResponseEntity.ok(userProfileService.updateUserRole(id,userProfileDTO));
    }

    /**
     * Endpoint to update a user profile.
     *
     * @param id The ID of the user profile to update.
     * @param userProfileDTO DTO containing the updated user profile details.
     * @return ResponseEntity with OK status and the updated UserProfileResponseDTO.
     */
    @PatchMapping("/{id}")
    public ResponseEntity<UserProfileResponseDTO> update(@PathVariable Long id, @Valid @RequestBody UserProfileRequestUpdateDTO userProfileDTO){
        return ResponseEntity.ok(userProfileService.update(id,userProfileDTO));
    }


}

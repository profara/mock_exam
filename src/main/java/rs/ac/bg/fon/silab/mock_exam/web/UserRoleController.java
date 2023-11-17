package rs.ac.bg.fon.silab.mock_exam.web;

import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rs.ac.bg.fon.silab.mock_exam.domain.userrole.dto.UserRoleRequestDTO;
import rs.ac.bg.fon.silab.mock_exam.domain.userrole.dto.UserRoleResponseDTO;
import rs.ac.bg.fon.silab.mock_exam.domain.userrole.service.UserRoleService;


/**
 * REST controller for managing user role operations.
 * Provides endpoints for creating, retrieving, updating, and deleting user roles.
 */
@RestController
@RequestMapping("/api/userRoles")
public class UserRoleController {

    private final UserRoleService userRoleService;

    /**
     * Constructs a UserRoleController with the necessary UserRoleService dependency.
     *
     * @param userRoleService The service that will handle the business logic for user roles.
     */
    public UserRoleController(UserRoleService userRoleService) {
        this.userRoleService = userRoleService;
    }

    /**
     * Endpoint to save a new user role.
     *
     * @param userRoleDTO DTO containing the details of the user role to be saved.
     * @return ResponseEntity with created status and the saved UserRoleResponseDTO.
     */
    @PostMapping
    public ResponseEntity<UserRoleResponseDTO> save(@Valid @RequestBody UserRoleRequestDTO userRoleDTO){
        return ResponseEntity.status(HttpStatus.CREATED).body(userRoleService.save(userRoleDTO));
    }

    /**
     * Endpoint to retrieve a user role by its ID.
     *
     * @param id The ID of the user role to retrieve.
     * @return ResponseEntity with OK status and the found UserRoleResponseDTO.
     */
    @GetMapping("/{id}")
    public ResponseEntity<UserRoleResponseDTO> getById(@PathVariable Long id){
        return ResponseEntity.ok(userRoleService.getById(id));
    }

    /**
     * Endpoint to retrieve a paginated list of user roles.
     *
     * @param pageable Pagination information.
     * @return ResponseEntity with OK status and a page of UserRoleResponseDTOs.
     */
    @GetMapping
    public ResponseEntity<Page<UserRoleResponseDTO>> get(Pageable pageable){
        return ResponseEntity.ok(userRoleService.get(pageable));
    }

    /**
     * Endpoint to delete a user role by its ID.
     *
     * @param id The ID of the user role to delete.
     */
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id){
        userRoleService.delete(id);
    }

    /**
     * Endpoint to update an existing user role with new details.
     *
     * @param id The ID of the user role to update.
     * @param userRoleDTO DTO containing updated user role details.
     * @return ResponseEntity with OK status and the updated UserRoleResponseDTO.
     */
    @PutMapping("/{id}")
    public ResponseEntity<UserRoleResponseDTO> update(@PathVariable Long id,@Valid @RequestBody UserRoleRequestDTO userRoleDTO){
        return ResponseEntity.ok(userRoleService.update(id, userRoleDTO));
    }
}

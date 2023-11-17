package rs.ac.bg.fon.silab.mock_exam.web;

import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rs.ac.bg.fon.silab.mock_exam.domain.application.dto.ApplicationRequestDTO;
import rs.ac.bg.fon.silab.mock_exam.domain.application.dto.ApplicationResponseDTO;
import rs.ac.bg.fon.silab.mock_exam.domain.application.service.ApplicationService;

/**
 * REST controller for managing application-related operations.
 * Provides endpoints for creating, retrieving, and deleting applications.
 */
@RestController
@RequestMapping("/api/applications")
public class ApplicationController {

    private final ApplicationService applicationService;

    /**
     * Constructs an ApplicationController with the necessary ApplicationService dependency.
     *
     * @param applicationService The service that will handle the business logic for applications.
     */
    public ApplicationController(ApplicationService applicationService) {
        this.applicationService = applicationService;
    }

    /**
     * Endpoint to save a new application.
     *
     * @param applicationDTO DTO containing the details of the application to be saved.
     * @return ResponseEntity with created status and the saved ApplicationResponseDTO.
     */
    @PostMapping
    public ResponseEntity<ApplicationResponseDTO> save(@Valid @RequestBody ApplicationRequestDTO applicationDTO){
        return ResponseEntity.status(HttpStatus.CREATED).body(applicationService.save(applicationDTO));
    }

    /**
     * Endpoint to retrieve an application by its ID.
     *
     * @param id The ID of the application to retrieve.
     * @return ResponseEntity with OK status and the found ApplicationResponseDTO.
     */
    @GetMapping("/{id}")
    public ResponseEntity<ApplicationResponseDTO> getById(@PathVariable Long id){
        return ResponseEntity.ok(applicationService.getById(id));
    }

    /**
     * Endpoint to retrieve a paginated list of applications.
     *
     * @param pageable Pagination information.
     * @return ResponseEntity with OK status and a page of ApplicationResponseDTOs.
     */
    @GetMapping
    public ResponseEntity<Page<ApplicationResponseDTO>> get(Pageable pageable){
        return ResponseEntity.ok(applicationService.get(pageable));
    }

    /**
     * Endpoint to delete an application by its ID.
     *
     * @param id The ID of the application to delete.
     */
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id){
        applicationService.delete(id);
    }

    /**
     * Endpoint to delete an appointment for a specific candidate.
     *
     * @param candidateId ID of the candidate.
     * @param appointmentId ID of the appointment to be deleted.
     */
    @DeleteMapping("/by-candidate/{candidateId}/appointments/{appointmentId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteAppointment(@PathVariable Long candidateId,@PathVariable Long appointmentId){
        applicationService.deleteAppointment(candidateId, appointmentId);
    }

    /**
     * Endpoint to create an application with a specific appointment for a candidate.
     *
     * @param candidateId ID of the candidate.
     * @param appointmentId ID of the appointment to be associated with the application.
     * @return ResponseEntity with created status and the created ApplicationResponseDTO.
     */
    @PostMapping("/by-candidate/{candidateId}/appointments/{appointmentId}")
    public ResponseEntity<ApplicationResponseDTO> createApplicationWithAppointment(
            @PathVariable Long candidateId,
            @PathVariable Long appointmentId){
        return ResponseEntity.status(HttpStatus.CREATED).body(applicationService.createApplicationWithAppointment(candidateId, appointmentId));
    }


}

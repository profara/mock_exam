package rs.ac.bg.fon.silab.mock_exam.web;

import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import rs.ac.bg.fon.silab.mock_exam.domain.candidate.dto.CandidateRequestDTO;
import rs.ac.bg.fon.silab.mock_exam.domain.candidate.dto.CandidateResponseDTO;
import rs.ac.bg.fon.silab.mock_exam.domain.candidate.service.CandidateService;

import java.util.List;

/**
 * REST controller for managing candidate-related operations.
 * Provides endpoints for creating, retrieving, updating, and deleting candidates, as well as specific filters for candidate retrieval.
 */
@RestController
@RequestMapping("/api/candidates")
public class CandidateController {

    private final CandidateService candidateService;

    /**
     * Constructs a CandidateController with necessary CandidateService dependency.
     *
     * @param candidateService The service that will handle the business logic for candidates.
     */
    public CandidateController(CandidateService candidateService) {
        this.candidateService = candidateService;
    }

    /**
     * Endpoint to save a new candidate.
     *
     * @param candidateDTO DTO containing details of the candidate to be saved.
     * @return ResponseEntity with created status and the saved CandidateResponseDTO.
     */
    @PostMapping
    public ResponseEntity<CandidateResponseDTO> save(@Valid @RequestBody CandidateRequestDTO candidateDTO){
        return ResponseEntity.status(HttpStatus.CREATED).body(candidateService.save(candidateDTO));
    }

    /**
     * Endpoint to retrieve a candidate by their ID.
     *
     * @param id The ID of the candidate to retrieve.
     * @return ResponseEntity with OK status and the found CandidateResponseDTO.
     */
    @GetMapping("/{id}")
    public ResponseEntity<CandidateResponseDTO> getById(@PathVariable Long id){
        return ResponseEntity.ok(candidateService.getById(id));
    }

    /**
     * Endpoint to retrieve a candidate by their email.
     *
     * @param email The email of the candidate to retrieve.
     * @return ResponseEntity with OK status and the found CandidateResponseDTO.
     */
    @GetMapping(params = "email")
    public ResponseEntity<CandidateResponseDTO> getByEmail(@RequestParam String email){
        return ResponseEntity.ok(candidateService.getByEmail(email));
    }

    /**
     * Endpoint to retrieve a paginated list of candidates. Requires admin role.
     *
     * @param pageable Pagination information.
     * @return ResponseEntity with OK status and a page of CandidateResponseDTOs.
     */
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping
    public ResponseEntity<Page<CandidateResponseDTO>> get(Pageable pageable){
        return ResponseEntity.ok(candidateService.get(pageable));
    }

    /**
     * Endpoint to delete a candidate by their ID. Requires admin role.
     *
     * @param id The ID of the candidate to delete.
     */
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id){
        candidateService.delete(id);
    }

    /**
     * Endpoint to update an existing candidate.
     *
     * @param id The ID of the candidate to update.
     * @param candidateDTO DTO containing updated candidate details.
     * @return ResponseEntity with OK status and the updated CandidateResponseDTO.
     */
    @PatchMapping("/{id}")
    public ResponseEntity<CandidateResponseDTO> update(@PathVariable Long id,
                                                       @Valid @RequestBody CandidateRequestDTO candidateDTO){
        return ResponseEntity.ok(candidateService.update(id,candidateDTO));
    }

    /**
     * Endpoint to retrieve candidates by appointment ID. Access restricted to admin role.
     *
     * @param appointmentId The ID of the appointment.
     * @param zipCode Optional filter by candidate's zip code.
     * @param schoolCode Optional filter by candidate's school code.
     * @param attendedPreparation Optional filter by candidates who attended preparation.
     * @param pageable Pagination information.
     * @return ResponseEntity containing a paginated list of CandidateResponseDTOs.
     */
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/by-appointment/{appointmentId}")
    public ResponseEntity<Page<CandidateResponseDTO>> getByAppointmentId(
            @PathVariable Long appointmentId,
            @RequestParam(required = false) Long zipCode,
            @RequestParam(required = false) Long schoolCode,
            @RequestParam(required = false) Boolean attendedPreparation,
            Pageable pageable){
        return ResponseEntity.ok(candidateService.getByAppointmentId(appointmentId,zipCode,schoolCode,attendedPreparation,pageable));
    }

    /**
     * Endpoint for filtering candidates by various criteria based on a specific appointment ID. Access restricted to admin role.
     *
     * @param appointmentId The ID of the appointment for filtering.
     * @param zipCode Optional filter by candidate's zip code.
     * @param schoolCode Optional filter by candidate's school code.
     * @param attendedPreparation Optional filter by candidates who attended preparation.
     * @param pageable Pagination information.
     * @return ResponseEntity containing a paginated list of CandidateResponseDTOs.
     */
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/by-appointment/{appointmentId}/filter")
    public ResponseEntity<Page<CandidateResponseDTO>> filterByAppointmentId(
            @PathVariable Long appointmentId,
            @RequestParam(required = false) Long zipCode,
            @RequestParam(required = false) Long schoolCode,
            @RequestParam(required = false) Boolean attendedPreparation,
            Pageable pageable){
        return ResponseEntity.ok(candidateService.filterByAppointmentId(appointmentId,zipCode,schoolCode,attendedPreparation,pageable));
    }

    /**
     * Endpoint to retrieve all candidates associated with a given appointment ID. Access restricted to admin role.
     *
     * @param appointmentId The ID of the appointment.
     * @param zipCode Optional filter by candidate's zip code.
     * @param schoolCode Optional filter by candidate's school code.
     * @param attendedPreparation Optional filter by candidates who attended preparation.
     * @return ResponseEntity containing a list of CandidateResponseDTOs.
     */
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/by-appointment/{appointmentId}/all")
    public ResponseEntity<List<CandidateResponseDTO>> getAllByAppointmentId(
            @PathVariable Long appointmentId,
            @RequestParam(required = false) Long zipCode,
            @RequestParam(required = false) Long schoolCode,
            @RequestParam(required = false) Boolean attendedPreparation){
        return ResponseEntity.ok(candidateService.getAllByAppointmentId(appointmentId, zipCode, schoolCode, attendedPreparation));
    }

    /**
     * Endpoint for filtering candidates based on various criteria. Access restricted to admin role.
     *
     * @param zipCode Optional filter by candidate's zip code.
     * @param schoolCode Optional filter by candidate's school code.
     * @param attendedPreparation Optional filter by candidates who attended preparation.
     * @param pageable Pagination information.
     * @return ResponseEntity containing a paginated list of CandidateResponseDTOs.
     */
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/filter")
    public ResponseEntity<Page<CandidateResponseDTO>> filterCandidates(
            @RequestParam(required = false) Long zipCode,
            @RequestParam(required = false) Long schoolCode,
            @RequestParam(required = false) Boolean attendedPreparation,
            Pageable pageable) {

        return ResponseEntity.ok(candidateService.filterCandidates(zipCode, schoolCode, attendedPreparation, pageable));
    }

}

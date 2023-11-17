package rs.ac.bg.fon.silab.mock_exam.web;

import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import rs.ac.bg.fon.silab.mock_exam.domain.appointment.dto.AppointmentRequestDTO;
import rs.ac.bg.fon.silab.mock_exam.domain.appointment.dto.AppointmentResponseDTO;
import rs.ac.bg.fon.silab.mock_exam.domain.appointment.service.AppointmentService;

import java.util.List;

/**
 * REST controller for managing appointment-related operations.
 * Provides endpoints for creating, retrieving, updating, and deleting appointments, including specific functionalities for candidates.
 */
@RestController
@RequestMapping("/api/appointments")
public class AppointmentController {

    private final AppointmentService appointmentService;

    /**
     * Constructs an AppointmentController with the necessary AppointmentService dependency.
     *
     * @param appointmentService The service that will handle the business logic for appointments.
     */
    public AppointmentController(AppointmentService appointmentService) {
        this.appointmentService = appointmentService;
    }

    /**
     * Endpoint to save a new appointment. Requires admin role.
     *
     * @param appointmentRequestDTO DTO containing the details of the appointment to be saved.
     * @return ResponseEntity with created status and the saved AppointmentResponseDTO.
     */
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping
    public ResponseEntity<AppointmentResponseDTO> save(@Valid @RequestBody AppointmentRequestDTO appointmentRequestDTO){
        return ResponseEntity.status(HttpStatus.CREATED).body(appointmentService.save(appointmentRequestDTO));
    }

    /**
     * Endpoint to retrieve an appointment by its ID.
     *
     * @param id The ID of the appointment to retrieve.
     * @return ResponseEntity with OK status and the found AppointmentResponseDTO.
     */
    @GetMapping("/{id}")
    public ResponseEntity<AppointmentResponseDTO> getById(@PathVariable Long id){
        return ResponseEntity.ok(appointmentService.findById(id));
    }

    /**
     * Endpoint to retrieve a paginated list of appointments.
     *
     * @param pageable Pagination information.
     * @return ResponseEntity with OK status and a page of AppointmentResponseDTOs.
     */
    @GetMapping
    public ResponseEntity<Page<AppointmentResponseDTO>> get(Pageable pageable){
        return ResponseEntity.ok(appointmentService.get(pageable));
    }

    /**
     * Endpoint to delete an appointment by its ID. Requires admin role.
     *
     * @param id The ID of the appointment to delete.
     */
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id){
        appointmentService.delete(id);
    }

    /**
     * Endpoint to update an existing appointment. Requires admin role.
     *
     * @param id The ID of the appointment to update.
     * @param appointmentRequestDTO DTO containing updated appointment details.
     * @return ResponseEntity with OK status and the updated AppointmentResponseDTO.
     */
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<AppointmentResponseDTO> update(@PathVariable Long id,
                                                         @Valid @RequestBody AppointmentRequestDTO appointmentRequestDTO){
        return ResponseEntity.ok(appointmentService.update(id,appointmentRequestDTO));
    }

    /**
     * Endpoint to retrieve a paginated list of appointments associated with a specific candidate.
     *
     * @param candidateId ID of the candidate.
     * @param pageable Pagination information.
     * @return ResponseEntity with OK status and a page of AppointmentResponseDTOs.
     */
    @GetMapping("/by-candidate/{candidateId}")
    public ResponseEntity<Page<AppointmentResponseDTO>> getByCandidateId(@PathVariable Long candidateId, Pageable pageable){
        return ResponseEntity.ok(appointmentService.getByCandidateId(candidateId, pageable));
    }

    /**
     * Endpoint to retrieve a sorted list of all appointments.
     *
     * @return ResponseEntity with OK status and a list of sorted AppointmentResponseDTOs.
     */
    @GetMapping("/all-sorted")
    public ResponseEntity<List<AppointmentResponseDTO>> getAllSorted(){
        return ResponseEntity.ok(appointmentService.getAllSorted());
    }

    /**
     * Endpoint to retrieve a paginated list of appointments that a specific candidate has not signed up for.
     *
     * @param candidateId ID of the candidate.
     * @param pageable Pagination information.
     * @return ResponseEntity with OK status and a page of AppointmentResponseDTOs.
     */
    @GetMapping("/by-candidate/{candidateId}/not-signed")
    public ResponseEntity<Page<AppointmentResponseDTO>> getByCandidateIdNotSigned(@PathVariable Long candidateId, Pageable pageable){
        return ResponseEntity.ok(appointmentService.getByCandidateIdNotSigned(candidateId, pageable));
    }
}

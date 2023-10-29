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
import rs.ac.bg.fon.silab.mock_exam.domain.candidate.dto.CandidateResponseDTO;

import java.util.List;

@RestController
@RequestMapping("/api/appointments")
public class AppointmentController {

    private final AppointmentService appointmentService;

    public AppointmentController(AppointmentService appointmentService) {
        this.appointmentService = appointmentService;
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping
    public ResponseEntity<AppointmentResponseDTO> save(@Valid @RequestBody AppointmentRequestDTO appointmentRequestDTO){
        return ResponseEntity.status(HttpStatus.CREATED).body(appointmentService.save(appointmentRequestDTO));
    }

    @GetMapping("/{id}")
    public ResponseEntity<AppointmentResponseDTO> getById(@PathVariable Long id){
        return ResponseEntity.ok(appointmentService.findById(id));
    }

    @GetMapping
    public ResponseEntity<Page<AppointmentResponseDTO>> get(Pageable pageable){
        return ResponseEntity.ok(appointmentService.get(pageable));
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id){
        appointmentService.delete(id);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<AppointmentResponseDTO> update(@PathVariable Long id,
                                                         @Valid @RequestBody AppointmentRequestDTO appointmentRequestDTO){
        return ResponseEntity.ok(appointmentService.update(id,appointmentRequestDTO));
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/{id}/candidates")
    public ResponseEntity<Page<CandidateResponseDTO>> getCandidates(@PathVariable Long id, Pageable pageable){
        return ResponseEntity.ok(appointmentService.getCandidates(id,pageable));
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/{id}/candidates/all")
    public ResponseEntity<List<CandidateResponseDTO>> getCandidates(@PathVariable Long id){
        return ResponseEntity.ok(appointmentService.getAllCandidates(id));
    }
}

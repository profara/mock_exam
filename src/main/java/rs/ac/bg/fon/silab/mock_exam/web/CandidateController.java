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


@RestController
@RequestMapping("/api/candidates")
public class CandidateController {

    private final CandidateService candidateService;

    public CandidateController(CandidateService candidateService) {
        this.candidateService = candidateService;
    }

    @PostMapping
    public ResponseEntity<CandidateResponseDTO> save(@Valid @RequestBody CandidateRequestDTO candidateDTO){
        return ResponseEntity.status(HttpStatus.CREATED).body(candidateService.save(candidateDTO));
    }

    @GetMapping("/{id}")
    public ResponseEntity<CandidateResponseDTO> getById(@PathVariable Long id){
        return ResponseEntity.ok(candidateService.getById(id));
    }

    @GetMapping(params = "email")
    public ResponseEntity<CandidateResponseDTO> getByEmail(@RequestParam String email){
        return ResponseEntity.ok(candidateService.getByEmail(email));
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping
    public ResponseEntity<Page<CandidateResponseDTO>> get(Pageable pageable){
        return ResponseEntity.ok(candidateService.get(pageable));
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id){
        candidateService.delete(id);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<CandidateResponseDTO> update(@PathVariable Long id,
                                                       @Valid @RequestBody CandidateRequestDTO candidateDTO){
        return ResponseEntity.ok(candidateService.update(id,candidateDTO));
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/by-appointment/{appointmentId}")
    public ResponseEntity<Page<CandidateResponseDTO>> getByAppointmentId(@PathVariable Long appointmentId, Pageable pageable){
        return ResponseEntity.ok(candidateService.getByAppointmentId(appointmentId,pageable));
    }

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

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/by-appointment/{appointmentId}/all")
    public ResponseEntity<List<CandidateResponseDTO>> getAllByAppointmentId(@PathVariable Long appointmentId){
        return ResponseEntity.ok(candidateService.getAllByAppointmentId(appointmentId));
    }

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

package rs.ac.bg.fon.silab.mock_exam.web;

import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rs.ac.bg.fon.silab.mock_exam.domain.candidate.dto.CandidateRequestDTO;
import rs.ac.bg.fon.silab.mock_exam.domain.candidate.dto.CandidateResponseDTO;
import rs.ac.bg.fon.silab.mock_exam.domain.candidate.dto.CandidateUpdateRequestDTO;
import rs.ac.bg.fon.silab.mock_exam.domain.candidate.service.CandidateService;

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

    @GetMapping
    public ResponseEntity<Page<CandidateResponseDTO>> get(Pageable pageable){
        return ResponseEntity.ok(candidateService.get(pageable));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id){
        candidateService.delete(id);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<CandidateResponseDTO> update(@PathVariable Long id,
                                                       @Valid @RequestBody CandidateUpdateRequestDTO candidateDTO){
        return ResponseEntity.ok(candidateService.update(id,candidateDTO));
    }
}

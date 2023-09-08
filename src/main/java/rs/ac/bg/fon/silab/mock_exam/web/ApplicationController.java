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

@RestController
@RequestMapping("/api/applications")
public class ApplicationController {

    private final ApplicationService applicationService;

    public ApplicationController(ApplicationService applicationService) {
        this.applicationService = applicationService;
    }

    @PostMapping
    public ResponseEntity<ApplicationResponseDTO> save(@Valid @RequestBody ApplicationRequestDTO applicationDTO){
        return ResponseEntity.status(HttpStatus.CREATED).body(applicationService.save(applicationDTO));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApplicationResponseDTO> getById(@PathVariable Long id){
        return ResponseEntity.ok(applicationService.getById(id));
    }

    @GetMapping
    public ResponseEntity<Page<ApplicationResponseDTO>> get(Pageable pageable){
        return ResponseEntity.ok(applicationService.get(pageable));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id){
        applicationService.delete(id);
    }
}

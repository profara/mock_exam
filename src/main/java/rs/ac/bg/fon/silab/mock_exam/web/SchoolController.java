package rs.ac.bg.fon.silab.mock_exam.web;

import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rs.ac.bg.fon.silab.mock_exam.domain.school.dto.SchoolRequestDTO;
import rs.ac.bg.fon.silab.mock_exam.domain.school.dto.SchoolResponseDTO;
import rs.ac.bg.fon.silab.mock_exam.domain.school.dto.SchoolUpdateRequestDTO;
import rs.ac.bg.fon.silab.mock_exam.domain.school.service.SchoolService;

@RestController
@RequestMapping("/api/schools")
public class SchoolController {

    private final SchoolService schoolService;

    public SchoolController(SchoolService schoolService) {
        this.schoolService = schoolService;
    }

    @PostMapping
    public ResponseEntity<SchoolResponseDTO> save(@Valid @RequestBody SchoolRequestDTO schoolDTO){
        return ResponseEntity.status(HttpStatus.CREATED).body(schoolService.save(schoolDTO));
    }

    @GetMapping("/{code}")
    public ResponseEntity<SchoolResponseDTO> getById(@PathVariable Long code){
        return ResponseEntity.ok(schoolService.getById(code));
    }

    @GetMapping
    public ResponseEntity<Page<SchoolResponseDTO>> get(Pageable pageable){
        return ResponseEntity.ok(schoolService.get(pageable));
    }

    @DeleteMapping("/{code}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long code){
        schoolService.delete(code);
    }

    @PatchMapping("/{code}")
    public ResponseEntity<SchoolResponseDTO> update(@PathVariable Long code,@Valid @RequestBody SchoolUpdateRequestDTO schoolDTO){
        return ResponseEntity.ok(schoolService.update(code,schoolDTO));
    }


}

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

import java.util.List;

/**
 * REST controller for managing school-related operations.
 * Provides endpoints for creating, retrieving, updating, and deleting schools.
 */
@RestController
@RequestMapping("/api/schools")
public class SchoolController {

    private final SchoolService schoolService;

    /**
     * Constructs a SchoolController with the necessary SchoolService dependency.
     *
     * @param schoolService The service that will handle the business logic for schools.
     */
    public SchoolController(SchoolService schoolService) {
        this.schoolService = schoolService;
    }

    /**
     * Endpoint to save a new school.
     *
     * @param schoolDTO DTO containing the details of the school to be saved.
     * @return ResponseEntity with created status and the saved SchoolResponseDTO.
     */
    @PostMapping
    public ResponseEntity<SchoolResponseDTO> save(@Valid @RequestBody SchoolRequestDTO schoolDTO){
        return ResponseEntity.status(HttpStatus.CREATED).body(schoolService.save(schoolDTO));
    }

    /**
     * Endpoint to retrieve a school by its code.
     *
     * @param code The code of the school to retrieve.
     * @return ResponseEntity with OK status and the found SchoolResponseDTO.
     */
    @GetMapping("/{code}")
    public ResponseEntity<SchoolResponseDTO> getById(@PathVariable Long code){
        return ResponseEntity.ok(schoolService.getById(code));
    }

    /**
     * Endpoint to retrieve a paginated list of schools.
     *
     * @param pageable Pagination information.
     * @return ResponseEntity with OK status and a page of SchoolResponseDTOs.
     */
    @GetMapping
    public ResponseEntity<Page<SchoolResponseDTO>> get(Pageable pageable){
        return ResponseEntity.ok(schoolService.get(pageable));
    }

    /**
     * Endpoint to retrieve all schools.
     *
     * @return ResponseEntity with OK status and a list of all SchoolResponseDTOs.
     */
    @GetMapping("/all")
    public ResponseEntity<List<SchoolResponseDTO>> getAll(){
        return ResponseEntity.ok(schoolService.getAll());
    }

    /**
     * Endpoint to delete a school by its code.
     *
     * @param code The code of the school to delete.
     */
    @DeleteMapping("/{code}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long code){
        schoolService.delete(code);
    }

    /**
     * Endpoint to update a school.
     *
     * @param code The code of the school to be updated.
     * @param schoolDTO DTO containing updated details of the school.
     * @return ResponseEntity with OK status and the updated SchoolResponseDTO.
     */
    @PatchMapping("/{code}")
    public ResponseEntity<SchoolResponseDTO> update(@PathVariable Long code,@Valid @RequestBody SchoolUpdateRequestDTO schoolDTO){
        return ResponseEntity.ok(schoolService.update(code,schoolDTO));
    }


}

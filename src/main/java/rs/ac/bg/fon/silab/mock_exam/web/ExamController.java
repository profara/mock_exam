package rs.ac.bg.fon.silab.mock_exam.web;

import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rs.ac.bg.fon.silab.mock_exam.domain.exam.dto.ExamRequestDTO;
import rs.ac.bg.fon.silab.mock_exam.domain.exam.dto.ExamResponseDTO;
import rs.ac.bg.fon.silab.mock_exam.domain.exam.service.ExamService;

/**
 * REST controller for managing exam-related operations.
 * Provides endpoints for creating, retrieving, updating, and deleting exam records.
 */
@RestController
@RequestMapping("/api/exams")
public class ExamController {

    private final ExamService examService;

    /**
     * Constructs an ExamController with the necessary ExamService dependency.
     *
     * @param examService The service that will handle the business logic for exams.
     */
    public ExamController(ExamService examService) {
        this.examService = examService;
    }

    /**
     * Endpoint to save a new exam.
     *
     * @param examDTO DTO containing the details of the exam to be saved.
     * @return ResponseEntity with created status and the saved ExamResponseDTO.
     */
    @PostMapping
    public ResponseEntity<ExamResponseDTO> save(@Valid @RequestBody ExamRequestDTO examDTO){
        return ResponseEntity.status(HttpStatus.CREATED).body(examService.save(examDTO));
    }

    /**
     * Endpoint to retrieve an exam by its ID.
     *
     * @param id The ID of the exam to retrieve.
     * @return ResponseEntity with OK status and the found ExamResponseDTO.
     */
    @GetMapping("/{id}")
    public ResponseEntity<ExamResponseDTO> getById(@PathVariable Long id){
        return ResponseEntity.ok(examService.getById(id));
    }

    /**
     * Endpoint to retrieve a paginated list of exams.
     *
     * @param pageable Pagination information.
     * @return ResponseEntity with OK status and a page of ExamResponseDTOs.
     */
    @GetMapping
    public ResponseEntity<Page<ExamResponseDTO>> get(Pageable pageable){
        return ResponseEntity.ok(examService.get(pageable));
    }

    /**
     * Endpoint to delete an exam by its ID.
     *
     * @param id The ID of the exam to delete.
     */
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id){
        examService.delete(id);
    }

    /**
     * Endpoint to update an exam.
     *
     * @param id The ID of the exam to be updated.
     * @param examDTO DTO containing updated details of the exam.
     * @return ResponseEntity with OK status and the updated ExamResponseDTO.
     */
    @PutMapping("/{id}")
    public ResponseEntity<ExamResponseDTO> update(@PathVariable Long id, @Valid @RequestBody ExamRequestDTO examDTO){
        return ResponseEntity.ok(examService.update(id,examDTO));
    }


}

package rs.ac.bg.fon.silab.mock_exam.web;

import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rs.ac.bg.fon.silab.mock_exam.domain.typeofschool.dto.TypeOfSchoolRequestDTO;
import rs.ac.bg.fon.silab.mock_exam.domain.typeofschool.dto.TypeOfSchoolResponseDTO;
import rs.ac.bg.fon.silab.mock_exam.domain.typeofschool.service.TypeOfSchoolService;

/**
 * REST controller for managing operations related to types of schools.
 * Offers endpoints for creating, retrieving, updating, and deleting types of schools.
 */
@RestController
@RequestMapping("/api/typeOfSchools")
public class TypeOfSchoolController {

    private final TypeOfSchoolService typeOfSchoolService;

    /**
     * Constructs a TypeOfSchoolController with necessary TypeOfSchoolService dependency.
     * This service handles the business logic related to type of school operations.
     *
     * @param typeOfSchoolService The service that will handle the business logic for types of schools.
     */
    public TypeOfSchoolController(TypeOfSchoolService typeOfSchoolService) {
        this.typeOfSchoolService = typeOfSchoolService;
    }

    /**
     * Endpoint to save a new type of school.
     *
     * @param typeOfSchoolDTO DTO containing the details of the type of school to be saved.
     * @return ResponseEntity with created status and the saved TypeOfSchoolResponseDTO.
     */
    @PostMapping
    public ResponseEntity<TypeOfSchoolResponseDTO> save(@Valid @RequestBody TypeOfSchoolRequestDTO typeOfSchoolDTO){
        return ResponseEntity.status(HttpStatus.CREATED).body(typeOfSchoolService.save(typeOfSchoolDTO));
    }

    /**
     * Endpoint to retrieve a type of school by its ID.
     *
     * @param id The ID of the type of school to retrieve.
     * @return ResponseEntity with OK status and the found TypeOfSchoolResponseDTO.
     */
    @GetMapping("/{id}")
    public ResponseEntity<TypeOfSchoolResponseDTO> getById(@PathVariable Long id){
        return ResponseEntity.ok(typeOfSchoolService.getById(id));
    }

    /**
     * Endpoint to retrieve a paginated list of types of schools.
     *
     * @param pageable Pagination information.
     * @return ResponseEntity with OK status and a page of TypeOfSchoolResponseDTOs.
     */
    @GetMapping
    public ResponseEntity<Page<TypeOfSchoolResponseDTO>> get(Pageable pageable){
        return ResponseEntity.ok(typeOfSchoolService.get(pageable));
    }

    /**
     * Endpoint to delete a type of school by its ID.
     *
     * @param id The ID of the type of school to delete.
     */
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id){
        typeOfSchoolService.delete(id);
    }

    /**
     * Endpoint to update a type of school.
     *
     * @param id The ID of the type of school to be updated.
     * @param typeOfSchoolDTO DTO containing updated details of the type of school.
     * @return ResponseEntity with OK status and the updated TypeOfSchoolResponseDTO.
     */
    @PutMapping("/{id}")
    public ResponseEntity<TypeOfSchoolResponseDTO> update(@PathVariable Long id,@Valid @RequestBody TypeOfSchoolRequestDTO typeOfSchoolDTO){
        return ResponseEntity.ok(typeOfSchoolService.update(id, typeOfSchoolDTO));
    }
}

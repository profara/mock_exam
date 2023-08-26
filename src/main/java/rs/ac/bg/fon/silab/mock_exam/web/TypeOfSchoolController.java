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

@RestController
@RequestMapping("/api/typeOfSchools")
public class TypeOfSchoolController {

    private final TypeOfSchoolService typeOfSchoolService;

    public TypeOfSchoolController(TypeOfSchoolService typeOfSchoolService) {
        this.typeOfSchoolService = typeOfSchoolService;
    }

    @PostMapping
    public ResponseEntity<TypeOfSchoolResponseDTO> save(@Valid @RequestBody TypeOfSchoolRequestDTO typeOfSchoolDTO){
        return ResponseEntity.status(HttpStatus.CREATED).body(typeOfSchoolService.save(typeOfSchoolDTO));
    }

    @GetMapping("/{id}")
    public ResponseEntity<TypeOfSchoolResponseDTO> getById(@PathVariable Long id){
        return ResponseEntity.ok(typeOfSchoolService.getById(id));
    }

    @GetMapping
    public ResponseEntity<Page<TypeOfSchoolResponseDTO>> get(Pageable pageable){
        return ResponseEntity.ok(typeOfSchoolService.get(pageable));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id){
        typeOfSchoolService.delete(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TypeOfSchoolResponseDTO> update(@PathVariable Long id,@Valid @RequestBody TypeOfSchoolRequestDTO typeOfSchoolDTO){
        return ResponseEntity.ok(typeOfSchoolService.update(id, typeOfSchoolDTO));
    }
}

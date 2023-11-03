package rs.ac.bg.fon.silab.mock_exam.web;

import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rs.ac.bg.fon.silab.mock_exam.domain.city.dto.CityRequestDTO;
import rs.ac.bg.fon.silab.mock_exam.domain.city.dto.CityRequestUpdateDTO;
import rs.ac.bg.fon.silab.mock_exam.domain.city.dto.CityResponseDTO;
import rs.ac.bg.fon.silab.mock_exam.domain.city.service.CityService;

import java.util.List;

@RestController
@RequestMapping("/api/cities")
public class CityController {

    private final CityService cityService;

    public CityController(CityService cityService) {
        this.cityService = cityService;
    }

    @PostMapping
    public ResponseEntity<CityResponseDTO> save(@Valid @RequestBody CityRequestDTO cityDTO){
        return ResponseEntity.status(HttpStatus.CREATED).body(cityService.save(cityDTO));
    }

    @GetMapping("/{zipCode}")
    public ResponseEntity<CityResponseDTO> getByZipCode(@PathVariable Long zipCode){
        return ResponseEntity.ok(cityService.getByZipCode(zipCode));
    }

    @GetMapping
    public ResponseEntity<Page<CityResponseDTO>> get(Pageable pageable){
        return ResponseEntity.ok(cityService.get(pageable));
    }

    @GetMapping("/all")
    public ResponseEntity<List<CityResponseDTO>> getAll(){
        return ResponseEntity.ok(cityService.getAll());
    }

    @DeleteMapping("/{zipCode}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long zipCode){
        cityService.delete(zipCode);
    }

    @PutMapping("/{zipCode}")
    public ResponseEntity<CityResponseDTO> update(@PathVariable Long zipCode, @Valid @RequestBody CityRequestUpdateDTO cityDTO){
        return ResponseEntity.ok(cityService.update(zipCode, cityDTO));
    }

}

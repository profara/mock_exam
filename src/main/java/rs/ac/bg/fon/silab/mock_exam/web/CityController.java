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

/**
 * REST controller for managing city-related operations.
 * Provides endpoints for creating, retrieving, updating, and deleting cities.
 */
@RestController
@RequestMapping("/api/cities")
public class CityController {

    private final CityService cityService;

    /**
     * Constructs a CityController with the necessary CityService dependency.
     *
     * @param cityService The service that will handle the business logic for cities.
     */
    public CityController(CityService cityService) {
        this.cityService = cityService;
    }

    /**
     * Endpoint to save a new city.
     *
     * @param cityDTO DTO containing the details of the city to be saved.
     * @return ResponseEntity with created status and the saved CityResponseDTO.
     */
    @PostMapping
    public ResponseEntity<CityResponseDTO> save(@Valid @RequestBody CityRequestDTO cityDTO){
        return ResponseEntity.status(HttpStatus.CREATED).body(cityService.save(cityDTO));
    }

    /**
     * Endpoint to retrieve a city by its zip code.
     *
     * @param zipCode The zip code of the city to retrieve.
     * @return ResponseEntity with OK status and the found CityResponseDTO.
     */
    @GetMapping("/{zipCode}")
    public ResponseEntity<CityResponseDTO> getByZipCode(@PathVariable Long zipCode){
        return ResponseEntity.ok(cityService.getByZipCode(zipCode));
    }

    /**
     * Endpoint to retrieve a paginated list of cities.
     *
     * @param pageable Pagination information.
     * @return ResponseEntity with OK status and a page of CityResponseDTOs.
     */
    @GetMapping
    public ResponseEntity<Page<CityResponseDTO>> get(Pageable pageable){
        return ResponseEntity.ok(cityService.get(pageable));
    }

    /**
     * Endpoint to retrieve a list of all cities.
     *
     * @return ResponseEntity with OK status and a list of all CityResponseDTOs.
     */
    @GetMapping("/all")
    public ResponseEntity<List<CityResponseDTO>> getAll(){
        return ResponseEntity.ok(cityService.getAll());
    }

    /**
     * Endpoint to delete a city by its zip code.
     *
     * @param zipCode The zip code of the city to delete.
     */
    @DeleteMapping("/{zipCode}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long zipCode){
        cityService.delete(zipCode);
    }

    /**
     * Endpoint to update a city.
     *
     * @param zipCode The zip code of the city to update.
     * @param cityDTO DTO containing updated details of the city.
     * @return ResponseEntity with OK status and the updated CityResponseDTO.
     */
    @PutMapping("/{zipCode}")
    public ResponseEntity<CityResponseDTO> update(@PathVariable Long zipCode, @Valid @RequestBody CityRequestUpdateDTO cityDTO){
        return ResponseEntity.ok(cityService.update(zipCode, cityDTO));
    }

}

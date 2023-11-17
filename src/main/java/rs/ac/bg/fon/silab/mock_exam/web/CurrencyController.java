package rs.ac.bg.fon.silab.mock_exam.web;

import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rs.ac.bg.fon.silab.mock_exam.domain.currency.dto.CurrencyRequestDTO;
import rs.ac.bg.fon.silab.mock_exam.domain.currency.dto.CurrencyResponseDTO;
import rs.ac.bg.fon.silab.mock_exam.domain.currency.service.CurrencyService;

/**
 * REST controller for managing currency-related operations.
 * Provides endpoints for creating, retrieving, updating, and deleting currency records.
 */
@RestController
@RequestMapping("/api/currencies")
public class CurrencyController {

    private final CurrencyService currencyService;

    /**
     * Constructs a CurrencyController with the necessary CurrencyService dependency.
     * This service handles business logic related to currency operations.
     *
     * @param currencyService The service that will handle the business logic for currencies.
     */
    public CurrencyController(CurrencyService currencyService) {
        this.currencyService = currencyService;
    }

    /**
     * Endpoint to save a new currency.
     *
     * @param currencyDTO DTO containing the details of the currency to be saved.
     * @return ResponseEntity with created status and the saved CurrencyResponseDTO.
     */
    @PostMapping
    public ResponseEntity<CurrencyResponseDTO> save(@Valid @RequestBody CurrencyRequestDTO currencyDTO){
        return ResponseEntity.status(HttpStatus.CREATED).body(currencyService.save(currencyDTO));
    }

    /**
     * Endpoint to retrieve a currency by its ID.
     *
     * @param id The ID of the currency to retrieve.
     * @return ResponseEntity with OK status and the found CurrencyResponseDTO.
     */
    @GetMapping("/{id}")
    public ResponseEntity<CurrencyResponseDTO> getById(@PathVariable Long id){
        return ResponseEntity.ok(currencyService.getById(id));
    }

    /**
     * Endpoint to retrieve a paginated list of currencies.
     *
     * @param pageable Pagination information.
     * @return ResponseEntity with OK status and a page of CurrencyResponseDTOs.
     */
    @GetMapping
    public ResponseEntity<Page<CurrencyResponseDTO>> get(Pageable pageable){
        return ResponseEntity.ok(currencyService.get(pageable));
    }

    /**
     * Endpoint to delete a currency by its ID.
     *
     * @param id The ID of the currency to delete.
     */
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id){
        currencyService.delete(id);
    }

    /**
     * Endpoint to update a currency.
     *
     * @param id The ID of the currency to be updated.
     * @param currencyDTO DTO containing updated details of the currency.
     * @return ResponseEntity with OK status and the updated CurrencyResponseDTO.
     */
    @PutMapping("/{id}")
    public ResponseEntity<CurrencyResponseDTO> update(@PathVariable Long id,
                                                      @Valid @RequestBody CurrencyRequestDTO currencyDTO){
        return ResponseEntity.ok(currencyService.update(id,currencyDTO));
    }

}

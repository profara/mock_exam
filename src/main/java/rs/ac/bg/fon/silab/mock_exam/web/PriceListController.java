package rs.ac.bg.fon.silab.mock_exam.web;

import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rs.ac.bg.fon.silab.mock_exam.domain.pricelist.dto.PriceListRequestDTO;
import rs.ac.bg.fon.silab.mock_exam.domain.pricelist.dto.PriceListResponseDTO;
import rs.ac.bg.fon.silab.mock_exam.domain.pricelist.service.PriceListService;

/**
 * REST controller for managing price list operations.
 * Provides endpoints for creating, retrieving, updating, and deleting price lists.
 */
@RestController
@RequestMapping("/api/priceLists")
public class PriceListController {

    private final PriceListService priceListService;

    /**
     * Constructs a PriceListController with the necessary PriceListService dependency.
     * This service handles business logic related to price list operations.
     *
     * @param priceListService The service that will handle the business logic for price lists.
     */
    public PriceListController(PriceListService priceListService) {
        this.priceListService = priceListService;
    }

    /**
     * Endpoint to save a new price list.
     *
     * @param priceListDTO DTO containing the details of the price list to be saved.
     * @return ResponseEntity with created status and the saved PriceListResponseDTO.
     */
    @PostMapping
    public ResponseEntity<PriceListResponseDTO> save(@Valid @RequestBody PriceListRequestDTO priceListDTO){
        return ResponseEntity.status(HttpStatus.CREATED).body(priceListService.save(priceListDTO));
    }

    /**
     * Endpoint to retrieve a price list by its ID.
     *
     * @param id The ID of the price list to retrieve.
     * @return ResponseEntity with OK status and the found PriceListResponseDTO.
     */
    @GetMapping("/{id}")
    public ResponseEntity<PriceListResponseDTO> getById(@PathVariable Long id){
        return ResponseEntity.ok(priceListService.getById(id));
    }

    /**
     * Endpoint to retrieve a paginated list of price lists.
     *
     * @param pageable Pagination information.
     * @return ResponseEntity with OK status and a page of PriceListResponseDTOs.
     */

    @GetMapping
    public ResponseEntity<Page<PriceListResponseDTO>> get(Pageable pageable){
        return ResponseEntity.ok(priceListService.get(pageable));
    }

    /**
     * Endpoint to delete a price list by its ID.
     *
     * @param id The ID of the price list to delete.
     */
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id){
        priceListService.delete(id);
    }

    /**
     * Endpoint to update a price list.
     *
     * @param id The ID of the price list to be updated.
     * @param priceListDTO DTO containing updated details of the price list.
     * @return ResponseEntity with OK status and the updated PriceListResponseDTO.
     */
    @PutMapping("/{id}")
    public ResponseEntity<PriceListResponseDTO> update(@PathVariable Long id,
                                                       @Valid @RequestBody PriceListRequestDTO priceListDTO){
        return ResponseEntity.ok(priceListService.update(id,priceListDTO));
    }
}

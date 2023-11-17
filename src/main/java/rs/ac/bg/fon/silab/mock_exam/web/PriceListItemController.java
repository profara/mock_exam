package rs.ac.bg.fon.silab.mock_exam.web;

import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rs.ac.bg.fon.silab.mock_exam.domain.pricelistitem.dto.PriceListItemCriteriaRequestDTO;
import rs.ac.bg.fon.silab.mock_exam.domain.pricelistitem.dto.PriceListItemRequestDTO;
import rs.ac.bg.fon.silab.mock_exam.domain.pricelistitem.dto.PriceListItemResponseDTO;
import rs.ac.bg.fon.silab.mock_exam.domain.pricelistitem.service.PriceListItemService;


/**
 * REST controller for managing price list item operations.
 * Provides endpoints for creating, retrieving, updating, and deleting price list items.
 */
@RestController
@RequestMapping("/api/priceListItems")
public class PriceListItemController {

    private final PriceListItemService priceListItemService;

    /**
     * Constructs a PriceListItemController with the necessary PriceListItemService dependency.
     *
     * @param priceListItemService The service that will handle the business logic for price list items.
     */
    public PriceListItemController(PriceListItemService priceListItemService) {
        this.priceListItemService = priceListItemService;
    }

    /**
     * Endpoint to save a new price list item.
     *
     * @param priceListItemDTO DTO containing the details of the price list item to be saved.
     * @return ResponseEntity with created status and the saved PriceListItemResponseDTO.
     */
    @PostMapping
    public ResponseEntity<PriceListItemResponseDTO> save(@Valid @RequestBody PriceListItemRequestDTO priceListItemDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(priceListItemService.save(priceListItemDTO));
    }

    /**
     * Endpoint to retrieve a price list item by its ID.
     *
     * @param id The ID of the price list item to retrieve.
     * @return ResponseEntity with OK status and the found PriceListItemResponseDTO.
     */
    @GetMapping("/{id}")
    public ResponseEntity<PriceListItemResponseDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(priceListItemService.getById(id));
    }

    /**
     * Endpoint to retrieve a paginated list of price list items.
     *
     * @param pageable Pagination information.
     * @return ResponseEntity with OK status and a page of PriceListItemResponseDTOs.
     */
    @GetMapping
    public ResponseEntity<Page<PriceListItemResponseDTO>> get(Pageable pageable) {
        return ResponseEntity.ok(priceListItemService.get(pageable));
    }

    /**
     * Endpoint to delete a price list item by its ID.
     *
     * @param id The ID of the price list item to delete.
     */
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        priceListItemService.delete(id);
    }

    /**
     * Endpoint to update a price list item.
     *
     * @param id The ID of the price list item to be updated.
     * @param priceListItemDTO DTO containing updated details of the price list item.
     * @return ResponseEntity with OK status and the updated PriceListItemResponseDTO.
     */
    @PutMapping("/{id}")
    public ResponseEntity<PriceListItemResponseDTO> update(@PathVariable Long id,
                                                           @Valid @RequestBody PriceListItemRequestDTO priceListItemDTO) {
        return ResponseEntity.ok(priceListItemService.update(id, priceListItemDTO));
    }

    /**
     * Endpoint to search for price list items based on specific criteria.
     *
     * @param criteriaDTO DTO containing criteria to filter price list items.
     * @return ResponseEntity with OK status and the filtered PriceListItemResponseDTO.
     */
    @GetMapping("/search")
    public ResponseEntity<PriceListItemResponseDTO> getByCriteria(
            @ModelAttribute PriceListItemCriteriaRequestDTO criteriaDTO
            ){
        return ResponseEntity.ok(priceListItemService.getByCriteria(criteriaDTO));
    }




}

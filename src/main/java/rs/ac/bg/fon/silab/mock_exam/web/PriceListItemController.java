package rs.ac.bg.fon.silab.mock_exam.web;

import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rs.ac.bg.fon.silab.mock_exam.domain.pricelistitem.dto.PriceListItemRequestDTO;
import rs.ac.bg.fon.silab.mock_exam.domain.pricelistitem.dto.PriceListItemResponseDTO;
import rs.ac.bg.fon.silab.mock_exam.domain.pricelistitem.service.PriceListItemService;

@RestController
@RequestMapping("/api/priceListItems")
public class PriceListItemController {

    private final PriceListItemService priceListItemService;

    public PriceListItemController(PriceListItemService priceListItemService) {
        this.priceListItemService = priceListItemService;
    }

    @PostMapping
    public ResponseEntity<PriceListItemResponseDTO> save(@Valid @RequestBody PriceListItemRequestDTO priceListItemDTO){
        return ResponseEntity.status(HttpStatus.CREATED).body(priceListItemService.save(priceListItemDTO));
    }

    @GetMapping("/{id}")
    public ResponseEntity<PriceListItemResponseDTO> getById(@PathVariable Long id){
        return ResponseEntity.ok(priceListItemService.getById(id));
    }

    @GetMapping
    public ResponseEntity<Page<PriceListItemResponseDTO>> get(Pageable pageable){
        return ResponseEntity.ok(priceListItemService.get(pageable));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id){
        priceListItemService.delete(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PriceListItemResponseDTO> update(@PathVariable Long id,
                                                           @Valid @RequestBody PriceListItemRequestDTO priceListItemDTO){
        return ResponseEntity.ok(priceListItemService.update(id, priceListItemDTO));
    }

}

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

@RestController
@RequestMapping("/api/priceLists")
public class PriceListController {

    private final PriceListService priceListService;

    public PriceListController(PriceListService priceListService) {
        this.priceListService = priceListService;
    }

    @PostMapping
    public ResponseEntity<PriceListResponseDTO> save(@Valid @RequestBody PriceListRequestDTO priceListDTO){
        return ResponseEntity.status(HttpStatus.CREATED).body(priceListService.save(priceListDTO));
    }

    @GetMapping("/{id}")
    public ResponseEntity<PriceListResponseDTO> getById(@PathVariable Long id){
        return ResponseEntity.ok(priceListService.getById(id));
    }

    @GetMapping
    public ResponseEntity<Page<PriceListResponseDTO>> get(Pageable pageable){
        return ResponseEntity.ok(priceListService.get(pageable));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id){
        priceListService.delete(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PriceListResponseDTO> update(@PathVariable Long id,
                                                       @Valid @RequestBody PriceListRequestDTO priceListDTO){
        return ResponseEntity.ok(priceListService.update(id,priceListDTO));
    }
}

package rs.ac.bg.fon.silab.mock_exam.web;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
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

}

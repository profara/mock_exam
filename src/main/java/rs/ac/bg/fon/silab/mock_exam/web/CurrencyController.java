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

@RestController
@RequestMapping("/api/currencies")
public class CurrencyController {

    private final CurrencyService currencyService;

    public CurrencyController(CurrencyService currencyService) {
        this.currencyService = currencyService;
    }

    @PostMapping
    public ResponseEntity<CurrencyResponseDTO> save(@Valid @RequestBody CurrencyRequestDTO currencyDTO){
        return ResponseEntity.status(HttpStatus.CREATED).body(currencyService.save(currencyDTO));
    }

    @GetMapping("/{id}")
    public ResponseEntity<CurrencyResponseDTO> getById(@PathVariable Long id){
        return ResponseEntity.ok(currencyService.getById(id));
    }

    @GetMapping
    public ResponseEntity<Page<CurrencyResponseDTO>> get(Pageable pageable){
        return ResponseEntity.ok(currencyService.get(pageable));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id){
        currencyService.delete(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CurrencyResponseDTO> update(@PathVariable Long id,
                                                      @Valid @RequestBody CurrencyRequestDTO currencyDTO){
        return ResponseEntity.ok(currencyService.update(id,currencyDTO));
    }
}

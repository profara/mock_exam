package rs.ac.bg.fon.silab.mock_exam.web;

import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rs.ac.bg.fon.silab.mock_exam.domain.payment.dto.PaymentRequestDTO;
import rs.ac.bg.fon.silab.mock_exam.domain.payment.dto.PaymentResponseDTO;
import rs.ac.bg.fon.silab.mock_exam.domain.payment.service.PaymentService;

@RestController
@RequestMapping("/api/payments")
public class PaymentController {

    private final PaymentService paymentService;

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @PostMapping
    public ResponseEntity<PaymentResponseDTO> save(@Valid @RequestBody PaymentRequestDTO paymentRequestDTO){
        return ResponseEntity.status(HttpStatus.CREATED).body(paymentService.save(paymentRequestDTO));
    }

    @GetMapping("/{id}")
    public ResponseEntity<PaymentResponseDTO> getById(@PathVariable Long id){
        return ResponseEntity.ok(paymentService.getById(id));
    }

    @GetMapping
    public ResponseEntity<Page<PaymentResponseDTO>> get(Pageable pageable){
        return ResponseEntity.ok(paymentService.get(pageable));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id){
        paymentService.delete(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PaymentResponseDTO> update(@PathVariable Long id,
                                                     @Valid @RequestBody PaymentRequestDTO paymentRequestDTO){
        return ResponseEntity.ok(paymentService.update(id,paymentRequestDTO));
    }
}

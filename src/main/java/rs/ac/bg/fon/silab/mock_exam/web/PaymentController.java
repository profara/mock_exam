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

import java.util.Map;

/**
 * REST controller for managing payment-related operations.
 * Provides endpoints for creating, retrieving, updating, and deleting payment records,
 * as well as sending payment slips via email.
 */
@RestController
@RequestMapping("/api/payments")
public class PaymentController {

    private final PaymentService paymentService;

    /**
     * Constructs a PaymentController with the necessary PaymentService dependency.
     *
     * @param paymentService The service that will handle the business logic for payments.
     */
    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    /**
     * Endpoint to save a new payment.
     *
     * @param paymentRequestDTO DTO containing the details of the payment to be saved.
     * @return ResponseEntity with created status and the saved PaymentResponseDTO.
     */
    @PostMapping
    public ResponseEntity<PaymentResponseDTO> save(@Valid @RequestBody PaymentRequestDTO paymentRequestDTO){
        return ResponseEntity.status(HttpStatus.CREATED).body(paymentService.save(paymentRequestDTO));
    }

    /**
     * Endpoint to retrieve a payment by its ID.
     *
     * @param id The ID of the payment to retrieve.
     * @return ResponseEntity with OK status and the found PaymentResponseDTO.
     */
    @GetMapping("/{id}")
    public ResponseEntity<PaymentResponseDTO> getById(@PathVariable Long id){
        return ResponseEntity.ok(paymentService.getById(id));
    }

    /**
     * Endpoint to retrieve a paginated list of payments.
     *
     * @param pageable Pagination information.
     * @return ResponseEntity with OK status and a page of PaymentResponseDTOs.
     */
    @GetMapping
    public ResponseEntity<Page<PaymentResponseDTO>> get(Pageable pageable){
        return ResponseEntity.ok(paymentService.get(pageable));
    }

    /**
     * Endpoint to delete a payment by its ID.
     *
     * @param id The ID of the payment to delete.
     */
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id){
        paymentService.delete(id);
    }

    /**
     * Endpoint to update a payment.
     *
     * @param id The ID of the payment to be updated.
     * @param paymentRequestDTO DTO containing updated details of the payment.
     * @return ResponseEntity with OK status and the updated PaymentResponseDTO.
     */
    @PutMapping("/{id}")
    public ResponseEntity<PaymentResponseDTO> update(@PathVariable Long id,
                                                     @Valid @RequestBody PaymentRequestDTO paymentRequestDTO){
        return ResponseEntity.ok(paymentService.update(id,paymentRequestDTO));
    }

    /**
     * Endpoint to send a payment slip via email.
     *
     * @param payload Map containing email and the necessary data like image and user email.
     * @return ResponseEntity indicating the request was processed.
     */
    @PostMapping("/send-payslip")
    public ResponseEntity<?> sendPayslipOnEmail(@RequestBody Map<String, String> payload){
        paymentService.sendPayslipOnEmail(payload);
        return ResponseEntity.ok(HttpStatus.CREATED);
    }
}

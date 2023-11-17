package rs.ac.bg.fon.silab.mock_exam.domain.payment.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import rs.ac.bg.fon.silab.mock_exam.domain.payment.dto.PaymentRequestDTO;
import rs.ac.bg.fon.silab.mock_exam.domain.payment.dto.PaymentResponseDTO;

import java.util.Map;

/**
 * Interface for managing payment-related operations.
 * Provides methods for saving, updating, retrieving, and deleting payment records.
 */
public interface PaymentService {

    /**
     * Saves a new payment record or updates an existing one based on the provided DTO.
     *
     * @param paymentRequestDTO the DTO containing payment details
     * @return the saved or updated payment as a DTO
     */
    PaymentResponseDTO save(PaymentRequestDTO paymentRequestDTO);

    /**
     * Retrieves a payment by its ID.
     *
     * @param id the ID of the payment to retrieve
     * @return the found Payment entity as a DTO
     * @throws rs.ac.bg.fon.silab.mock_exam.infrastructure.exception.EntityNotFoundException if the Payment is not found
     */
    PaymentResponseDTO getById(Long id);

    /**
     * Retrieves a paginated list of payments.
     *
     * @param pageable the pagination information
     * @return a page of PaymentResponseDTO objects
     */
    Page<PaymentResponseDTO> get(Pageable pageable);

    /**
     * Deletes a payment by its ID.
     *
     * @param id the ID of the payment to delete
     * @throws rs.ac.bg.fon.silab.mock_exam.infrastructure.exception.EntityNotFoundException if Payment with the entered ID doesn't exist
     */
    void delete(Long id);

    /**
     * Updates an existing payment with new details provided in the DTO.
     *
     * @param id the ID of the payment to update
     * @param paymentRequestDTO the DTO containing updated details
     * @return the updated payment as a DTO
     * @throws rs.ac.bg.fon.silab.mock_exam.infrastructure.exception.EntityNotFoundException if Payment with the entered ID doesn't exist
     */
    PaymentResponseDTO update(Long id, PaymentRequestDTO paymentRequestDTO);

    /**
     * Sends a payslip as an email attachment.
     *
     * @param payload a Map containing the necessary data like image and user email
     */
    void sendPayslipOnEmail(Map<String, String> payload);
}

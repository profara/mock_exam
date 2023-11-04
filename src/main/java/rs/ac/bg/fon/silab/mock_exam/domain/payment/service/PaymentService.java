package rs.ac.bg.fon.silab.mock_exam.domain.payment.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import rs.ac.bg.fon.silab.mock_exam.domain.payment.dto.PaymentRequestDTO;
import rs.ac.bg.fon.silab.mock_exam.domain.payment.dto.PaymentResponseDTO;

import java.util.Map;

public interface PaymentService {
    PaymentResponseDTO save(PaymentRequestDTO paymentRequestDTO);

    PaymentResponseDTO getById(Long id);

    Page<PaymentResponseDTO> get(Pageable pageable);

    void delete(Long id);

    PaymentResponseDTO update(Long id, PaymentRequestDTO paymentRequestDTO);

    void sendPayslipOnEmail(Map<String, String> payload);
}

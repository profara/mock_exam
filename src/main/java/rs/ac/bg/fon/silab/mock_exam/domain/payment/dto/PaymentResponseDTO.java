package rs.ac.bg.fon.silab.mock_exam.domain.payment.dto;

import rs.ac.bg.fon.silab.mock_exam.domain.application.dto.ApplicationResponseDTO;

import java.math.BigDecimal;

public record PaymentResponseDTO(
        Long id,
        String referenceNumber,
        String creditorAccount,
        BigDecimal amount,
        String paymentPurpose,
        int model,
        ApplicationResponseDTO application
) {
}

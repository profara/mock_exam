package rs.ac.bg.fon.silab.mock_exam.domain.payment.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import rs.ac.bg.fon.silab.mock_exam.domain.application.dto.ApplicationRequestDTO;

import java.math.BigDecimal;

public record PaymentRequestDTO(
        @NotBlank(message = "Reference number is mandatory")
        String referenceNumber,
        @NotBlank(message = "Creditor account is mandatory")
        String creditorAccount,
        @NotNull(message = "Amount is mandatory")
        BigDecimal amount,
        @NotBlank(message = "Payment purpose is mandatory")
        String paymentPurpose,
        int model,
        @NotNull(message = "Application is mandatory")
        Long applicationId

) {
}

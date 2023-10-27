package rs.ac.bg.fon.silab.mock_exam.domain.payment.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import rs.ac.bg.fon.silab.mock_exam.domain.application.service.ApplicationService;
import rs.ac.bg.fon.silab.mock_exam.domain.currency.service.CurrencyService;
import rs.ac.bg.fon.silab.mock_exam.domain.payment.dto.PaymentRequestDTO;
import rs.ac.bg.fon.silab.mock_exam.domain.payment.dto.PaymentResponseDTO;
import rs.ac.bg.fon.silab.mock_exam.domain.payment.entity.Payment;

@Mapper(componentModel = "spring", uses = {ApplicationService.class})
public interface PaymentMapper {

    @Mapping(source = "applicationId", target = "application")
    Payment map(PaymentRequestDTO paymentRequestDTO);

    PaymentResponseDTO map(Payment payment);

    @Mapping(source = "applicationId",target = "application")
    void update(@MappingTarget Payment payment, PaymentRequestDTO paymentRequestDTO);
}

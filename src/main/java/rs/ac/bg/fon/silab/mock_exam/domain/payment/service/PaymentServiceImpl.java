package rs.ac.bg.fon.silab.mock_exam.domain.payment.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import rs.ac.bg.fon.silab.mock_exam.domain.payment.dto.PaymentRequestDTO;
import rs.ac.bg.fon.silab.mock_exam.domain.payment.dto.PaymentResponseDTO;
import rs.ac.bg.fon.silab.mock_exam.domain.payment.entity.Payment;
import rs.ac.bg.fon.silab.mock_exam.domain.payment.mapper.PaymentMapper;
import rs.ac.bg.fon.silab.mock_exam.domain.payment.repository.PaymentRepository;
import rs.ac.bg.fon.silab.mock_exam.infrastructure.exception.EntityNotFoundException;

@Service
public class PaymentServiceImpl implements PaymentService{

    private final PaymentRepository paymentRepository;
    private final PaymentMapper mapper;

    public PaymentServiceImpl(PaymentRepository paymentRepository, PaymentMapper mapper) {
        this.paymentRepository = paymentRepository;
        this.mapper = mapper;
    }

    @Override
    public PaymentResponseDTO save(PaymentRequestDTO paymentRequestDTO) {
        Payment payment = mapper.map(paymentRequestDTO);

        paymentRepository.save(payment);

        return mapper.map(payment);
    }

    @Override
    public PaymentResponseDTO getById(Long id) {
        var payment = paymentRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(Payment.class.getSimpleName(), "id", id));

        return mapper.map(payment);
    }

    @Override
    public Page<PaymentResponseDTO> get(Pageable pageable) {
        return paymentRepository.findAll(pageable).map(mapper::map);
    }

    @Override
    public void delete(Long id) {
        if(!paymentRepository.existsById(id)){
            throw new EntityNotFoundException(Payment.class.getSimpleName(), "id", id);
        }

        paymentRepository.deleteById(id);
    }

    @Override
    public PaymentResponseDTO update(Long id, PaymentRequestDTO paymentRequestDTO) {
        var payment = paymentRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(Payment.class.getSimpleName(), "id", id));

        mapper.update(payment, paymentRequestDTO);

        paymentRepository.save(payment);

        return mapper.map(payment);
    }
}

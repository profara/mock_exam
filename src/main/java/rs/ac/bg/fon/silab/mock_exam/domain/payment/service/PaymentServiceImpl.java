package rs.ac.bg.fon.silab.mock_exam.domain.payment.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import rs.ac.bg.fon.silab.mock_exam.domain.application.entity.Application;
import rs.ac.bg.fon.silab.mock_exam.domain.payment.dto.PaymentRequestDTO;
import rs.ac.bg.fon.silab.mock_exam.domain.payment.dto.PaymentResponseDTO;
import rs.ac.bg.fon.silab.mock_exam.domain.payment.entity.Payment;
import rs.ac.bg.fon.silab.mock_exam.domain.payment.mapper.PaymentMapper;
import rs.ac.bg.fon.silab.mock_exam.domain.payment.repository.PaymentRepository;
import rs.ac.bg.fon.silab.mock_exam.domain.pricelist.entity.PriceList;
import rs.ac.bg.fon.silab.mock_exam.domain.pricelist.service.PriceListService;
import rs.ac.bg.fon.silab.mock_exam.domain.pricelistitem.entity.PriceListItem;
import rs.ac.bg.fon.silab.mock_exam.domain.pricelistitem.service.PriceListItemService;
import rs.ac.bg.fon.silab.mock_exam.infrastructure.exception.EntityNotFoundException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Year;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import static rs.ac.bg.fon.silab.mock_exam.infrastructure.config.Constants.MATH_EXAM_ID;
import static rs.ac.bg.fon.silab.mock_exam.infrastructure.config.Constants.OPSTA_INF_EXAM_ID;

@Service
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepository paymentRepository;
    private final PaymentMapper mapper;
    private final PriceListService priceListService;
    private final PriceListItemService priceListItemService;

    public PaymentServiceImpl(PaymentRepository paymentRepository, PaymentMapper mapper, PriceListService priceListService, PriceListItemService priceListItemService) {
        this.paymentRepository = paymentRepository;
        this.mapper = mapper;
        this.priceListService = priceListService;
        this.priceListItemService = priceListItemService;
    }

    @Override
    @Transactional
    public PaymentResponseDTO save(PaymentRequestDTO paymentRequestDTO) {
        Payment payment = mapper.map(paymentRequestDTO);

        BigDecimal amount = calculateAmount(payment);
        payment.setAmount(amount);

        paymentRepository.save(payment);

        return mapper.map(payment);
    }


    @Override
    @Transactional(readOnly = true)
    public PaymentResponseDTO getById(Long id) {
        var payment = paymentRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(Payment.class.getSimpleName(), "id", id));

        return mapper.map(payment);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<PaymentResponseDTO> get(Pageable pageable) {
        return paymentRepository.findAll(pageable).map(mapper::map);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        if (!paymentRepository.existsById(id)) {
            throw new EntityNotFoundException(Payment.class.getSimpleName(), "id", id);
        }

        paymentRepository.deleteById(id);
    }

    @Override
    @Transactional
    public PaymentResponseDTO update(Long id, PaymentRequestDTO paymentRequestDTO) {
        var payment = paymentRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(Payment.class.getSimpleName(), "id", id));

        mapper.update(payment, paymentRequestDTO);

        paymentRepository.save(payment);

        return mapper.map(payment);
    }


    private BigDecimal calculateAmount(Payment payment) {
        Application application = payment.getApplication();

        boolean privileged = application.getCandidate().isAttendedPreparation();
        List<Long> exams = application.getAppointments().stream()
                .map(appointment -> appointment.getExam().getId())
                .toList();

        PriceList priceList = priceListService.findByYear(Year.now());
        List<PriceListItem> priceListItems = priceListItemService.findByPriceList(priceList.getId());

        Map<Long, Long> examsCount = application.getAppointments().stream()
                .map(appointment -> appointment.getExam().getId())
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));

        BigDecimal amount = priceListItems.stream()
                .filter(priceListItem -> privileged == priceListItem.isPrivileged())
                .filter(priceListItem -> exams.contains(priceListItem.getExam().getId()))
                .filter(priceListItem -> payment.getCurrency().equals(priceListItem.getCurrency()))
                .map(priceListItem -> priceListItem.getPrice().multiply(new BigDecimal(examsCount.get(priceListItem.getExam().getId()))))
                .reduce(BigDecimal.ZERO, BigDecimal::add);



        if(exams.contains(MATH_EXAM_ID) && exams.contains(OPSTA_INF_EXAM_ID)){
            if(!privileged) {
                BigDecimal decreasePercentage = new BigDecimal(0.083333333333);
                BigDecimal amountToSubtract = amount.multiply(decreasePercentage);
                amount = amount.subtract(amountToSubtract);
            } else{
                BigDecimal decreasePercentage = new BigDecimal(0.10);
                BigDecimal amountToSubtract = amount.multiply(decreasePercentage);
                amount = amount.subtract(amountToSubtract);
            }
        }

        return amount.setScale(2, RoundingMode.HALF_UP);
    }
}

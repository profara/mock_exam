package rs.ac.bg.fon.silab.mock_exam.domain.payment.service;

import jakarta.xml.bind.DatatypeConverter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import rs.ac.bg.fon.silab.mock_exam.domain.application.entity.Application;
import rs.ac.bg.fon.silab.mock_exam.domain.appointment.entity.Appointment;
import rs.ac.bg.fon.silab.mock_exam.domain.currency.service.CurrencyService;
import rs.ac.bg.fon.silab.mock_exam.domain.payment.dto.PaymentRequestDTO;
import rs.ac.bg.fon.silab.mock_exam.domain.payment.dto.PaymentResponseDTO;
import rs.ac.bg.fon.silab.mock_exam.domain.payment.entity.Payment;
import rs.ac.bg.fon.silab.mock_exam.domain.payment.mapper.PaymentMapper;
import rs.ac.bg.fon.silab.mock_exam.domain.payment.repository.PaymentRepository;
import rs.ac.bg.fon.silab.mock_exam.domain.pricelist.entity.PriceList;
import rs.ac.bg.fon.silab.mock_exam.domain.pricelist.service.PriceListService;
import rs.ac.bg.fon.silab.mock_exam.domain.pricelistitem.entity.PriceListItem;
import rs.ac.bg.fon.silab.mock_exam.domain.pricelistitem.service.PriceListItemService;
import rs.ac.bg.fon.silab.mock_exam.infrastructure.email.EmailService;
import rs.ac.bg.fon.silab.mock_exam.infrastructure.exception.EntityNotFoundException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Year;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import static rs.ac.bg.fon.silab.mock_exam.infrastructure.config.Constants.*;

@Service
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepository paymentRepository;
    private final PaymentMapper mapper;
    private final PriceListService priceListService;
    private final PriceListItemService priceListItemService;
    private final CurrencyService currencyService;
    private final EmailService emailService;

    public PaymentServiceImpl(PaymentRepository paymentRepository, PaymentMapper mapper, PriceListService priceListService, PriceListItemService priceListItemService, CurrencyService currencyService, EmailService emailService) {
        this.paymentRepository = paymentRepository;
        this.mapper = mapper;
        this.priceListService = priceListService;
        this.priceListItemService = priceListItemService;
        this.currencyService = currencyService;
        this.emailService = emailService;
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

    @Override
    public void sendPayslipOnEmail(Map<String, String> payload) {
        String imageData = payload.get("imageData");
        String userEmail = payload.get("userEmail");

        byte[] imageBytes = DatatypeConverter.parseBase64Binary(imageData.substring(imageData.indexOf(",") + 1));

        emailService.sendAttachment(userEmail, imageBytes);
    }


    private BigDecimal calculateAmount(Payment payment) {
        Application application = payment.getApplication();
        List<Appointment> appointments = application.getAppointments();

        boolean privileged = application.getCandidate().isAttendedPreparation();
        List<Long> exams = extractExamIds(appointments);
        PriceList priceList = priceListService.findByYear(Year.now());
        List<PriceListItem> priceListItems = priceListItemService.findByPriceList(priceList.getId());

        Map<Long, Long> examsCount = countExams(appointments);

        BigDecimal amount = priceListItems.stream()
                .filter(priceListItem -> privileged == priceListItem.isPrivileged())
                .filter(priceListItem -> exams.contains(priceListItem.getExam().getId()))
                .filter(priceListItem -> currencyService.findByCode(CURRENCY_CODE).equals(priceListItem.getCurrency()))
                .map(priceListItem -> priceListItem.getPrice().multiply(new BigDecimal(examsCount.get(priceListItem.getExam().getId()))))
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        if(exams.contains(MATH_EXAM_ID) && exams.contains(OPSTA_INF_EXAM_ID)){
            amount = amount.subtract(DISCOUNT_AMOUNT);
        }

        return amount.setScale(2, RoundingMode.HALF_UP);
    }

    private List<Long> extractExamIds(List<Appointment> appointments){
        return appointments.stream()
                .map(appointment -> appointment.getExam().getId())
                .toList();
    }

    private Map<Long, Long> countExams(List<Appointment> appointments){
        return appointments.stream()
                .map(appointment -> appointment.getExam().getId())
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
    }



}

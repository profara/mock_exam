package rs.ac.bg.fon.silab.mock_exam.domain.payment.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import rs.ac.bg.fon.silab.mock_exam.domain.application.entity.Application;
import rs.ac.bg.fon.silab.mock_exam.domain.candidate.entity.Candidate;
import rs.ac.bg.fon.silab.mock_exam.domain.exam.entity.Exam;
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
import java.time.Year;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class PaymentServiceImplTest {

    private static final String REFERENCE_NUMBER = "ref123";
    private static final String CREDIT_ACCOUNT = "credAcc123";
    private static final String PURPOSE = "Purpose";

    @Mock
    private PaymentRepository mockPaymentRepository;

    @Mock
    private PaymentMapper mockMapper;

    @Mock
    private PriceListService mockPriceListService;

    @Mock
    private PriceListItemService mockPriceListItemService;

    @InjectMocks
    private PaymentServiceImpl paymentService;


    @Test
    void testSave() {
        PaymentRequestDTO dto = new PaymentRequestDTO(REFERENCE_NUMBER, CREDIT_ACCOUNT, PURPOSE, 10, 1001L);
        Application application = new Application(new Date(), true, new Candidate());
        Payment mappedPayment = new Payment(REFERENCE_NUMBER, CREDIT_ACCOUNT, new BigDecimal(100), PURPOSE, 10, application);
        PaymentResponseDTO expectedResponseDTO = new PaymentResponseDTO(1L, REFERENCE_NUMBER, CREDIT_ACCOUNT, BigDecimal.valueOf(100.50), PURPOSE, 10, null);
        PriceList realPriceList = new PriceList();
        realPriceList.setId(1L);
        Exam mockExam = new Exam();
        mockExam.setId(123L);
        PriceListItem priceListItem = new PriceListItem();
        priceListItem.setExam(mockExam);
        List<PriceListItem> realPriceListItems = List.of(priceListItem);

        when(mockMapper.map(dto)).thenReturn(mappedPayment);
        when(mockPaymentRepository.save(mappedPayment)).thenReturn(mappedPayment);
        when(mockMapper.map(mappedPayment)).thenReturn(expectedResponseDTO);
        when(mockPriceListService.findByYear(any(Year.class))).thenReturn(realPriceList);
        when(mockPriceListItemService.findByPriceList(any(Long.class))).thenReturn(realPriceListItems);

        PaymentResponseDTO actualResponseDTO = paymentService.save(dto);

        verify(mockMapper).map(dto);
        verify(mockPaymentRepository).save(mappedPayment);
        verify(mockMapper).map(mappedPayment);

        assertEquals(expectedResponseDTO, actualResponseDTO);
    }

    @Test
    void testGetByIdWhenPaymentExists() {
        Long id = 1L;
        Payment mockPayment = new Payment();
        PaymentResponseDTO expectedResponseDTO = new PaymentResponseDTO(1L, REFERENCE_NUMBER, CREDIT_ACCOUNT, BigDecimal.valueOf(100.50), PURPOSE, 10, null);

        when(mockPaymentRepository.findById(id)).thenReturn(Optional.of(mockPayment));
        when(mockMapper.map(mockPayment)).thenReturn(expectedResponseDTO);

        PaymentResponseDTO actualResponseDTO = paymentService.getById(id);

        verify(mockPaymentRepository).findById(id);
        verify(mockMapper).map(mockPayment);

        assertEquals(expectedResponseDTO, actualResponseDTO);
    }

    @Test
    void testGetByIdWhenPaymentDoesNotExist() {
        Long id = 1L;
        when(mockPaymentRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> paymentService.getById(id));
    }

    @Test
    void testGet() {
        Pageable pageable = mock(Pageable.class);
        Payment mockPayment = new Payment();
        PaymentResponseDTO mockDTO = new PaymentResponseDTO(1L, REFERENCE_NUMBER, CREDIT_ACCOUNT, BigDecimal.valueOf(100.50), PURPOSE, 10, null);
        Page<Payment> page = new PageImpl<>(List.of(new Payment()));

        when(mockPaymentRepository.findAll(pageable)).thenReturn(page);
        when(mockMapper.map(mockPayment)).thenReturn(mockDTO);

        Page<PaymentResponseDTO> resultPage = paymentService.get(pageable);

        verify(mockMapper, times(page.getContent().size())).map(any(Payment.class));

        assertNotNull(resultPage);
        assertEquals(1, resultPage.getContent().size());
        assertEquals(mockDTO, resultPage.getContent().get(0));
    }

    @Test
    void testDeleteWhenPaymentExists() {
        Long id = 1L;
        when(mockPaymentRepository.existsById(id)).thenReturn(true);

        paymentService.delete(id);

        verify(mockPaymentRepository).deleteById(id);
    }

    @Test
    void testDeleteWhenPaymentDoesNotExist() {
        Long id = 1L;
        when(mockPaymentRepository.existsById(id)).thenReturn(false);

        assertThrows(EntityNotFoundException.class, () -> paymentService.delete(id));

        verify(mockPaymentRepository, never()).deleteById(id);
    }

    @Test
    void testUpdate() {
        Long id = 1L;
        PaymentRequestDTO dto = new PaymentRequestDTO(REFERENCE_NUMBER, CREDIT_ACCOUNT, PURPOSE, 10, 1001L);
        Payment existingPayment = new Payment();
        PaymentResponseDTO expectedResponseDTO = new PaymentResponseDTO(1L, REFERENCE_NUMBER, CREDIT_ACCOUNT, BigDecimal.valueOf(100.50), PURPOSE, 10, null);

        when(mockPaymentRepository.findById(id)).thenReturn(Optional.of(existingPayment));
        when(mockPaymentRepository.save(existingPayment)).thenReturn(existingPayment);
        when(mockMapper.map(existingPayment)).thenReturn(expectedResponseDTO);

        doNothing().when(mockMapper).update(any(), any());

        PaymentResponseDTO actualResponseDTO = paymentService.update(id, dto);

        verify(mockMapper).update(any(), any());
        verify(mockPaymentRepository).save(existingPayment);
        verify(mockMapper).map(existingPayment);

        assertEquals(expectedResponseDTO, actualResponseDTO);
    }
}


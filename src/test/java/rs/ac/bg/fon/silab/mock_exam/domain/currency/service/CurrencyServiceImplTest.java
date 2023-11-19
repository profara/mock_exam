package rs.ac.bg.fon.silab.mock_exam.domain.currency.service;

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
import rs.ac.bg.fon.silab.mock_exam.domain.currency.dto.CurrencyRequestDTO;
import rs.ac.bg.fon.silab.mock_exam.domain.currency.dto.CurrencyResponseDTO;
import rs.ac.bg.fon.silab.mock_exam.domain.currency.entity.Currency;
import rs.ac.bg.fon.silab.mock_exam.domain.currency.mapper.CurrencyMapper;
import rs.ac.bg.fon.silab.mock_exam.domain.currency.repository.CurrencyRepository;
import rs.ac.bg.fon.silab.mock_exam.infrastructure.exception.EntityNotFoundException;

import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class CurrencyServiceImplTest {

    @Mock
    private CurrencyRepository currencyRepository;

    @Mock
    private CurrencyMapper mapper;

    @InjectMocks
    private CurrencyServiceImpl currencyService;

    @Test
    void testFindByIdWhenCurrencyExists() {
        Long id = 1L;
        Currency mockCurrency = new Currency();
        when(currencyRepository.findById(id)).thenReturn(Optional.of(mockCurrency));

        Currency result = currencyService.findById(id);
        assertEquals(mockCurrency, result);
    }

    @Test
    void testFindByIdWhenCurrencyDoesNotExist() {
        Long id = 1L;
        when(currencyRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> currencyService.findById(id));
    }

    @Test
    void testFindByCode() {
        String code = "USD";
        Currency mockCurrency = new Currency();
        when(currencyRepository.findByCode(code)).thenReturn(mockCurrency);

        Currency result = currencyService.findByCode(code);
        assertEquals(mockCurrency, result);
    }

    @Test
    void testSave() {
        CurrencyRequestDTO dto = new CurrencyRequestDTO("US Dollar", "USD");
        Currency mappedCurrency = new Currency();
        Currency savedCurrency = new Currency();
        CurrencyResponseDTO responseDTO = new CurrencyResponseDTO(1L, "US Dollar", "USD");

        when(mapper.map(dto)).thenReturn(mappedCurrency);
        when(currencyRepository.save(mappedCurrency)).thenReturn(savedCurrency);
        when(mapper.map(savedCurrency)).thenReturn(responseDTO);

        CurrencyResponseDTO resultDTO = currencyService.save(dto);

        verify(mapper).map(dto);
        verify(currencyRepository).save(mappedCurrency);
        verify(mapper).map(savedCurrency);

        assertEquals(responseDTO, resultDTO);
    }

    @Test
    void testGetByIdWhenCurrencyExists() {
        Long id = 1L;
        Currency mockCurrency = new Currency();
        CurrencyResponseDTO expectedResponseDTO = new CurrencyResponseDTO(1L, "US Dollar", "USD");

        when(currencyRepository.findById(id)).thenReturn(Optional.of(mockCurrency));
        when(mapper.map(mockCurrency)).thenReturn(expectedResponseDTO);

        CurrencyResponseDTO actualResponseDTO = currencyService.getById(id);

        verify(currencyRepository).findById(id);
        verify(mapper).map(mockCurrency);

        assertEquals(expectedResponseDTO, actualResponseDTO);
    }

    @Test
    void testGetByIdWhenCurrencyDoesNotExist() {
        Long id = 1L;
        when(currencyRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> currencyService.getById(id));

        verify(currencyRepository).findById(id);
        verify(mapper, never()).map(any(Currency.class));
    }

    @Test
    void testGet() {
        Pageable pageable = mock(Pageable.class);
        Currency mockCurrency = new Currency();
        CurrencyResponseDTO mockDTO = new CurrencyResponseDTO(1L, "US Dollar", "USD");
        Page<Currency> page = new PageImpl<>(List.of(new Currency()));

        when(currencyRepository.findAll(pageable)).thenReturn(page);
        when(mapper.map(mockCurrency)).thenReturn(mockDTO);

        Page<CurrencyResponseDTO> resultPage = currencyService.get(pageable);

        verify(mapper, times(page.getContent().size())).map(any(Currency.class));

        assertNotNull(resultPage);
        assertEquals(1, resultPage.getContent().size());
        assertEquals(mockDTO, resultPage.getContent().get(0));
    }

    @Test
    void testDeleteWhenCurrencyExists() {
        Long id = 1L;
        when(currencyRepository.existsById(id)).thenReturn(true);

        currencyService.delete(id);

        verify(currencyRepository).deleteById(id);
    }

    @Test
    void testDeleteWhenCurrencyDoesNotExist() {
        Long id = 1L;
        when(currencyRepository.existsById(id)).thenReturn(false);

        assertThrows(EntityNotFoundException.class, () -> currencyService.delete(id));

        verify(currencyRepository, never()).deleteById(id);
    }

    @Test
    void testUpdateWhenCurrencyExists() {
        Long id = 1L;
        CurrencyRequestDTO dto = new CurrencyRequestDTO("US Dollar", "USD");
        Currency existingCurrency = new Currency();
        CurrencyResponseDTO responseDTO = new CurrencyResponseDTO(1L, "US Dollar", "USD");

        when(currencyRepository.findById(id)).thenReturn(Optional.of(existingCurrency));
        when(currencyRepository.save(existingCurrency)).thenReturn(existingCurrency);
        when(mapper.map(existingCurrency)).thenReturn(responseDTO);

        CurrencyResponseDTO resultDTO = currencyService.update(id, dto);

        verify(mapper).update(any(), any());
        verify(currencyRepository).save(existingCurrency);
        verify(mapper).map(existingCurrency);

        assertEquals(responseDTO, resultDTO);
    }

    @Test
    void testUpdateWhenCurrencyDoesNotExist() {
        Long id = 1L;
        CurrencyRequestDTO dto = new CurrencyRequestDTO("US Dollar", "USD");

        when(currencyRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> currencyService.update(id, dto));

        verify(currencyRepository).findById(id);
        verify(mapper, never()).update(any(Currency.class), any(CurrencyRequestDTO.class));
        verify(currencyRepository, never()).save(any(Currency.class));
    }
}

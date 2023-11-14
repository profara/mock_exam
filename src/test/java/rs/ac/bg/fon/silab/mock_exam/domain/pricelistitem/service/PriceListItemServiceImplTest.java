package rs.ac.bg.fon.silab.mock_exam.domain.pricelistitem.service;

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

import rs.ac.bg.fon.silab.mock_exam.domain.currency.dto.CurrencyCodeRequestDTO;
import rs.ac.bg.fon.silab.mock_exam.domain.currency.dto.CurrencyResponseDTO;
import rs.ac.bg.fon.silab.mock_exam.domain.exam.dto.ExamRequestDTO;
import rs.ac.bg.fon.silab.mock_exam.domain.exam.dto.ExamResponseDTO;
import rs.ac.bg.fon.silab.mock_exam.domain.pricelist.dto.PriceListRequestDTO;
import rs.ac.bg.fon.silab.mock_exam.domain.pricelist.dto.PriceListResponseDTO;
import rs.ac.bg.fon.silab.mock_exam.domain.pricelistitem.dto.PriceListItemCriteriaRequestDTO;
import rs.ac.bg.fon.silab.mock_exam.domain.pricelistitem.dto.PriceListItemRequestDTO;
import rs.ac.bg.fon.silab.mock_exam.domain.pricelistitem.dto.PriceListItemResponseDTO;
import rs.ac.bg.fon.silab.mock_exam.domain.pricelistitem.entity.PriceListItem;
import rs.ac.bg.fon.silab.mock_exam.domain.pricelistitem.mapper.PriceListItemMapper;
import rs.ac.bg.fon.silab.mock_exam.domain.pricelistitem.repository.PriceListItemRepository;
import rs.ac.bg.fon.silab.mock_exam.infrastructure.exception.EntityNotFoundException;

import java.math.BigDecimal;
import java.time.Year;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class PriceListItemServiceImplTest {

    @Mock
    private PriceListItemRepository priceListItemRepository;

    @Mock
    private PriceListItemMapper mapper;

    @InjectMocks
    private PriceListItemServiceImpl priceListItemService;

    @Test
    void testSave() {
        BigDecimal price = new BigDecimal("100.00");
        Year year = Year.now();
        PriceListRequestDTO priceListRequestDTO = new PriceListRequestDTO(year);
        CurrencyCodeRequestDTO currencyCodeRequestDTO = new CurrencyCodeRequestDTO("RSD");
        ExamRequestDTO examRequestDTO = new ExamRequestDTO("Matematika");

        PriceListItemRequestDTO dto = new PriceListItemRequestDTO(priceListRequestDTO, price, true, currencyCodeRequestDTO, examRequestDTO);
        PriceListItem mappedPriceListItem = new PriceListItem();
        PriceListItem savedPriceListItem = new PriceListItem();

        PriceListResponseDTO priceListResponseDTO = new PriceListResponseDTO(1L, year);
        CurrencyResponseDTO currencyResponseDTO = new CurrencyResponseDTO(1L, "Dinar", "RSD");
        ExamResponseDTO examResponseDTO = new ExamResponseDTO(1L, "Matematika");
        PriceListItemResponseDTO responseDTO = new PriceListItemResponseDTO(1L,priceListResponseDTO,price,true,currencyResponseDTO,examResponseDTO );

        when(mapper.map(dto)).thenReturn(mappedPriceListItem);
        when(priceListItemRepository.save(mappedPriceListItem)).thenReturn(savedPriceListItem);
        when(mapper.map(savedPriceListItem)).thenReturn(responseDTO);

        PriceListItemResponseDTO resultDTO = priceListItemService.save(dto);

        verify(mapper).map(dto);
        verify(priceListItemRepository).save(mappedPriceListItem);
        verify(mapper).map(savedPriceListItem);

        assertEquals(responseDTO, resultDTO);
    }

    @Test
    void testGetByIdWhenPriceListItemExists() {
        Long id = 1L;
        PriceListItem mockPriceListItem = new PriceListItem();
        Year year = Year.now();
        BigDecimal price = new BigDecimal("100.00");
        PriceListResponseDTO priceListResponseDTO = new PriceListResponseDTO(id, year);
        CurrencyResponseDTO currencyResponseDTO = new CurrencyResponseDTO(id, "Dinar", "RSD");
        ExamResponseDTO examResponseDTO = new ExamResponseDTO(id, "Matematika");
        PriceListItemResponseDTO expectedResponseDTO = new PriceListItemResponseDTO(id, priceListResponseDTO,price,true,currencyResponseDTO, examResponseDTO);

        when(priceListItemRepository.findById(id)).thenReturn(Optional.of(mockPriceListItem));
        when(mapper.map(mockPriceListItem)).thenReturn(expectedResponseDTO);

        PriceListItemResponseDTO actualResponseDTO = priceListItemService.getById(id);

        verify(priceListItemRepository).findById(id);
        verify(mapper).map(mockPriceListItem);

        assertEquals(expectedResponseDTO, actualResponseDTO);
    }

    @Test
    void testGetByIdWhenPriceListItemDoesNotExist() {
        Long id = 1L;
        when(priceListItemRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> priceListItemService.getById(id));
    }

    @Test
    void testGet() {
        Pageable pageable = mock(Pageable.class);
        PriceListItem mockPriceListItem = new PriceListItem();

        Long id = 1L;
        Year year = Year.now();
        BigDecimal price = new BigDecimal("100.00");
        PriceListResponseDTO priceListResponseDTO = new PriceListResponseDTO(id, year);
        CurrencyResponseDTO currencyResponseDTO = new CurrencyResponseDTO(id, "Dinar", "RSD");
        ExamResponseDTO examResponseDTO = new ExamResponseDTO(id, "Matematika");
        PriceListItemResponseDTO mockDTO = new PriceListItemResponseDTO(id, priceListResponseDTO, price, true, currencyResponseDTO, examResponseDTO);
        Page<PriceListItem> page = new PageImpl<>(List.of(new PriceListItem()));

        when(priceListItemRepository.findAll(pageable)).thenReturn(page);
        when(mapper.map(mockPriceListItem)).thenReturn(mockDTO);

        Page<PriceListItemResponseDTO> resultPage = priceListItemService.get(pageable);

        verify(mapper, times(page.getContent().size())).map(any(PriceListItem.class));

        assertNotNull(resultPage);
        assertEquals(1, resultPage.getContent().size());
        assertEquals(mockDTO, resultPage.getContent().get(0));
    }

    @Test
    void testDeleteWhenPriceListItemExists() {
        Long id = 1L;
        when(priceListItemRepository.existsById(id)).thenReturn(true);

        priceListItemService.delete(id);

        verify(priceListItemRepository).deleteById(id);
    }

    @Test
    void testDeleteWhenPriceListItemDoesNotExist() {
        Long id = 1L;
        when(priceListItemRepository.existsById(id)).thenReturn(false);

        assertThrows(EntityNotFoundException.class, () -> priceListItemService.delete(id));

        verify(priceListItemRepository, never()).deleteById(id);
    }

    @Test
    void testUpdate() {
        Long id = 1L;
        BigDecimal price = new BigDecimal("100.00");
        Year year = Year.now();
        PriceListRequestDTO priceListRequestDTO = new PriceListRequestDTO(year);
        CurrencyCodeRequestDTO currencyCodeRequestDTO = new CurrencyCodeRequestDTO("RSD");
        ExamRequestDTO examRequestDTO = new ExamRequestDTO("Matematika");

        PriceListItemRequestDTO dto = new PriceListItemRequestDTO(priceListRequestDTO, price, true, currencyCodeRequestDTO, examRequestDTO);
        PriceListItem existingPriceListItem = new PriceListItem();

        PriceListResponseDTO priceListResponseDTO = new PriceListResponseDTO(1L, year);
        CurrencyResponseDTO currencyResponseDTO = new CurrencyResponseDTO(1L, "Dinar", "RSD");
        ExamResponseDTO examResponseDTO = new ExamResponseDTO(1L, "Matematika");
        PriceListItemResponseDTO responseDTO = new PriceListItemResponseDTO(id, priceListResponseDTO, price, true, currencyResponseDTO, examResponseDTO);

        when(priceListItemRepository.findById(id)).thenReturn(Optional.of(existingPriceListItem));
        when(priceListItemRepository.save(existingPriceListItem)).thenReturn(existingPriceListItem);
        when(mapper.map(existingPriceListItem)).thenReturn(responseDTO);

        PriceListItemResponseDTO resultDTO = priceListItemService.update(id, dto);

        verify(mapper).update(any(), any());
        verify(priceListItemRepository).save(existingPriceListItem);
        verify(mapper).map(existingPriceListItem);

        assertEquals(responseDTO, resultDTO);
    }

    @Test
    void testFindByPriceList() {
        Long priceListId = 1L;
        List<PriceListItem> priceListItems = List.of(new PriceListItem());

        when(priceListItemRepository.findByPriceList_Id(priceListId)).thenReturn(priceListItems);

        List<PriceListItem> result = priceListItemService.findByPriceList(priceListId);

        verify(priceListItemRepository).findByPriceList_Id(priceListId);
        assertEquals(priceListItems, result);
    }

    @Test
    void testGetByCriteria() {
        Year year = Year.now();
        PriceListItemCriteriaRequestDTO criteriaDTO = new PriceListItemCriteriaRequestDTO(year, true, "Matematika", "RSD");
        PriceListItem mockPriceListItem = new PriceListItem();

        BigDecimal price = new BigDecimal("100.00");
        PriceListResponseDTO priceListResponseDTO = new PriceListResponseDTO(1L, year);
        CurrencyResponseDTO currencyResponseDTO = new CurrencyResponseDTO(1L, "Dinar", "RSD");
        ExamResponseDTO examResponseDTO = new ExamResponseDTO(1L, "Matematika");
        PriceListItemResponseDTO expectedResponseDTO = new PriceListItemResponseDTO(1L, priceListResponseDTO, price, true, currencyResponseDTO, examResponseDTO);

        when(mapper.criteria(criteriaDTO)).thenReturn(mockPriceListItem);
        when(priceListItemRepository.findByExamAndPriceListAndPrivilegedAndCurrency(
                any(), any(), anyBoolean(), any()
        )).thenReturn(Optional.of(mockPriceListItem));
        when(mapper.map(mockPriceListItem)).thenReturn(expectedResponseDTO);

        PriceListItemResponseDTO actualResponseDTO = priceListItemService.getByCriteria(criteriaDTO);

        verify(mapper).criteria(criteriaDTO);
        verify(priceListItemRepository).findByExamAndPriceListAndPrivilegedAndCurrency(
                any(), any(), anyBoolean(), any()
        );
        verify(mapper).map(mockPriceListItem);

        assertEquals(expectedResponseDTO, actualResponseDTO);
    }

    @Test
    void testGetByCriteriaWhenNotFound() {
        Year year = Year.now();
        PriceListItemCriteriaRequestDTO criteriaDTO = new PriceListItemCriteriaRequestDTO(year, true, "Matematika", "RSD");
        PriceListItem mockCriteria = mock(PriceListItem.class);

        when(mapper.criteria(criteriaDTO)).thenReturn(mockCriteria);
        when(priceListItemRepository.findByExamAndPriceListAndPrivilegedAndCurrency(
                any(), any(), anyBoolean(), any()
        )).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> priceListItemService.getByCriteria(criteriaDTO));

        verify(mapper).criteria(criteriaDTO);
        verify(priceListItemRepository).findByExamAndPriceListAndPrivilegedAndCurrency(
                any(), any(), anyBoolean(), any()
        );
    }

}

package rs.ac.bg.fon.silab.mock_exam.domain.pricelist.service;

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

import rs.ac.bg.fon.silab.mock_exam.domain.pricelist.dto.PriceListRequestDTO;
import rs.ac.bg.fon.silab.mock_exam.domain.pricelist.dto.PriceListResponseDTO;
import rs.ac.bg.fon.silab.mock_exam.domain.pricelist.entity.PriceList;
import rs.ac.bg.fon.silab.mock_exam.domain.pricelist.mapper.PriceListMapper;
import rs.ac.bg.fon.silab.mock_exam.domain.pricelist.repository.PriceListRepository;
import rs.ac.bg.fon.silab.mock_exam.infrastructure.exception.EntityNotFoundException;

import java.time.Year;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class PriceListServiceImplTest {

    @Mock
    private PriceListRepository priceListRepository;

    @Mock
    private PriceListMapper mapper;

    @InjectMocks
    private PriceListServiceImpl priceListService;

    @Test
    void testFindByYear() {
        Year year = Year.now();
        PriceList mockPriceList = new PriceList();
        when(priceListRepository.findByYear(year)).thenReturn(mockPriceList);

        PriceList result = priceListService.findByYear(year);
        assertEquals(mockPriceList, result);
    }

    @Test
    void testSave() {
        Year year = Year.now();
        PriceListRequestDTO dto = new PriceListRequestDTO(year);
        PriceList mappedPriceList = new PriceList();
        PriceList savedPriceList = new PriceList();
        PriceListResponseDTO responseDTO = new PriceListResponseDTO(1L, year);

        when(mapper.map(dto)).thenReturn(mappedPriceList);
        when(priceListRepository.save(mappedPriceList)).thenReturn(savedPriceList);
        when(mapper.map(savedPriceList)).thenReturn(responseDTO);

        PriceListResponseDTO resultDTO = priceListService.save(dto);

        verify(mapper).map(dto);
        verify(priceListRepository).save(mappedPriceList);
        verify(mapper).map(savedPriceList);

        assertEquals(responseDTO, resultDTO);
    }

    @Test
    void testGetByIdWhenPriceListExists() {
        Long id = 1L;
        Year year = Year.now();
        PriceList mockPriceList = new PriceList();
        PriceListResponseDTO expectedResponseDTO = new PriceListResponseDTO(id,year);

        when(priceListRepository.findById(id)).thenReturn(Optional.of(mockPriceList));
        when(mapper.map(mockPriceList)).thenReturn(expectedResponseDTO);

        PriceListResponseDTO actualResponseDTO = priceListService.getById(id);

        verify(priceListRepository).findById(id);
        verify(mapper).map(mockPriceList);

        assertEquals(expectedResponseDTO, actualResponseDTO);
    }

    @Test
    void testGetByIdWhenPriceListDoesNotExist() {
        Long id = 1L;
        when(priceListRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> priceListService.getById(id));
    }

    @Test
    void testGet() {
        Pageable pageable = mock(Pageable.class);
        PriceList mockPriceList = new PriceList();
        Year year = Year.now();
        PriceListResponseDTO mockDTO = new PriceListResponseDTO(1L, year);
        Page<PriceList> page = new PageImpl<>(List.of(new PriceList()));

        when(priceListRepository.findAll(pageable)).thenReturn(page);
        when(mapper.map(mockPriceList)).thenReturn(mockDTO);

        Page<PriceListResponseDTO> resultPage = priceListService.get(pageable);

        verify(mapper, times(page.getContent().size())).map(any(PriceList.class));

        assertNotNull(resultPage);
        assertEquals(1, resultPage.getContent().size());
        assertEquals(mockDTO, resultPage.getContent().get(0));
    }

    @Test
    void testDeleteWhenPriceListExists() {
        Long id = 1L;
        when(priceListRepository.existsById(id)).thenReturn(true);

        priceListService.delete(id);

        verify(priceListRepository).deleteById(id);
    }

    @Test
    void testDeleteWhenPriceListDoesNotExist() {
        Long id = 1L;
        when(priceListRepository.existsById(id)).thenReturn(false);

        assertThrows(EntityNotFoundException.class, () -> priceListService.delete(id));

        verify(priceListRepository, never()).deleteById(id);
    }

    @Test
    void testUpdateWhenPriceListExists() {
        Long id = 1L;
        Year year = Year.now();
        PriceListRequestDTO dto = new PriceListRequestDTO(year);
        PriceList existingPriceList = new PriceList();
        PriceListResponseDTO responseDTO = new PriceListResponseDTO(1L, year);

        when(priceListRepository.findById(id)).thenReturn(Optional.of(existingPriceList));
        when(priceListRepository.save(existingPriceList)).thenReturn(existingPriceList);
        when(mapper.map(existingPriceList)).thenReturn(responseDTO);

        PriceListResponseDTO resultDTO = priceListService.update(id, dto);

        verify(mapper).update(existingPriceList, dto);
        verify(priceListRepository).save(existingPriceList);
        verify(mapper).map(existingPriceList);

        assertEquals(responseDTO, resultDTO);
    }

    @Test
    void testUpdateWhenPriceListDoesNotExist() {
        Long id = 1L;
        Year year = Year.now();
        PriceListRequestDTO dto = new PriceListRequestDTO(year);

        when(priceListRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> {
            priceListService.update(id, dto);
        });

        verify(priceListRepository, never()).save(any());
    }
}

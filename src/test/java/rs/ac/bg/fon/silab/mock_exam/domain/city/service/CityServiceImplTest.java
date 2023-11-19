package rs.ac.bg.fon.silab.mock_exam.domain.city.service;

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

import rs.ac.bg.fon.silab.mock_exam.domain.city.dto.CityRequestDTO;
import rs.ac.bg.fon.silab.mock_exam.domain.city.dto.CityRequestUpdateDTO;
import rs.ac.bg.fon.silab.mock_exam.domain.city.dto.CityResponseDTO;
import rs.ac.bg.fon.silab.mock_exam.domain.city.entity.City;
import rs.ac.bg.fon.silab.mock_exam.domain.city.mapper.CityMapper;
import rs.ac.bg.fon.silab.mock_exam.domain.city.repository.CityRepository;
import rs.ac.bg.fon.silab.mock_exam.infrastructure.exception.EntityNotFoundException;

import java.util.Optional;
import java.util.List;

@ExtendWith(MockitoExtension.class)
public class CityServiceImplTest {

    @Mock
    private CityRepository cityRepository;

    @Mock
    private CityMapper mapper;

    @InjectMocks
    private CityServiceImpl cityService;

    @Test
    void testGetByIdWhenCityExists() {
        Long zipCode = 11000L;
        City mockCity = new City();
        when(cityRepository.findById(zipCode)).thenReturn(Optional.of(mockCity));

        City result = cityService.getById(zipCode);
        assertEquals(mockCity, result);
    }

    @Test
    void testGetByIdWhenCityDoesNotExist() {
        Long zipCode = 11000L;
        when(cityRepository.findById(zipCode)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> cityService.getById(zipCode));
    }

    @Test
    void testSave() {
        CityRequestDTO dto = new CityRequestDTO(11000L, "Beograd");
        City mappedCity = new City(11000L, "Beograd");
        City savedCity = new City(11000L, "Beograd");
        CityResponseDTO responseDTO = new CityResponseDTO(11000L, "Beograd");

        when(mapper.map(dto)).thenReturn(mappedCity);
        when(cityRepository.save(mappedCity)).thenReturn(savedCity);
        when(mapper.map(savedCity)).thenReturn(responseDTO);

        CityResponseDTO resultDTO = cityService.save(dto);

        verify(mapper).map(dto);
        verify(cityRepository).save(mappedCity);
        verify(mapper).map(savedCity);

        assertEquals(responseDTO, resultDTO);
    }

    @Test
    void testGetByZipCodeWhenCityExists() {
        Long zipCode = 11000L;
        City mockCity = new City();
        CityResponseDTO expectedResponseDTO = new CityResponseDTO(11000L, "Beograd");

        when(cityRepository.findById(zipCode)).thenReturn(Optional.of(mockCity));
        when(mapper.map(mockCity)).thenReturn(expectedResponseDTO);

        CityResponseDTO actualResponseDTO = cityService.getByZipCode(zipCode);

        verify(cityRepository).findById(zipCode);
        verify(mapper).map(mockCity);

        assertEquals(expectedResponseDTO, actualResponseDTO);
    }

    @Test
    void testGetByZipCodeWhenCityDoesNotExist() {
        Long zipCode = 11000L;
        when(cityRepository.findById(zipCode)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> cityService.getByZipCode(zipCode));

        verify(cityRepository).findById(zipCode);
        verify(mapper, never()).map(any(City.class));
    }

    @Test
    void testGet() {
        Pageable pageable = mock(Pageable.class);
        City mockCity = new City();
        CityResponseDTO mockDTO = new CityResponseDTO(11000L, "Beograd");
        Page<City> page = new PageImpl<>(List.of(new City()));

        when(cityRepository.findAll(pageable)).thenReturn(page);
        when(mapper.map(mockCity)).thenReturn(mockDTO);

        Page<CityResponseDTO> resultPage = cityService.get(pageable);

        verify(mapper, times(page.getContent().size())).map(any(City.class));

        assertNotNull(resultPage);
        assertEquals(1, resultPage.getContent().size());
        assertEquals(mockDTO, resultPage.getContent().get(0));
    }

    @Test
    void testDeleteWhenCityExists() {
        Long zipCode = 11000L;
        when(cityRepository.existsById(zipCode)).thenReturn(true);

        cityService.delete(zipCode);

        verify(cityRepository).deleteById(zipCode);
    }

    @Test
    void testDeleteWhenCityDoesNotExist() {
        Long zipCode = 10001L;
        when(cityRepository.existsById(zipCode)).thenReturn(false);

        assertThrows(EntityNotFoundException.class, () -> cityService.delete(zipCode));

        verify(cityRepository, never()).deleteById(zipCode);
    }

    @Test
    void testUpdateWhenCityExists() {
        Long zipCode = 10001L;
        CityRequestUpdateDTO dto = new CityRequestUpdateDTO("CityName");
        City existingCity = new City();
        CityResponseDTO responseDTO = new CityResponseDTO(10001L, "CityName");

        when(cityRepository.findById(zipCode)).thenReturn(Optional.of(existingCity));
        when(cityRepository.save(existingCity)).thenReturn(existingCity);
        when(mapper.map(existingCity)).thenReturn(responseDTO);

        CityResponseDTO resultDTO = cityService.update(zipCode, dto);

        verify(mapper).update(any(), any());
        verify(cityRepository).save(existingCity);
        verify(mapper).map(existingCity);

        assertEquals(responseDTO, resultDTO);
    }

    @Test
    void testUpdateWhenCityDoesNotExist() {
        Long zipCode = 11000L;
        CityRequestUpdateDTO dto = new CityRequestUpdateDTO("New City Name");

        when(cityRepository.findById(zipCode)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> cityService.update(zipCode, dto));

        verify(cityRepository).findById(zipCode);
        verify(mapper, never()).update(any(City.class), any(CityRequestUpdateDTO.class));
        verify(cityRepository, never()).save(any(City.class));
    }

    @Test
    void testGetAll() {
        List<City> cities = List.of(new City(11000L, "City1"), new City(12000L, "City2"));
        List<CityResponseDTO> cityResponseDTOs = cities.stream()
                .map(city -> new CityResponseDTO(city.getZipCode(), city.getName()))
                .toList();

        when(cityRepository.findAll()).thenReturn(cities);
        when(mapper.map(any(City.class))).thenAnswer(invocation -> {
            City city = invocation.getArgument(0);
            return new CityResponseDTO(city.getZipCode(), city.getName());
        });

        List<CityResponseDTO> resultDTOs = cityService.getAll();

        verify(cityRepository).findAll();
        verify(mapper, times(cities.size())).map(any(City.class));
        assertEquals(cityResponseDTOs.size(), resultDTOs.size());
        for (int i = 0; i < cityResponseDTOs.size(); i++) {
            assertEquals(cityResponseDTOs.get(i), resultDTOs.get(i));
        }
    }
}

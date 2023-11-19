package rs.ac.bg.fon.silab.mock_exam.domain.school.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.ScopedMock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import rs.ac.bg.fon.silab.mock_exam.domain.city.dto.CityResponseDTO;
import rs.ac.bg.fon.silab.mock_exam.domain.city.dto.CitySimpleRequestDTO;
import rs.ac.bg.fon.silab.mock_exam.domain.city.entity.City;
import rs.ac.bg.fon.silab.mock_exam.domain.school.dto.SchoolRequestDTO;
import rs.ac.bg.fon.silab.mock_exam.domain.school.dto.SchoolResponseDTO;
import rs.ac.bg.fon.silab.mock_exam.domain.school.dto.SchoolUpdateRequestDTO;
import rs.ac.bg.fon.silab.mock_exam.domain.school.entity.School;
import rs.ac.bg.fon.silab.mock_exam.domain.school.mapper.SchoolMapper;
import rs.ac.bg.fon.silab.mock_exam.domain.school.repository.SchoolRepository;
import rs.ac.bg.fon.silab.mock_exam.domain.typeofschool.dto.TypeOfSchoolRequestDTO;
import rs.ac.bg.fon.silab.mock_exam.domain.typeofschool.dto.TypeOfSchoolResponseDTO;
import rs.ac.bg.fon.silab.mock_exam.domain.typeofschool.entity.TypeOfSchool;
import rs.ac.bg.fon.silab.mock_exam.infrastructure.exception.EntityNotFoundException;

import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class SchoolServiceImplTest {

    @Mock
    private SchoolRepository schoolRepository;

    @Mock
    private SchoolMapper mapper;

    @InjectMocks
    private SchoolServiceImpl schoolService;

    @Test
    void testFindByIdWhenSchoolExists() {
        Long code = 1234567L;
        School mockSchool = new School();
        when(schoolRepository.findById(code)).thenReturn(Optional.of(mockSchool));

        School result = schoolService.findById(code);
        assertEquals(mockSchool, result);
    }

    @Test
    void testFindByIdWhenSchoolDoesNotExist() {
        Long code = 1234567L;
        when(schoolRepository.findById(code)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> schoolService.findById(code));
    }

    @Test
    void testSave() {
        TypeOfSchoolRequestDTO typeOfSchool = new TypeOfSchoolRequestDTO("test");
        CitySimpleRequestDTO citySimpleRequestDTO = new CitySimpleRequestDTO(11000L);
        SchoolRequestDTO dto = new SchoolRequestDTO(1234567L, "Test skola", typeOfSchool,citySimpleRequestDTO);

        School mappedSchool = new School();
        School savedSchool = new School();

        CityResponseDTO cityResponseDTO = new CityResponseDTO(11000L, "Beograd");
        TypeOfSchoolResponseDTO typeOfSchoolResponseDTO = new TypeOfSchoolResponseDTO(1L,"test");
        SchoolResponseDTO responseDTO = new SchoolResponseDTO(1234567L, "Test skola", typeOfSchoolResponseDTO, cityResponseDTO);

        when(mapper.map(dto)).thenReturn(mappedSchool);
        when(schoolRepository.save(mappedSchool)).thenReturn(savedSchool);
        when(mapper.map(savedSchool)).thenReturn(responseDTO);

        SchoolResponseDTO resultDTO = schoolService.save(dto);

        verify(mapper).map(dto);
        verify(schoolRepository).save(mappedSchool);
        verify(mapper).map(savedSchool);

        assertEquals(responseDTO, resultDTO);
    }

    @Test
    void testGetByIdWhenSchoolExists() {
        Long code = 1234567L;
        School mockSchool = new School();

        CityResponseDTO cityResponseDTO = new CityResponseDTO(11000L, "Beograd");
        TypeOfSchoolResponseDTO typeOfSchoolResponseDTO = new TypeOfSchoolResponseDTO(1L,"test");
        SchoolResponseDTO expectedResponseDTO = new SchoolResponseDTO(1234567L, "Test skola", typeOfSchoolResponseDTO, cityResponseDTO);

        when(schoolRepository.findById(code)).thenReturn(Optional.of(mockSchool));
        when(mapper.map(mockSchool)).thenReturn(expectedResponseDTO);

        SchoolResponseDTO actualResponseDTO = schoolService.getById(code);

        verify(schoolRepository).findById(code);
        verify(mapper).map(mockSchool);

        assertEquals(expectedResponseDTO, actualResponseDTO);
    }

    @Test
    void testGetByIdWhenSchoolDoesNotExist() {
        Long code = 12345L;
        when(schoolRepository.findById(code)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> schoolService.getById(code));
    }

    @Test
    void testGet() {
        Pageable pageable = mock(Pageable.class);
        School mockSchool = new School();

        CityResponseDTO cityResponseDTO = new CityResponseDTO(11000L, "Beograd");
        TypeOfSchoolResponseDTO typeOfSchoolResponseDTO = new TypeOfSchoolResponseDTO(1L,"test");
        SchoolResponseDTO mockDTO = new SchoolResponseDTO(1234567L, "Test skola", typeOfSchoolResponseDTO, cityResponseDTO);

        Page<School> page = new PageImpl<>(List.of(mockSchool));

        when(schoolRepository.findAll(pageable)).thenReturn(page);
        when(mapper.map(mockSchool)).thenReturn(mockDTO);

        Page<SchoolResponseDTO> resultPage = schoolService.get(pageable);

        verify(schoolRepository).findAll(pageable);
        verify(mapper, times(page.getContent().size())).map(any(School.class));

        assertNotNull(resultPage);
        assertEquals(1, resultPage.getContent().size());
        assertEquals(mockDTO, resultPage.getContent().get(0));
    }

    @Test
    void testDeleteWhenSchoolExists() {
        Long code = 12345L;
        when(schoolRepository.existsById(code)).thenReturn(true);

        schoolService.delete(code);

        verify(schoolRepository).deleteById(code);
    }

    @Test
    void testDeleteWhenSchoolDoesNotExist() {
        Long code = 12345L;
        when(schoolRepository.existsById(code)).thenReturn(false);

        assertThrows(EntityNotFoundException.class, () -> schoolService.delete(code));

        verify(schoolRepository, never()).deleteById(code);
    }

    @Test
    void testUpdateWhenSchoolExists() {
        Long code = 12345L;
        SchoolUpdateRequestDTO dto = new SchoolUpdateRequestDTO("Test skola");
        School existingSchool = new School();

        CityResponseDTO cityResponseDTO = new CityResponseDTO(11000L, "Beograd");
        TypeOfSchoolResponseDTO typeOfSchoolResponseDTO = new TypeOfSchoolResponseDTO(1L,"test");
        SchoolResponseDTO responseDTO = new SchoolResponseDTO(1234567L, "Test skola", typeOfSchoolResponseDTO, cityResponseDTO);


        when(schoolRepository.findById(code)).thenReturn(Optional.of(existingSchool));
        when(schoolRepository.save(existingSchool)).thenReturn(existingSchool);
        when(mapper.map(existingSchool)).thenReturn(responseDTO);

        SchoolResponseDTO resultDTO = schoolService.update(code, dto);

        verify(mapper).update(existingSchool, dto);
        verify(schoolRepository).save(existingSchool);
        verify(mapper).map(existingSchool);

        assertEquals(responseDTO, resultDTO);
    }

    @Test
    void testUpdateWhenSchoolDoesNotExist() {
        Long nonExistentCode = 12345L;
        SchoolUpdateRequestDTO dto = new SchoolUpdateRequestDTO("Test skola");

        when(schoolRepository.findById(nonExistentCode)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> schoolService.update(nonExistentCode, dto));

        verify(schoolRepository, never()).save(any(School.class));
        verify(mapper, never()).update(any(School.class), any(SchoolUpdateRequestDTO.class));
    }

    @Test
    void testGetAll() {
        TypeOfSchool typeOfSchool1 = new TypeOfSchool("test1");
        City city1 = new City(11000L, "Beograd");
        School school1 = new School(1234567L, "Test skola1", typeOfSchool1, city1);

        TypeOfSchool typeOfSchool2 = new TypeOfSchool("test2");
        City city2 = new City(21000L, "Nis");
        School school2 = new School(7654321L, "Test skola2", typeOfSchool2, city2);

        List<School> schools = List.of(school1, school2);

        CityResponseDTO cityResponseDTO1 = new CityResponseDTO(11000L, "Beograd");
        TypeOfSchoolResponseDTO typeOfSchoolResponseDTO1 = new TypeOfSchoolResponseDTO(1L,"test1");
        SchoolResponseDTO mockDTO1 = new SchoolResponseDTO(1234567L, "Test skola1", typeOfSchoolResponseDTO1, cityResponseDTO1);

        CityResponseDTO cityResponseDTO2 = new CityResponseDTO(21000L, "Nis");
        TypeOfSchoolResponseDTO typeOfSchoolResponseDTO2 = new TypeOfSchoolResponseDTO(2L, "test2");
        SchoolResponseDTO mockDTO2 = new SchoolResponseDTO(7654321L, "Test skola2", typeOfSchoolResponseDTO2, cityResponseDTO2);


        when(schoolRepository.findAll()).thenReturn(schools);
        when(mapper.map(schools.get(0))).thenReturn(mockDTO1);
        when(mapper.map(schools.get(1))).thenReturn(mockDTO2);

        List<SchoolResponseDTO> result = schoolService.getAll();

        verify(schoolRepository).findAll();
        verify(mapper).map(schools.get(0));
        verify(mapper).map(schools.get(1));
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals(mockDTO1, result.get(0));
        assertEquals(mockDTO2, result.get(1));
    }
}

package rs.ac.bg.fon.silab.mock_exam.domain.school.service;

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

import rs.ac.bg.fon.silab.mock_exam.domain.city.dto.CityResponseDTO;
import rs.ac.bg.fon.silab.mock_exam.domain.city.dto.CitySimpleRequestDTO;
import rs.ac.bg.fon.silab.mock_exam.domain.school.dto.SchoolRequestDTO;
import rs.ac.bg.fon.silab.mock_exam.domain.school.dto.SchoolResponseDTO;
import rs.ac.bg.fon.silab.mock_exam.domain.school.dto.SchoolUpdateRequestDTO;
import rs.ac.bg.fon.silab.mock_exam.domain.school.entity.School;
import rs.ac.bg.fon.silab.mock_exam.domain.school.mapper.SchoolMapper;
import rs.ac.bg.fon.silab.mock_exam.domain.school.repository.SchoolRepository;
import rs.ac.bg.fon.silab.mock_exam.domain.typeofschool.dto.TypeOfSchoolRequestDTO;
import rs.ac.bg.fon.silab.mock_exam.domain.typeofschool.dto.TypeOfSchoolResponseDTO;
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
    void testUpdate() {
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
    void testGetAll() {
        List<School> schools = List.of(new School());

        CityResponseDTO cityResponseDTO = new CityResponseDTO(11000L, "Beograd");
        TypeOfSchoolResponseDTO typeOfSchoolResponseDTO = new TypeOfSchoolResponseDTO(1L,"test");
        SchoolResponseDTO mockDTO = new SchoolResponseDTO(1234567L, "Test skola", typeOfSchoolResponseDTO, cityResponseDTO);


        when(schoolRepository.findAll()).thenReturn(schools);
        when(mapper.map(any(School.class))).thenReturn(mockDTO);

        List<SchoolResponseDTO> result = schoolService.getAll();

        verify(schoolRepository).findAll();
        verify(mapper, times(schools.size())).map(any(School.class));

        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertEquals(mockDTO, result.get(0));
    }
}

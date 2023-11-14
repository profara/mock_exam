
package rs.ac.bg.fon.silab.mock_exam.domain.typeofschool.service;

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

import rs.ac.bg.fon.silab.mock_exam.domain.typeofschool.dto.TypeOfSchoolRequestDTO;
import rs.ac.bg.fon.silab.mock_exam.domain.typeofschool.dto.TypeOfSchoolResponseDTO;
import rs.ac.bg.fon.silab.mock_exam.domain.typeofschool.entity.TypeOfSchool;
import rs.ac.bg.fon.silab.mock_exam.domain.typeofschool.mapper.TypeOfSchoolMapper;
import rs.ac.bg.fon.silab.mock_exam.domain.typeofschool.repository.TypeOfSchoolRepository;
import rs.ac.bg.fon.silab.mock_exam.infrastructure.exception.EntityNotFoundException;

import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class TypeOfSchoolServiceImplTest {

    @Mock
    private TypeOfSchoolRepository typeOfSchoolRepository;

    @Mock
    private TypeOfSchoolMapper mapper;

    @InjectMocks
    private TypeOfSchoolServiceImpl typeOfSchoolService;

    @Test
    void testFindByName() {
        String name = "Gimnazija";
        TypeOfSchool mockTypeOfSchool = new TypeOfSchool();
        when(typeOfSchoolRepository.findByName(name)).thenReturn(mockTypeOfSchool);

        TypeOfSchool result = typeOfSchoolService.findByName(name);

        verify(typeOfSchoolRepository).findByName(name);
        assertEquals(mockTypeOfSchool, result);
    }

    @Test
    void testSave() {
        TypeOfSchoolRequestDTO dto = new TypeOfSchoolRequestDTO("Gimnazija");
        TypeOfSchool mappedTypeOfSchool = new TypeOfSchool("Gimnazija");
        TypeOfSchool savedTypeOfSchool = new TypeOfSchool("Gimnazija");
        TypeOfSchoolResponseDTO responseDTO = new TypeOfSchoolResponseDTO(1L, "Gimnazija");

        when(mapper.map(dto)).thenReturn(mappedTypeOfSchool);
        when(typeOfSchoolRepository.save(mappedTypeOfSchool)).thenReturn(savedTypeOfSchool);
        when(mapper.map(savedTypeOfSchool)).thenReturn(responseDTO);

        TypeOfSchoolResponseDTO resultDTO = typeOfSchoolService.save(dto);

        verify(mapper).map(dto);
        verify(typeOfSchoolRepository).save(mappedTypeOfSchool);
        verify(mapper).map(savedTypeOfSchool);

        assertEquals(responseDTO, resultDTO);
    }

    @Test
    void testGetByIdWhenTypeOfSchoolExists() {
        Long id = 1L;
        TypeOfSchool mockTypeOfSchool = new TypeOfSchool();
        TypeOfSchoolResponseDTO expectedResponseDTO = new TypeOfSchoolResponseDTO(id, "Gimnazija");

        when(typeOfSchoolRepository.findById(id)).thenReturn(Optional.of(mockTypeOfSchool));
        when(mapper.map(mockTypeOfSchool)).thenReturn(expectedResponseDTO);

        TypeOfSchoolResponseDTO actualResponseDTO = typeOfSchoolService.getById(id);

        verify(typeOfSchoolRepository).findById(id);
        verify(mapper).map(mockTypeOfSchool);

        assertEquals(expectedResponseDTO, actualResponseDTO);
    }

    @Test
    void testGetByIdWhenTypeOfSchoolDoesNotExist() {
        Long id = 1L;
        when(typeOfSchoolRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> typeOfSchoolService.getById(id));
    }

    @Test
    void testGet() {
        Pageable pageable = mock(Pageable.class);
        TypeOfSchool mockTypeOfSchool = new TypeOfSchool();
        TypeOfSchoolResponseDTO mockDTO = new TypeOfSchoolResponseDTO(1L, "Gimnazija");
        Page<TypeOfSchool> page = new PageImpl<>(List.of(mockTypeOfSchool));

        when(typeOfSchoolRepository.findAll(pageable)).thenReturn(page);
        when(mapper.map(mockTypeOfSchool)).thenReturn(mockDTO);

        Page<TypeOfSchoolResponseDTO> resultPage = typeOfSchoolService.get(pageable);

        verify(typeOfSchoolRepository).findAll(pageable);
        verify(mapper, times(page.getContent().size())).map(any(TypeOfSchool.class));

        assertNotNull(resultPage);
        assertEquals(1, resultPage.getContent().size());
        assertEquals(mockDTO, resultPage.getContent().get(0));
    }

    @Test
    void testDeleteWhenTypeOfSchoolExists() {
        Long id = 1L;
        when(typeOfSchoolRepository.existsById(id)).thenReturn(true);

        typeOfSchoolService.delete(id);

        verify(typeOfSchoolRepository).deleteById(id);
    }

    @Test
    void testDeleteWhenTypeOfSchoolDoesNotExist() {
        Long id = 1L;
        when(typeOfSchoolRepository.existsById(id)).thenReturn(false);

        assertThrows(EntityNotFoundException.class, () -> typeOfSchoolService.delete(id));

        verify(typeOfSchoolRepository, never()).deleteById(id);
    }

    @Test
    void testUpdate() {
        Long id = 1L;
        TypeOfSchoolRequestDTO dto = new TypeOfSchoolRequestDTO("Ekonomska");
        TypeOfSchool existingTypeOfSchool = new TypeOfSchool();
        TypeOfSchoolResponseDTO responseDTO = new TypeOfSchoolResponseDTO(id, "Ekonomska");

        when(typeOfSchoolRepository.findById(id)).thenReturn(Optional.of(existingTypeOfSchool));
        when(typeOfSchoolRepository.save(existingTypeOfSchool)).thenReturn(existingTypeOfSchool);
        when(mapper.map(existingTypeOfSchool)).thenReturn(responseDTO);

        TypeOfSchoolResponseDTO resultDTO = typeOfSchoolService.update(id, dto);

        verify(mapper).update(existingTypeOfSchool, dto);
        verify(typeOfSchoolRepository).save(existingTypeOfSchool);
        verify(mapper).map(existingTypeOfSchool);

        assertEquals(responseDTO, resultDTO);
    }
}

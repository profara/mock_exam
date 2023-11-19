package rs.ac.bg.fon.silab.mock_exam.domain.exam.service;

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

import rs.ac.bg.fon.silab.mock_exam.domain.exam.dto.ExamRequestDTO;
import rs.ac.bg.fon.silab.mock_exam.domain.exam.dto.ExamResponseDTO;
import rs.ac.bg.fon.silab.mock_exam.domain.exam.entity.Exam;
import rs.ac.bg.fon.silab.mock_exam.domain.exam.mapper.ExamMapper;
import rs.ac.bg.fon.silab.mock_exam.domain.exam.repository.ExamRepository;
import rs.ac.bg.fon.silab.mock_exam.infrastructure.exception.EntityNotFoundException;

import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class ExamServiceImplTest {

    @Mock
    private ExamRepository examRepository;

    @Mock
    private ExamMapper mapper;

    @InjectMocks
    private ExamServiceImpl examService;

    @Test
    void testFindByName() {
        String name = "Matematika";
        Exam mockExam = new Exam();
        when(examRepository.findByName(name)).thenReturn(mockExam);

        Exam result = examService.findByName(name);
        assertEquals(mockExam, result);
    }

    @Test
    void testFindByIdWhenExamExists() {
        Long id = 1L;
        Exam mockExam = new Exam();
        when(examRepository.findById(id)).thenReturn(Optional.of(mockExam));

        Exam result = examService.findById(id);
        assertEquals(mockExam, result);
    }

    @Test
    void testFindByIdWhenExamDoesNotExist() {
        Long id = 1L;
        when(examRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> examService.findById(id));
    }

    @Test
    void testSave() {
        ExamRequestDTO dto = new ExamRequestDTO("Matematika");
        Exam mappedExam = new Exam();
        Exam savedExam = new Exam();
        ExamResponseDTO responseDTO = new ExamResponseDTO(1L, "Matematika");

        when(mapper.map(dto)).thenReturn(mappedExam);
        when(examRepository.save(mappedExam)).thenReturn(savedExam);
        when(mapper.map(savedExam)).thenReturn(responseDTO);

        ExamResponseDTO resultDTO = examService.save(dto);

        verify(mapper).map(dto);
        verify(examRepository).save(mappedExam);
        verify(mapper).map(savedExam);

        assertEquals(responseDTO, resultDTO);
    }

    @Test
    void testGetByIdWhenExamExists() {
        Long id = 1L;
        Exam mockExam = new Exam();
        ExamResponseDTO expectedResponseDTO = new ExamResponseDTO(1L, "Matematika");

        when(examRepository.findById(id)).thenReturn(Optional.of(mockExam));
        when(mapper.map(mockExam)).thenReturn(expectedResponseDTO);

        ExamResponseDTO actualResponseDTO = examService.getById(id);

        verify(examRepository).findById(id);
        verify(mapper).map(mockExam);

        assertEquals(expectedResponseDTO, actualResponseDTO);
    }

    @Test
    void testGetByIdWhenExamDoesNotExist() {
        Long id = 1L;
        when(examRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> examService.getById(id));

        verify(examRepository).findById(id);
    }

    @Test
    void testGet() {
        Pageable pageable = mock(Pageable.class);
        Exam mockExam = new Exam();
        ExamResponseDTO mockDTO = new ExamResponseDTO(1L, "Matematika");
        Page<Exam> page = new PageImpl<>(List.of(new Exam()));

        when(examRepository.findAll(pageable)).thenReturn(page);
        when(mapper.map(mockExam)).thenReturn(mockDTO);

        Page<ExamResponseDTO> resultPage = examService.get(pageable);

        verify(mapper, times(page.getContent().size())).map(any(Exam.class));

        assertNotNull(resultPage);
        assertEquals(1, resultPage.getContent().size());
        assertEquals(mockDTO, resultPage.getContent().get(0));
    }

    @Test
    void testDeleteWhenExamExists() {
        Long id = 1L;
        when(examRepository.existsById(id)).thenReturn(true);

        examService.delete(id);

        verify(examRepository).deleteById(id);
    }

    @Test
    void testDeleteWhenExamDoesNotExist() {
        Long id = 1L;
        when(examRepository.existsById(id)).thenReturn(false);

        assertThrows(EntityNotFoundException.class, () -> examService.delete(id));

        verify(examRepository, never()).deleteById(id);
    }

    @Test
    void testUpdateWhenExamExists() {
        Long id = 1L;
        ExamRequestDTO dto = new ExamRequestDTO("Matematika");
        Exam existingExam = new Exam();
        ExamResponseDTO responseDTO = new ExamResponseDTO(1L, "Matematika");

        when(examRepository.findById(id)).thenReturn(Optional.of(existingExam));
        when(examRepository.save(existingExam)).thenReturn(existingExam);
        when(mapper.map(existingExam)).thenReturn(responseDTO);

        ExamResponseDTO resultDTO = examService.update(id, dto);

        verify(mapper).update(any(), any());
        verify(examRepository).save(existingExam);
        verify(mapper).map(existingExam);

        assertEquals(responseDTO, resultDTO);
    }

    @Test
    void testUpdateWhenExamDoesNotExist() {
        Long id = 1L;
        ExamRequestDTO dto = new ExamRequestDTO("Matematika");

        when(examRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> examService.update(id, dto));

        verify(examRepository).findById(id);
        verify(mapper, never()).update(any(Exam.class), any(ExamRequestDTO.class));
        verify(examRepository, never()).save(any(Exam.class));
    }
}

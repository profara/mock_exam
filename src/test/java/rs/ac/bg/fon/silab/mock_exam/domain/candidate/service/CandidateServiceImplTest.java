package rs.ac.bg.fon.silab.mock_exam.domain.candidate.service;

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

import rs.ac.bg.fon.silab.mock_exam.domain.candidate.dto.CandidateRequestDTO;
import rs.ac.bg.fon.silab.mock_exam.domain.candidate.dto.CandidateResponseDTO;
import rs.ac.bg.fon.silab.mock_exam.domain.candidate.entity.Candidate;
import rs.ac.bg.fon.silab.mock_exam.domain.candidate.mapper.CandidateMapper;
import rs.ac.bg.fon.silab.mock_exam.domain.candidate.repository.CandidateRepository;
import rs.ac.bg.fon.silab.mock_exam.infrastructure.exception.EntityNotFoundException;

import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class CandidateServiceImplTest {

    @Mock
    private CandidateRepository candidateRepository;

    @Mock
    private CandidateMapper mapper;

    @InjectMocks
    private CandidateServiceImpl candidateService;

    @Test
    void testFindByIdWhenCandidateExists() {
        Long id = 1L;
        Candidate mockCandidate = new Candidate();
        when(candidateRepository.findById(id)).thenReturn(Optional.of(mockCandidate));

        Candidate result = candidateService.findById(id);
        assertEquals(mockCandidate, result);
    }

    @Test
    void testFindByIdWhenCandidateDoesNotExist() {
        Long id = 1L;
        when(candidateRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> candidateService.findById(id));
    }

    @Test
    void testSave() {
        CandidateRequestDTO dto = new CandidateRequestDTO("John", "Doe", "123 St.", true, null, 100001L, 10001L);
        Candidate mappedCandidate = new Candidate();
        Candidate savedCandidate = new Candidate();
        CandidateResponseDTO responseDTO = new CandidateResponseDTO(1L, "John", "Doe", "123 St.", true, null, null, null);

        when(mapper.map(dto)).thenReturn(mappedCandidate);
        when(candidateRepository.save(mappedCandidate)).thenReturn(savedCandidate);
        when(mapper.map(savedCandidate)).thenReturn(responseDTO);

        CandidateResponseDTO resultDTO = candidateService.save(dto);

        verify(mapper).map(dto);
        verify(candidateRepository).save(mappedCandidate);
        verify(mapper).map(savedCandidate);

        assertEquals(responseDTO, resultDTO);
    }

    @Test
    void testGetByIdWhenCandidateExists() {
        Long id = 1L;
        Candidate mockCandidate = new Candidate();
        CandidateResponseDTO expectedResponseDTO = new CandidateResponseDTO(1L, "John", "Doe", "123 St.", true, null, null, null);

        when(candidateRepository.findById(id)).thenReturn(Optional.of(mockCandidate));
        when(mapper.map(mockCandidate)).thenReturn(expectedResponseDTO);

        CandidateResponseDTO actualResponseDTO = candidateService.getById(id);

        verify(candidateRepository).findById(id);
        verify(mapper).map(mockCandidate);

        assertEquals(expectedResponseDTO, actualResponseDTO);
    }

    @Test
    void testGet() {
        Pageable pageable = mock(Pageable.class);
        Candidate mockCandidate = new Candidate();
        CandidateResponseDTO mockDTO = new CandidateResponseDTO(1L, "John", "Doe", "123 St.", true, null, null, null);
        Page<Candidate> page = new PageImpl<>(List.of(new Candidate()));

        when(candidateRepository.findAll(pageable)).thenReturn(page);
        when(mapper.map(mockCandidate)).thenReturn(mockDTO);

        Page<CandidateResponseDTO> resultPage = candidateService.get(pageable);

        verify(mapper, times(page.getContent().size())).map(any(Candidate.class));

        assertNotNull(resultPage);
        assertEquals(1, resultPage.getContent().size());
        assertEquals(mockDTO, resultPage.getContent().get(0));
    }

    @Test
    void testDeleteWhenCandidateExists() {
        Long id = 1L;
        when(candidateRepository.existsById(id)).thenReturn(true);

        candidateService.delete(id);

        verify(candidateRepository).deleteById(id);
    }

    @Test
    void testDeleteWhenCandidateDoesNotExist() {
        Long id = 1L;
        when(candidateRepository.existsById(id)).thenReturn(false);

        assertThrows(EntityNotFoundException.class, () -> candidateService.delete(id));

        verify(candidateRepository, never()).deleteById(id);
    }

    @Test
    void testUpdate() {
        Long id = 1L;
        CandidateRequestDTO dto = new CandidateRequestDTO("John", "Doe", "123 St.", true, null, 100001L, 10001L);
        Candidate existingCandidate = new Candidate();
        CandidateResponseDTO responseDTO = new CandidateResponseDTO(1L, "John", "Doe", "123 St.", true, null, null, null);

        when(candidateRepository.findById(id)).thenReturn(Optional.of(existingCandidate));
        when(candidateRepository.save(existingCandidate)).thenReturn(existingCandidate);
        when(mapper.map(existingCandidate)).thenReturn(responseDTO);

        CandidateResponseDTO resultDTO = candidateService.update(id, dto);

        verify(mapper).update(any(), any());
        verify(candidateRepository).save(existingCandidate);
        verify(mapper).map(existingCandidate);

        assertEquals(responseDTO, resultDTO);
    }

    @Test
    void testGetByEmail() {
        String email = "john.doe@example.com";
        Candidate mockCandidate = new Candidate();
        CandidateResponseDTO expectedResponseDTO = new CandidateResponseDTO(1L, "John", "Doe", "123 St.", true, null, null, null);

        when(candidateRepository.findByUserProfile_Email(email)).thenReturn(Optional.of(mockCandidate));
        when(mapper.map(mockCandidate)).thenReturn(expectedResponseDTO);

        CandidateResponseDTO actualResponseDTO = candidateService.getByEmail(email);

        verify(candidateRepository).findByUserProfile_Email(email);
        verify(mapper).map(mockCandidate);

        assertEquals(expectedResponseDTO, actualResponseDTO);
    }
}

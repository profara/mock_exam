package rs.ac.bg.fon.silab.mock_exam.domain.candidate.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
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

    private final Long id = 1L;
    private Candidate mockCandidate;
    private CandidateRequestDTO requestDTO;
    private CandidateResponseDTO responseDTO;

    @BeforeEach
    void setUp(){
        mockCandidate = new Candidate();
        mockCandidate.setId(id);
        requestDTO = new CandidateRequestDTO("TestName", "TestSurname", "123 St.", true, null, 100001L, 10001L);
        responseDTO = new CandidateResponseDTO(id, "TestName", "TestSurname", "123 St.", true, null, null, null);
    }

    @Test
    void testFindByIdWhenCandidateExists() {
        when(candidateRepository.findById(id)).thenReturn(Optional.of(mockCandidate));

        Candidate result = candidateService.findById(id);
        assertEquals(mockCandidate, result);
    }

    @Test
    void testFindByIdWhenCandidateDoesNotExist() {
        when(candidateRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> candidateService.findById(id));
    }

    @Test
    void testSave() {
        Candidate mappedCandidate = new Candidate();
        Candidate savedCandidate = new Candidate();

        when(mapper.map(requestDTO)).thenReturn(mappedCandidate);
        when(candidateRepository.save(mappedCandidate)).thenReturn(savedCandidate);
        when(mapper.map(savedCandidate)).thenReturn(responseDTO);

        CandidateResponseDTO resultDTO = candidateService.save(requestDTO);

        verify(mapper).map(requestDTO);
        verify(candidateRepository).save(mappedCandidate);
        verify(mapper).map(savedCandidate);

        assertEquals(responseDTO, resultDTO);
    }

    @Test
    void testGetByIdWhenCandidateExists() {
        when(candidateRepository.findById(id)).thenReturn(Optional.of(mockCandidate));
        when(mapper.map(mockCandidate)).thenReturn(responseDTO);

        CandidateResponseDTO actualResponseDTO = candidateService.getById(id);

        verify(candidateRepository).findById(id);
        verify(mapper).map(mockCandidate);

        assertEquals(responseDTO, actualResponseDTO);
    }

    @Test
    void testGet() {
        Pageable pageable = mock(Pageable.class);
        Page<Candidate> page = new PageImpl<>(List.of(mockCandidate));

        when(candidateRepository.findAll(pageable)).thenReturn(page);
        when(mapper.map(mockCandidate)).thenReturn(responseDTO);

        Page<CandidateResponseDTO> resultPage = candidateService.get(pageable);

        verify(mapper, times(page.getContent().size())).map(any(Candidate.class));

        assertNotNull(resultPage);
        assertEquals(1, resultPage.getContent().size());
        assertEquals(responseDTO, resultPage.getContent().get(0));
    }

    @Test
    void testDeleteWhenCandidateExists() {
        when(candidateRepository.existsById(id)).thenReturn(true);

        candidateService.delete(id);

        verify(candidateRepository).deleteById(id);
    }

    @Test
    void testDeleteWhenCandidateDoesNotExist() {
        when(candidateRepository.existsById(id)).thenReturn(false);

        assertThrows(EntityNotFoundException.class, () -> candidateService.delete(id));

        verify(candidateRepository, never()).deleteById(id);
    }

    @Test
    void testUpdateWhenCandidateExists() {
        when(candidateRepository.findById(id)).thenReturn(Optional.of(mockCandidate));
        when(mapper.map(mockCandidate)).thenReturn(responseDTO);

        CandidateResponseDTO resultDTO = candidateService.update(id, requestDTO);

        verify(mapper).update(mockCandidate, requestDTO);
        verify(candidateRepository).save(mockCandidate);
        assertEquals(responseDTO, resultDTO);
    }

    @Test
    void testUpdateWhenCandidateDoesNotExist() {
        when(candidateRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> candidateService.update(id, requestDTO));
        verify(mapper, never()).update(any(Candidate.class), any(CandidateRequestDTO.class));
        verify(candidateRepository, never()).save(any(Candidate.class));
    }

    @Test
    void testGetByEmailWhenCandidateExists() {
        String email = "john.doe@example.com";

        when(candidateRepository.findByUserProfile_Email(email)).thenReturn(Optional.of(mockCandidate));
        when(mapper.map(mockCandidate)).thenReturn(responseDTO);

        CandidateResponseDTO actualResponseDTO = candidateService.getByEmail(email);

        verify(candidateRepository).findByUserProfile_Email(email);
        verify(mapper).map(mockCandidate);

        assertEquals(responseDTO, actualResponseDTO);
    }

    @Test
    void testGetByEmailWhenCandidateDoesNotExist() {
        String email = "john.doe@example.com";
        when(candidateRepository.findByUserProfile_Email(email)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> candidateService.getByEmail(email));
        verify(candidateRepository).findByUserProfile_Email(email);
        verify(mapper, never()).map(any(Candidate.class));
    }
}

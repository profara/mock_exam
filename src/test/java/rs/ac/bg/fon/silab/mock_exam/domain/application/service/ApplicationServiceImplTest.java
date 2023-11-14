package rs.ac.bg.fon.silab.mock_exam.domain.application.service;

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
import rs.ac.bg.fon.silab.mock_exam.domain.application.dto.ApplicationRequestDTO;
import rs.ac.bg.fon.silab.mock_exam.domain.application.dto.ApplicationResponseDTO;
import rs.ac.bg.fon.silab.mock_exam.domain.application.entity.Application;
import rs.ac.bg.fon.silab.mock_exam.domain.application.mapper.ApplicationMapper;
import rs.ac.bg.fon.silab.mock_exam.domain.application.repository.ApplicationRepository;
import rs.ac.bg.fon.silab.mock_exam.domain.candidate.entity.Candidate;
import rs.ac.bg.fon.silab.mock_exam.infrastructure.exception.EntityNotFoundException;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class ApplicationServiceImplTest {

    @Mock
    private ApplicationRepository applicationRepository;

    @Mock
    private ApplicationMapper mapper;

    @InjectMocks
    private ApplicationServiceImpl applicationService;

    @Test
    void testFindByIdWhenApplicationExists() {
        Long id = 1L;
        Application mockApplication = new Application();
        when(applicationRepository.findById(id)).thenReturn(Optional.of(mockApplication));

        Application result = applicationService.findById(id);
        assertEquals(mockApplication, result);
    }

    @Test
    void testFindByIdWhenApplicationDoesNotExist() {
        Long id = 1L;
        when(applicationRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> applicationService.findById(id));
    }

    @Test
    void testSave() {
        Date mockDate = new Date();
        boolean mockPrivileged = true;
        Long mockCandidateId = 1L;
        List<Long> mockAppointmentIds = List.of(1L, 2L);
        Candidate mockCandidate = new Candidate();
        mockCandidate.setId(mockCandidateId);

        ApplicationRequestDTO dto = new ApplicationRequestDTO(mockDate, mockPrivileged, mockCandidateId, mockAppointmentIds);
        Application mappedApplication = new Application();
        mappedApplication.setCandidate(mockCandidate);
        Application savedApplication = new Application();
        savedApplication.setCandidate(mockCandidate);
        ApplicationResponseDTO responseDTO = new ApplicationResponseDTO(1L, mockDate, mockPrivileged, null, null);

        when(mapper.map(dto)).thenReturn(mappedApplication);
        when(applicationRepository.save(mappedApplication)).thenReturn(savedApplication);
        when(mapper.map(savedApplication)).thenReturn(responseDTO);

        ApplicationResponseDTO resultDTO = applicationService.save(dto);

        verify(mapper).map(dto);
        verify(applicationRepository).save(mappedApplication);
        verify(mapper).map(savedApplication);

        assertEquals(responseDTO, resultDTO);
    }

    @Test
    void testGetByIdWhenApplicationExists() {
        Date mockDate = new Date();
        boolean mockPrivileged = true;
        Long id = 1L;
        Application mockApplication = new Application();
        ApplicationResponseDTO expectedResponseDTO = new ApplicationResponseDTO(1L, mockDate, mockPrivileged, null, null);

        when(applicationRepository.findById(id)).thenReturn(Optional.of(mockApplication));
        when(mapper.map(mockApplication)).thenReturn(expectedResponseDTO);

        ApplicationResponseDTO actualResponseDTO = applicationService.getById(id);

        verify(applicationRepository).findById(id);
        verify(mapper).map(mockApplication);

        assertEquals(expectedResponseDTO, actualResponseDTO);
    }

    @Test
    void testGet() {
        Date mockDate = new Date();
        boolean mockPrivileged = true;
        Pageable pageable = mock(Pageable.class);
        Application mockApplication = new Application();
        ApplicationResponseDTO mockDTO = new ApplicationResponseDTO(1L, mockDate, mockPrivileged, null, null);
        Page<Application> page = new PageImpl<>(List.of(new Application()));

        when(applicationRepository.findAll(pageable)).thenReturn(page);
        when(mapper.map(mockApplication)).thenReturn(mockDTO);

        Page<ApplicationResponseDTO> resultPage = applicationService.get(pageable);

        verify(mapper, times(page.getContent().size())).map(any(Application.class));

        assertNotNull(resultPage);
        assertEquals(1, resultPage.getContent().size());
        assertEquals(mockDTO, resultPage.getContent().get(0));
    }

    @Test
    void testDeleteWhenApplicationExists() {
        Long id = 1L;
        when(applicationRepository.existsById(id)).thenReturn(true);

        applicationService.delete(id);

        verify(applicationRepository).deleteById(id);
    }

    @Test
    void testDeleteWhenApplicationDoesNotExist() {
        Long id = 1L;
        when(applicationRepository.existsById(id)).thenReturn(false);

        assertThrows(EntityNotFoundException.class, () -> applicationService.delete(id));

        verify(applicationRepository, never()).deleteById(id);
    }
}


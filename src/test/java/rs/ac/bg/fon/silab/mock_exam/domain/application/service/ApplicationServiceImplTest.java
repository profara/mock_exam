package rs.ac.bg.fon.silab.mock_exam.domain.application.service;

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
import rs.ac.bg.fon.silab.mock_exam.domain.application.dto.ApplicationRequestDTO;
import rs.ac.bg.fon.silab.mock_exam.domain.application.dto.ApplicationResponseDTO;
import rs.ac.bg.fon.silab.mock_exam.domain.application.entity.Application;
import rs.ac.bg.fon.silab.mock_exam.domain.application.exception.DuplicateAppointmentApplicationException;
import rs.ac.bg.fon.silab.mock_exam.domain.application.mapper.ApplicationMapper;
import rs.ac.bg.fon.silab.mock_exam.domain.application.repository.ApplicationRepository;
import rs.ac.bg.fon.silab.mock_exam.domain.appointment.entity.Appointment;
import rs.ac.bg.fon.silab.mock_exam.domain.appointment.service.AppointmentService;
import rs.ac.bg.fon.silab.mock_exam.domain.candidate.entity.Candidate;
import rs.ac.bg.fon.silab.mock_exam.domain.candidate.service.CandidateService;
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
    @Mock
    private CandidateService candidateService;
    @Mock
    private AppointmentService appointmentService;
    @InjectMocks
    private ApplicationServiceImpl applicationService;

    private final Long APPLICATION_ID = 1L;
    private final Long CANDIDATE_ID = 1L;
    private final Date mockDate = new Date();
    private final Candidate mockCandidate = new Candidate();

    @BeforeEach
    void setUp(){
        mockCandidate.setId(CANDIDATE_ID);
    }

    @Test
    void testFindByIdWhenApplicationExists() {
        Application mockApplication = new Application();
        when(applicationRepository.findById(APPLICATION_ID)).thenReturn(Optional.of(mockApplication));

        Application result = applicationService.findById(APPLICATION_ID);
        assertEquals(mockApplication, result);
    }

    @Test
    void testFindByIdWhenApplicationDoesNotExist() {
        when(applicationRepository.findById(APPLICATION_ID)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> applicationService.findById(APPLICATION_ID));
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
    void testSaveWithDuplicateAppointment() {
        ApplicationRequestDTO requestDTO = new ApplicationRequestDTO(mockDate, true, CANDIDATE_ID, List.of(1L, 2L));
        Application application = new Application();
        Appointment appointment = new Appointment();
        appointment.setId(1L);
        application.addAppointment(appointment);
        application.setCandidate(mockCandidate);

        when(mapper.map(requestDTO)).thenReturn(application);
        when(applicationRepository.findByCandidateId(CANDIDATE_ID)).thenReturn(List.of(application));

        assertThrows(DuplicateAppointmentApplicationException.class, () -> applicationService.save(requestDTO));
    }

    @Test
    void testCreateApplicationWithAppointment() {
        Long appointmentId = 2L;
        Appointment mockAppointment = new Appointment();
        mockAppointment.setId(appointmentId);

        when(candidateService.existsById(CANDIDATE_ID)).thenReturn(true);
        when(candidateService.findById(CANDIDATE_ID)).thenReturn(mockCandidate);
        when(appointmentService.getById(appointmentId)).thenReturn(mockAppointment);

        ApplicationResponseDTO expectedResponse = new ApplicationResponseDTO(APPLICATION_ID, mockDate, true, null, null);
        when(mapper.map(any(Application.class))).thenReturn(expectedResponse);

        ApplicationResponseDTO actualResponse = applicationService.createApplicationWithAppointment(CANDIDATE_ID, appointmentId);

        assertEquals(expectedResponse, actualResponse);
        verify(appointmentService).getById(appointmentId);
        verify(candidateService).findById(CANDIDATE_ID);
        verify(applicationRepository).save(any(Application.class));
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


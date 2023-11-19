package rs.ac.bg.fon.silab.mock_exam.domain.appointment.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
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
import rs.ac.bg.fon.silab.mock_exam.domain.appointment.dto.AppointmentRequestDTO;
import rs.ac.bg.fon.silab.mock_exam.domain.appointment.dto.AppointmentResponseDTO;
import rs.ac.bg.fon.silab.mock_exam.domain.appointment.entity.Appointment;
import rs.ac.bg.fon.silab.mock_exam.domain.appointment.mapper.AppointmentMapper;
import rs.ac.bg.fon.silab.mock_exam.domain.appointment.repository.AppointmentRepository;
import rs.ac.bg.fon.silab.mock_exam.domain.candidate.repository.CandidateRepository;
import rs.ac.bg.fon.silab.mock_exam.infrastructure.exception.EntityNotFoundException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class AppointmentServiceImplTest {

    @Mock
    private AppointmentRepository appointmentRepository;
    @Mock
    private AppointmentMapper mapper;
    @Mock
    private CandidateRepository candidateRepository;
    @InjectMocks
    private AppointmentServiceImpl appointmentService;
    private final Long id = 1L;
    private final LocalDateTime appointmentDateTime = LocalDateTime.now();
    private Appointment mockAppointment;
    private AppointmentRequestDTO requestDTO;
    private AppointmentResponseDTO responseDTO;

    @BeforeEach
    void setUp(){
        this.mockAppointment = new Appointment();
        mockAppointment.setId(id);
        this.requestDTO = new AppointmentRequestDTO(id, appointmentDateTime);
        this.responseDTO = new AppointmentResponseDTO(id, null, appointmentDateTime);

    }

    @Test
    void testGetByIdWhenAppointmentExists() {
        when(appointmentRepository.findById(id)).thenReturn(Optional.of(mockAppointment));

        Appointment result = appointmentService.getById(id);
        assertEquals(mockAppointment, result);
    }

    @Test
    void testGetByIdWhenAppointmentDoesNotExist() {
        when(appointmentRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> appointmentService.getById(id));
    }

    @Test
    void testSave() {
        Appointment mappedAppointment = new Appointment();
        Appointment savedAppointment = new Appointment();

        when(mapper.map(requestDTO)).thenReturn(mappedAppointment);
        when(appointmentRepository.save(mappedAppointment)).thenReturn(savedAppointment);
        when(mapper.map(savedAppointment)).thenReturn(responseDTO);

        AppointmentResponseDTO resultDTO = appointmentService.save(requestDTO);

        verify(mapper).map(requestDTO);
        verify(appointmentRepository).save(mappedAppointment);
        verify(mapper).map(savedAppointment);

        assertEquals(responseDTO, resultDTO);
    }

    @Test
    void testFindByIdWhenAppointmentExists() {
        AppointmentResponseDTO expectedResponseDTO = new AppointmentResponseDTO(1L, null, LocalDateTime.now());

        when(appointmentRepository.findById(id)).thenReturn(Optional.of(mockAppointment));
        when(mapper.map(mockAppointment)).thenReturn(expectedResponseDTO);

        AppointmentResponseDTO actualResponseDTO = appointmentService.findById(id);

        verify(appointmentRepository).findById(id);
        verify(mapper).map(mockAppointment);

        assertEquals(expectedResponseDTO, actualResponseDTO);
    }

    @Test
    void testFindByIdWhenAppointmentDoesNotExist() {
        when(appointmentRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> appointmentService.findById(id));
    }

    @Test
    void testGet() {

        Pageable pageable = mock(Pageable.class);


        Page<Appointment> page = new PageImpl<>(List.of(mockAppointment));
        when(appointmentRepository.findAll(pageable)).thenReturn(page);
        when(mapper.map(mockAppointment)).thenReturn(responseDTO);

        Page<AppointmentResponseDTO> resultPage = appointmentService.get(pageable);

        verify(mapper, times(page.getContent().size())).map(any(Appointment.class));

        assertNotNull(resultPage);
        assertEquals(1, resultPage.getContent().size());
        assertEquals(responseDTO, resultPage.getContent().get(0));
    }

    @Test
    void testDeleteWhenAppointmentExists() {
        when(appointmentRepository.existsById(id)).thenReturn(true);

        appointmentService.delete(id);

        verify(appointmentRepository).deleteById(id);
    }

    @Test
    void testDeleteWhenAppointmentDoesNotExist() {
        when(appointmentRepository.existsById(id)).thenReturn(false);

        assertThrows(EntityNotFoundException.class, () -> {
            appointmentService.delete(id);
        });

        verify(appointmentRepository, never()).deleteById(id);
    }

    @Test
    void testUpdateWhenAppointmentExists() {
        Appointment existingAppointment = new Appointment();
        Appointment tempAppointment = new Appointment();
        AppointmentRequestDTO dto = new AppointmentRequestDTO(1L, LocalDateTime.now());
        AppointmentResponseDTO responseDTO = new AppointmentResponseDTO(1L, null, LocalDateTime.now());

        when(appointmentRepository.findById(id)).thenReturn(Optional.of(existingAppointment));
        when(mapper.map(dto)).thenReturn(tempAppointment);
        when(mapper.map(existingAppointment)).thenReturn(responseDTO);

        AppointmentResponseDTO result = appointmentService.update(id, dto);

        verify(mapper).update(existingAppointment, tempAppointment);
        verify(appointmentRepository).save(existingAppointment);
        assertEquals(responseDTO, result);
    }

    @Test
    void testUpdateWhenAppointmentDoesNotExist() {
        when(appointmentRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> {
            appointmentService.update(id, requestDTO);
        });

        verify(mapper, never()).update(any(Appointment.class), any(Appointment.class));
        verify(appointmentRepository, never()).save(any(Appointment.class));
    }

    @Test
    void testGetByCandidateIdWhenCandidateExists() {
        Pageable pageable = mock(Pageable.class);
        Page<Appointment> page = new PageImpl<>(List.of(mockAppointment));

        when(candidateRepository.existsById(id)).thenReturn(true);
        when(appointmentRepository.findByCandidateId(id, pageable)).thenReturn(page);
        when(mapper.map(mockAppointment)).thenReturn(responseDTO);

        Page<AppointmentResponseDTO> resultPage = appointmentService.getByCandidateId(id, pageable);

        verify(appointmentRepository).findByCandidateId(id, pageable);
        verify(mapper, times(page.getContent().size())).map(any(Appointment.class));
        assertNotNull(resultPage);
        assertEquals(1, resultPage.getContent().size());
        assertEquals(responseDTO, resultPage.getContent().get(0));
    }

    @Test
    void testGetByCandidateIdWhenCandidateDoesNotExist() {
        Pageable pageable = mock(Pageable.class);

        when(candidateRepository.existsById(id)).thenReturn(false);

        assertThrows(EntityNotFoundException.class, () -> appointmentService.getByCandidateId(id, pageable));
    }

    @Test
    void testGetAllSorted() {
        List<Appointment> sortedAppointments = List.of(mockAppointment);
        when(appointmentRepository.findAllByOrderByAppointmentDateAscExamNameAsc()).thenReturn(sortedAppointments);
        when(mapper.map(mockAppointment)).thenReturn(responseDTO);

        List<AppointmentResponseDTO> result = appointmentService.getAllSorted();

        verify(appointmentRepository).findAllByOrderByAppointmentDateAscExamNameAsc();
        verify(mapper, times(sortedAppointments.size())).map(any(Appointment.class));
        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertEquals(responseDTO, result.get(0));
    }

    @Test
    void testGetByCandidateIdNotSignedWhenCandidateExists() {
        Pageable pageable = mock(Pageable.class);
        Page<Appointment> page = new PageImpl<>(List.of(mockAppointment));

        when(candidateRepository.existsById(id)).thenReturn(true);
        when(appointmentRepository.findByCandidateIdNotSigned(id, pageable)).thenReturn(page);
        when(mapper.map(mockAppointment)).thenReturn(responseDTO);

        Page<AppointmentResponseDTO> resultPage = appointmentService.getByCandidateIdNotSigned(id, pageable);

        verify(appointmentRepository).findByCandidateIdNotSigned(id, pageable);
        verify(mapper, times(page.getContent().size())).map(any(Appointment.class));
        assertNotNull(resultPage);
        assertEquals(1, resultPage.getContent().size());
        assertEquals(responseDTO, resultPage.getContent().get(0));
    }

    @Test
    void testGetByCandidateIdNotSignedWhenCandidateDoesNotExist() {
        Long candidateId = 1L;
        Pageable pageable = mock(Pageable.class);

        when(candidateRepository.existsById(candidateId)).thenReturn(false);

        assertThrows(EntityNotFoundException.class, () -> appointmentService.getByCandidateIdNotSigned(candidateId, pageable));
    }
}




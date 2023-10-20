package rs.ac.bg.fon.silab.mock_exam.domain.appointment.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

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
import rs.ac.bg.fon.silab.mock_exam.domain.appointment.service.AppointmentServiceImpl;
import rs.ac.bg.fon.silab.mock_exam.domain.exam.dto.ExamResponseDTO;
import rs.ac.bg.fon.silab.mock_exam.infrastructure.exception.EntityNotFoundException;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class AppointmentServiceImplTest {

    @Mock
    private AppointmentRepository appointmentRepository;

    @Mock
    private AppointmentMapper mapper;

    @InjectMocks
    private AppointmentServiceImpl appointmentService;

    @Test
    void testGetByIdWhenAppointmentExists() {
        Long id = 1L;
        Appointment mockAppointment = new Appointment();
        when(appointmentRepository.findById(id)).thenReturn(Optional.of(mockAppointment));

        Appointment result = appointmentService.getById(id);
        assertEquals(mockAppointment, result);
    }

    @Test
    void testGetByIdWhenAppointmentDoesNotExist() {
        Long id = 1L;
        when(appointmentRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> appointmentService.getById(id));
    }

    @Test
    void testSave() {
        AppointmentRequestDTO dto = new AppointmentRequestDTO(1L, new Date());
        Appointment mappedAppointment = new Appointment();
        Appointment savedAppointment = new Appointment();
        AppointmentResponseDTO responseDTO = new AppointmentResponseDTO(1L, null, new Date());

        when(mapper.map(dto)).thenReturn(mappedAppointment);
        when(appointmentRepository.save(mappedAppointment)).thenReturn(savedAppointment);
        when(mapper.map(savedAppointment)).thenReturn(responseDTO);

        AppointmentResponseDTO resultDTO = appointmentService.save(dto);

        verify(mapper).map(dto);
        verify(appointmentRepository).save(mappedAppointment);
        verify(mapper).map(savedAppointment);

        assertEquals(responseDTO, resultDTO);
    }

    @Test
    void testFindByIdWhenAppointmentExists() {
        Long id = 1L;
        Appointment mockAppointment = new Appointment();
        AppointmentResponseDTO expectedResponseDTO = new AppointmentResponseDTO(1L, null, new Date());

        when(appointmentRepository.findById(id)).thenReturn(Optional.of(mockAppointment));
        when(mapper.map(mockAppointment)).thenReturn(expectedResponseDTO);

        AppointmentResponseDTO actualResponseDTO = appointmentService.findById(id);

        verify(appointmentRepository).findById(id);
        verify(mapper).map(mockAppointment);

        assertEquals(expectedResponseDTO, actualResponseDTO);
    }

    @Test
    void testFindByIdWhenAppointmentDoesNotExist() {
        Long id = 1L;
        when(appointmentRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> appointmentService.findById(id));
    }

    @Test
    void testGet() {

        Pageable pageable = mock(Pageable.class);

        Appointment mockAppointment = new Appointment();

        ExamResponseDTO mockExamDTO = new ExamResponseDTO(1L, "Ispit");
        AppointmentResponseDTO mockDTO = new AppointmentResponseDTO(1L, mockExamDTO, new Date());

        Page<Appointment> page = new PageImpl<>(List.of(new Appointment()));
        when(appointmentRepository.findAll(pageable)).thenReturn(page);
        when(mapper.map(mockAppointment)).thenReturn(mockDTO);

        Page<AppointmentResponseDTO> resultPage = appointmentService.get(pageable);

        verify(mapper, times(page.getContent().size())).map(any(Appointment.class));

        assertNotNull(resultPage);
        assertEquals(1, resultPage.getContent().size());
        assertEquals(mockDTO, resultPage.getContent().get(0));
    }

    @Test
    void testDeleteWhenAppointmentExists() {
        Long id = 1L;
        when(appointmentRepository.existsById(id)).thenReturn(true);

        appointmentService.delete(id);

        verify(appointmentRepository).deleteById(id);
    }

    @Test
    void testDeleteWhenAppointmentDoesNotExist() {
        Long id = 1L;

        when(appointmentRepository.existsById(id)).thenReturn(false);

        assertThrows(EntityNotFoundException.class, () -> {
            appointmentService.delete(id);
        });

        verify(appointmentRepository, never()).deleteById(id);
    }

    @Test
    void testUpdateWhenAppointmentExists() {

        Long id = 1L;
        Appointment existingAppointment = new Appointment();
        Appointment tempAppointment = new Appointment();
        AppointmentRequestDTO dto = new AppointmentRequestDTO(1L, new Date());
        AppointmentResponseDTO responseDTO = new AppointmentResponseDTO(1L, null, new Date());

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

        Long id = 1L;
        AppointmentRequestDTO dto = new AppointmentRequestDTO(1L, new Date());

        when(appointmentRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> {
            appointmentService.update(id, dto);
        });

        verify(mapper, never()).update(any(Appointment.class), any(Appointment.class));
        verify(appointmentRepository, never()).save(any(Appointment.class));
    }
}




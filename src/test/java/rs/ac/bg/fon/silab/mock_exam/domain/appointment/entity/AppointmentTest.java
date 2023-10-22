package rs.ac.bg.fon.silab.mock_exam.domain.appointment.entity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import rs.ac.bg.fon.silab.mock_exam.domain.application.entity.Application;
import rs.ac.bg.fon.silab.mock_exam.domain.exam.entity.Exam;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AppointmentTest {

    private Appointment appointment;
    private Exam mockExam;
    private Application mockApplication;

    @BeforeEach
    void setUp() {
        mockExam = mock(Exam.class);
        mockApplication = mock(Application.class);
        appointment = new Appointment(mockExam, new Date(), new ArrayList<>());
    }

    @Test
    void testAddApplication() {
        when(mockApplication.getAppointments()).thenReturn(new ArrayList<>());

        appointment.getApplications().add(mockApplication);

        assertTrue(appointment.getApplications().contains(mockApplication));
    }


    @Test
    void testRemoveApplication() {
        appointment.getApplications().add(mockApplication);
        assertTrue(appointment.getApplications().contains(mockApplication));

        appointment.getApplications().remove(mockApplication);

        assertFalse(appointment.getApplications().contains(mockApplication));
    }

    @Test
    void testEqualsWithSameObject() {
        assertTrue(appointment.equals(appointment));
    }

    @Test
    void testEqualsWithNull() {
        assertFalse(appointment.equals(null));
    }

    @Test
    void testEqualsWithDifferentClass() {
        Object obj = new Object();
        assertFalse(appointment.equals(obj));
    }

    @Test
    void testEqualsWithDifferentId() {
        Appointment anotherAppointment = new Appointment(mockExam, new Date(), new ArrayList<>());
        anotherAppointment.setId(2L);
        appointment.setId(1L);

        assertFalse(appointment.equals(anotherAppointment));
    }

    @Test
    void testEqualsWithSameId() {
        Appointment anotherAppointment = new Appointment(mockExam, new Date(), new ArrayList<>());
        anotherAppointment.setId(1L);
        appointment.setId(1L);

        assertTrue(appointment.equals(anotherAppointment));
    }

    @Test
    void testHashCodeConsistency() {
        int initialHashCode = appointment.hashCode();
        appointment.setAppointmentDate(new Date());
        assertEquals(initialHashCode, appointment.hashCode());
    }

    @Test
    void testHashCodeDifference() {
        Appointment anotherAppointment = new Appointment(mockExam, new Date(), new ArrayList<>());
        anotherAppointment.setId(2L);
        appointment.setId(1L);

        assertNotEquals(appointment.hashCode(), anotherAppointment.hashCode());
    }

    @Test
    void testToString() {
        String expectedString = "Appointment{" +
                "id=" + appointment.getId() +
                ", exam=" + mockExam +
                ", appointmentDate=" + appointment.getAppointmentDate() +
                ", applications=" + appointment.getApplications() +
                '}';
        assertEquals(expectedString, appointment.toString());
    }
}

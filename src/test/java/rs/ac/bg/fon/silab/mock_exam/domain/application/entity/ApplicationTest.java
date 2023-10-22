package rs.ac.bg.fon.silab.mock_exam.domain.application.entity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import rs.ac.bg.fon.silab.mock_exam.domain.appointment.entity.Appointment;
import rs.ac.bg.fon.silab.mock_exam.domain.candidate.entity.Candidate;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ApplicationTest {

    private Application application;
    private Candidate mockCandidate;
    private Appointment mockAppointment;
    private Appointment anotherMockAppointment;

    @BeforeEach
    void setUp() {
        mockCandidate = mock(Candidate.class);
        mockAppointment = mock(Appointment.class);
        anotherMockAppointment = mock(Appointment.class);
        application = new Application(new Date(), false, mockCandidate);
    }

    @Test
    void testAddAppointment() {
        when(mockAppointment.getApplications()).thenReturn(new ArrayList<>());

        application.addAppointment(mockAppointment);

        assertTrue(application.getAppointments().contains(mockAppointment));
    }

    @Test
    void testAddAppointmentWhenAppointmentsIsNull() {
        Application application = new Application();
        when(mockAppointment.getApplications()).thenReturn(new ArrayList<>());

        application.addAppointment(mockAppointment);

        assertNotNull(application.getAppointments());
        assertTrue(application.getAppointments().contains(mockAppointment));
    }

    @Test
    void testAddNullAppointment() {

        Application application = new Application();

        assertThrows(NullPointerException.class, () -> application.addAppointment(null));
    }

    @Test
    void testRemoveAppointment() {

        application.addAppointment(mockAppointment);
        assertTrue(application.getAppointments().contains(mockAppointment));

        when(mockAppointment.getApplications()).thenReturn(new ArrayList<>(List.of(application)));

        application.removeAppointment(mockAppointment);

        assertFalse(application.getAppointments().contains(mockAppointment));
    }


    @Test
    void testRemoveAppointmentFromEmptyList() {
        assertFalse(application.getAppointments().contains(mockAppointment));

        application.removeAppointment(mockAppointment);

        assertTrue(application.getAppointments().isEmpty());

    }

    @Test
    void testRemoveNonExistingAppointment() {

        application.addAppointment(anotherMockAppointment);
        assertFalse(application.getAppointments().contains(mockAppointment));

        when(mockAppointment.getApplications()).thenReturn(new ArrayList<>());

        application.removeAppointment(mockAppointment);

        assertTrue(application.getAppointments().contains(anotherMockAppointment));
        assertFalse(application.getAppointments().contains(mockAppointment));
    }

    @Test
    void testEqualsWithSameObject() {
        assertTrue(application.equals(application));
    }

    @Test
    void testEqualsWithNull() {
        assertFalse(application.equals(null));
    }

    @Test
    void testEqualsWithDifferentClass() {
        Object obj = new Object();
        assertFalse(application.equals(obj));
    }

    @Test
    void testEqualsWithDifferentId() {
        Application anotherApplication = new Application(new Date(), true, mockCandidate);
        anotherApplication.setId(2L);
        application.setId(1L);

        assertFalse(application.equals(anotherApplication));
    }

    @Test
    void testEqualsWithSameId() {
        Application anotherApplication = new Application(new Date(), true, mockCandidate);
        anotherApplication.setId(1L);
        application.setId(1L);

        assertTrue(application.equals(anotherApplication));
    }

    @Test
    void testHashCodeConsistency() {
        int initialHashCode = application.hashCode();
        application.setPrivileged(true);
        assertEquals(initialHashCode, application.hashCode());
    }

    @Test
    void testHashCodeDifference() {
        Application anotherApplication = new Application(new Date(), true, mockCandidate);
        anotherApplication.setId(2L);
        application.setId(1L);

        assertNotEquals(application.hashCode(), anotherApplication.hashCode());
    }

    @Test
    void testToString() {
        String expectedString = "Application{" +
                "id=null, applicationDate=" + application.getApplicationDate() +
                ", privileged=" + application.isPrivileged() +
                ", candidate=" + mockCandidate +
                '}';
        assertEquals(expectedString, application.toString());
    }
}

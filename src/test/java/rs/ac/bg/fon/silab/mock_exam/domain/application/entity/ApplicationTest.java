package rs.ac.bg.fon.silab.mock_exam.domain.application.entity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import rs.ac.bg.fon.silab.mock_exam.domain.appointment.entity.Appointment;
import rs.ac.bg.fon.silab.mock_exam.domain.candidate.entity.Candidate;

import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class ApplicationTest {

    private Application application;
    private Candidate mockCandidate;
    private Appointment mockAppointment;

    @BeforeEach
    void setUp() {
        mockCandidate = new Candidate();
        application = new Application(new Date(), false, mockCandidate);

        mockAppointment = new Appointment();
    }

    @Test
    void testAddAppointment() {
        application.addAppointment(mockAppointment);
        List<Appointment> appointments = application.getAppointments();
        assertTrue(appointments.contains(mockAppointment));
        assertTrue(mockAppointment.getApplications().contains(application));
    }

    @Test
    void testRemoveAppointment() {
        application.addAppointment(mockAppointment);
        application.removeAppointment(mockAppointment);

        assertFalse(application.getAppointments().contains(mockAppointment));
        assertFalse(mockAppointment.getApplications().contains(application));
    }

    @Test
    void testSetAndGetId() {
        Long id = 123L;
        application.setId(id);
        assertEquals(id, application.getId());
    }

    @Test
    void testSetAndGetApplicationDate() {
        Date date = new Date();
        application.setApplicationDate(date);
        assertEquals(date, application.getApplicationDate());
    }

    @Test
    void testSetAndGetPrivileged() {
        application.setPrivileged(true);
        assertTrue(application.isPrivileged());
    }

    @Test
    void testSetAndGetCandidate() {
        Candidate candidate = new Candidate(); // Assuming Candidate has a default constructor
        application.setCandidate(candidate);
        assertEquals(candidate, application.getCandidate());
    }

    @Test
    void testEqualsSameObject() {
        assertTrue(application.equals(application));
    }

    @Test
    void testEqualsDifferentObject() {
        Application anotherApplication = new Application();
        anotherApplication.setId(application.getId());
        assertTrue(application.equals(anotherApplication));
    }

    @Test
    void testEqualsNullObject() {
        assertFalse(application.equals(null));
    }

    @Test
    void testEqualsDifferentObjectType() {
        assertFalse(application.equals(new Object()));
    }

    @Test
    void testHashCode() {
        Application anotherApplication = new Application();
        anotherApplication.setId(application.getId());
        assertEquals(anotherApplication.hashCode(), application.hashCode());
    }

}


package rs.ac.bg.fon.silab.mock_exam.domain.appointment.entity;

import org.junit.jupiter.api.Test;
import rs.ac.bg.fon.silab.mock_exam.domain.application.entity.Application;
import rs.ac.bg.fon.silab.mock_exam.domain.exam.entity.Exam;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class AppointmentTest {

    @Test
    void testDefaultConstructor() {
        Appointment appointment = new Appointment();
        assertNull(appointment.getId());
        assertNull(appointment.getExam());
        assertNull(appointment.getAppointmentDate());
        assertNotNull(appointment.getApplications());
        assertTrue(appointment.getApplications().isEmpty());
    }

    @Test
    void testConstructorWithDate() {
        Date date = new Date();
        Appointment appointment = new Appointment(date);
        assertEquals(date, appointment.getAppointmentDate());
    }

    @Test
    void testConstructorWithAllArgs() {
        Date date = new Date();
        Exam exam = new Exam();
        Application app1 = new Application();
        Application app2 = new Application();
        List<Application> applications = Arrays.asList(app1, app2);

        Appointment appointment = new Appointment(exam, date, applications);
        assertEquals(exam, appointment.getExam());
        assertEquals(date, appointment.getAppointmentDate());
        assertEquals(applications, appointment.getApplications());
    }

    @Test
    void testGettersAndSetters() {
        Appointment appointment = new Appointment();
        Date date = new Date();
        Exam exam = new Exam();
        Application app = new Application();
        List<Application> applications = Arrays.asList(app);

        appointment.setId(1L);
        appointment.setExam(exam);
        appointment.setAppointmentDate(date);
        appointment.setApplications(applications);

        assertEquals(Long.valueOf(1L), appointment.getId());
        assertEquals(exam, appointment.getExam());
        assertEquals(date, appointment.getAppointmentDate());
        assertEquals(applications, appointment.getApplications());
    }

    @Test
    void testEqualsAndHashCode() {
        Appointment appointment1 = new Appointment();
        Appointment appointment2 = new Appointment();

        assertEquals(appointment1, appointment2);
        assertEquals(appointment1.hashCode(), appointment2.hashCode());

        appointment1.setId(1L);
        assertNotEquals(appointment1, appointment2);
        assertNotEquals(appointment1.hashCode(), appointment2.hashCode());

        appointment2.setId(1L);
        assertEquals(appointment1, appointment2);
        assertEquals(appointment1.hashCode(), appointment2.hashCode());
    }

    @Test
    void testToString() {
        Appointment appointment = new Appointment();
        String expectedString = "Appointment{" +
                "id=null, exam=null, appointmentDate=null, applications=[]}";
        assertEquals(expectedString, appointment.toString());
    }
}


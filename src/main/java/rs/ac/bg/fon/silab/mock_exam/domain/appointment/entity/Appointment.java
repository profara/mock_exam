package rs.ac.bg.fon.silab.mock_exam.domain.appointment.entity;

import jakarta.persistence.*;
import rs.ac.bg.fon.silab.mock_exam.domain.application.entity.Application;
import rs.ac.bg.fon.silab.mock_exam.domain.exam.entity.Exam;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static rs.ac.bg.fon.silab.mock_exam.infrastructure.config.Constants.*;

/**
 * Represents an appointment for a mock entrance exam.
 * This entity is linked to {@link Exam} and {@link Application} entities.
 * It tracks the appointment date and manages the applications associated with it.
 *
 *  <p> This class includes methods to get and set its fields, along with methods to manage applications linked to the appointment.
 *  It also overrides the {@code equals}, {@code hashCode}, {@code toString} methods from the {@code Object} class.</p>
 */
@Entity
public class Appointment {

    /**
     * Unique identifier for the appointment. This is automatically generated.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * The exam associated with this appointment. The association is mandatory.
     */
    @ManyToOne(optional = false)
    @JoinColumn(name = FOREIGN_KEY_EXAM)
    private Exam exam;

    /**
     * Date and time when the appointment is scheduled. It is a required field.
     */
    @Column(name = APPOINTMENT_DATE_COLUMN_NAME, nullable = false)
    private LocalDateTime appointmentDate;

    /**
     * List of applications associated with this appointment. It is managed through a many-to-many relationship.
     * The list is implemented using an ArrayList.
     */
    @ManyToMany(mappedBy = "appointments")
    private List<Application> applications = new ArrayList<>();

    /**
     * Default constructor required by JPA. It is not intended for direct use.
     */
    public Appointment() {
    }

    /**
     * Creates a new appointment with the specified appointment date.
     *
     * @param appointmentDate the date and time when the appointment is scheduled, not null
     */
    public Appointment(LocalDateTime appointmentDate) {
        this.appointmentDate = appointmentDate;
    }

    /**
     * Creates a new appointment with specified exam, appointment date, and associated applications.
     *
     * @param exam the exam associated with this appointment, not null
     * @param appointmentDate the date and time when the appointment is scheduled, not null
     * @param applications a List of {@link Application} objects associated with this appointment
     */
    public Appointment(Exam exam, LocalDateTime appointmentDate, List<Application> applications) {
        this.exam = exam;
        this.appointmentDate = appointmentDate;
        this.applications = applications;
    }

    /**
     * Gets the unique identifier of the appointment.
     *
     * @return the identifier of the appointment
     */
    public Long getId() {
        return id;
    }

    /**
     * Sets the unique identifier of the appointment.
     *
     * @param id the identifier to be set for the appointment
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Gets the exam associated with the appointment.
     *
     * @return the exam linked to the appointment
     */
    public Exam getExam() {
        return exam;
    }

    /**
     * Sets the exam associated with the appointment.
     *
     * @param exam the exam to be linked to the appointment, not null
     */
    public void setExam(Exam exam) {
        this.exam = exam;
    }

    /**
     * Gets the date and time of the appointment.
     *
     * @return the date and time when the appointment is scheduled
     */
    public LocalDateTime getAppointmentDate() {
        return appointmentDate;
    }

    /**
     * Sets the date and time of the appointment.
     *
     * @param appointmentDate the date and time to be set for when the appointment is scheduled, not null
     */
    public void setAppointmentDate(LocalDateTime appointmentDate) {
        this.appointmentDate = appointmentDate;
    }

    /**
     * Gets the list of applications associated with the appointment.
     *
     * @return a List of {@link Application} objects associated with the appointment. The returned list may be empty
     *         if there are no applications associated.
     */
    public List<Application> getApplications() {
        return applications;
    }

    /**
     * Sets the list of applications for the appointment.
     *
     * @param applications a List of {@link Application} objects to be associated with the appointment
     */
    public void setApplications(List<Application> applications) {
        this.applications = applications;
    }

    /**
     * Indicates whether some other {@link Appointment} is "equal to" this one based on the id.
     *
     * @param o the reference object with which to compare
     * @return
     * <ul>
     *     <li>{@code true} - if this Appointment is the same as the object argument</li>
     *     <li>{@code false} - otherwise</li>
     * </ul>
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Appointment that = (Appointment) o;
        return Objects.equals(id, that.id);
    }

    /**
     * Calculates hash code based on the id.
     *
     * @return hash code value for this object based on the id
     */
    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    /**
     * Returns a string representation of the object.
     *
     * @return a string representation of the object
     */
    @Override
    public String toString() {
        return "Appointment{" +
                "id=" + id +
                ", exam=" + exam +
                ", appointmentDate=" + appointmentDate +
                ", applications=" + applications +
                '}';
    }
}

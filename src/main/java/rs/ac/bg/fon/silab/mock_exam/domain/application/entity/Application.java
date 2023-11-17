package rs.ac.bg.fon.silab.mock_exam.domain.application.entity;

import jakarta.persistence.*;
import rs.ac.bg.fon.silab.mock_exam.domain.candidate.entity.Candidate;
import rs.ac.bg.fon.silab.mock_exam.domain.appointment.entity.Appointment;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import static rs.ac.bg.fon.silab.mock_exam.infrastructure.config.Constants.*;

/**
 * Represents an application made by a candidate for a mock entrance exam.
 * This entity is linked to {@link Candidate} and {@link Appointment} entities.
 * It tracks the application date, whether the candidate is privileged, and manages appointments associated with it.
 *
 * <p> The class includes basic getter and setter methods for its fields, along with methods to add and remove appointments.
 * It also overrides the {@code equals}, {@code hashCode}, {@code toString} methods from the {@code Object} class.</p>
 */
@Entity
public class Application {

    /**
     * Unique identifier for the application. This is automatically generated.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Date on which the application was made. It is a required field.
     */
    @Column(name = APPLICATION_DATE_COLUMN_NAME, nullable = false)
    private Date applicationDate;

    /**
     * Flag indicating whether the candidate has privileged status.
     */
    @Column(columnDefinition = "TINYINT(1)", nullable = false)
    private boolean privileged;

    /**
     * The candidate associated with this application. The association is mandatory.
     */
    @ManyToOne(optional = false)
    @JoinColumn(name = FOREIGN_KEY_CANDIDATE)
    private Candidate candidate;

    /**
     * List of appointments associated with this application. It is managed through many-to-many relationship.
     * The list is implemented using ArrayList implementation.
     */
    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(name = APPLICATION_APPOINTMENT_TABLE_NAME,
            joinColumns = @JoinColumn(name = FOREIGN_KEY_APPLICATION),
            inverseJoinColumns = @JoinColumn(name = FOREIGN_KEY_APPOINTMENT)
    )
    private List<Appointment> appointments= new ArrayList<>();

    /**
     * Default constructor required by JPA. It is not intended for direct use.
     */
    public Application() {
    }

    /**
     * Creates a new application with specified date, privileged status, and associated candidate.
     *
     * @param applicationDate the date on which the application is made, not null
     * @param privileged a boolean flag indicating if the candidate who made an application is privileged.
     * @param candidate the candidate associated with this application, not null
     */
    public Application(Date applicationDate, boolean privileged, Candidate candidate) {
        this.applicationDate = applicationDate;
        this.privileged = privileged;
        this.candidate = candidate;
    }

    /**
     * Adds an appointment to this application. This method ensures consistency by also adding
     * this application to the appointment's list of applications.
     *
     * @param appointment the appointment to add, not null
     */
    public void addAppointment(Appointment appointment){
        if(appointments == null){
            appointments = new ArrayList<>();
        }
        appointments.add(appointment);
        appointment.getApplications().add(this);
    }

    /**
     * Removes an appointment from this application. This method ensures consistency by also removing
     * this application from the appointment's list of applications.
     *
     * @param appointment the appointment to remove, not null
     */
    public void removeAppointment(Appointment appointment){
        appointments.remove(appointment);
        appointment.getApplications().remove(this);
    }

    /**
     * Gets the list of appointments associated with this application.
     *
     * @return a List of {@link Appointment} objects associated with this application.The returned list may be empty
     *         if there are no appointments associated.
     */
    public List<Appointment> getAppointments() {
        return appointments;
    }

    /**
     * Sets the list of appointments for this application.
     *
     * @param appointments a List of {@link Appointment} objects to be associated with this application.
     */
    public void setAppointments(List<Appointment> appointments) {
        this.appointments = appointments;
    }

    /**
     * Gets the unique identifier of the application.
     *
     * @return the identifier of the application
     */
    public Long getId() {
        return id;
    }

    /**
     * Sets the unique identifier of the application.
     *
     * @param id the identifier to be set for the application
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Gets the date of the application.
     *
     * @return the date when the application was made
     */
    public Date getApplicationDate() {
        return applicationDate;
    }

    /**
     * Sets the date of the application.
     *
     * @param applicationDate the date to be set for when the application was made
     */
    public void setApplicationDate(Date applicationDate) {
        this.applicationDate = applicationDate;
    }

    /**
     * Checks if the candidate associated with the application has privileged status.
     *
     * @return
     * <ul>
     *     <li>{@code true} - if the candidate associated with the application has attended the preparation</li>
     *     <li>{@code false} - otherwise</li>
     * </ul>
     */
    public boolean isPrivileged() {
        return privileged;
    }

    /**
     * Sets the privileged status of the application.
     *
     * @param privileged a boolean indicating if the application should be set as privileged
     */
    public void setPrivileged(boolean privileged) {
        this.privileged = privileged;
    }

    /**
     * Gets the candidate associated with the application.
     *
     * @return the candidate linked to the application
     */
    public Candidate getCandidate() {
        return candidate;
    }

    /**
     * Sets the candidate associated with the application.
     *
     * @param candidate the candidate to be linked to the application
     */
    public void setCandidate(Candidate candidate) {
        this.candidate = candidate;
    }

    /**
     * Indicates whether some other {@link Application} is "equal to" this one based on the id.
     *
     * @param o the reference object with which to compare
     * @return
     * <ul>
     *     <li>{@code true} - if this Application is the same as the object argument</li>
     *     <li>{@code false} - otherwise</li>
     * </ul>
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Application that = (Application) o;
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
        return "Application{" +
                "id=" + id +
                ", applicationDate=" + applicationDate +
                ", privileged=" + privileged +
                ", candidate=" + candidate +
                '}';
    }
}

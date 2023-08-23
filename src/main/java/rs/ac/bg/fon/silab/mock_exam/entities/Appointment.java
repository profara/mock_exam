package rs.ac.bg.fon.silab.mock_exam.entities;

import jakarta.persistence.*;
import rs.ac.bg.fon.silab.mock_exam.entities.composite.keys.AppointmentId;

import java.util.Date;
import java.util.List;
import java.util.Objects;

import static rs.ac.bg.fon.silab.mock_exam.config.Constants.*;
import static rs.ac.bg.fon.silab.mock_exam.config.Constants.FOREIGN_KEY_APPLICATION;

@Entity
@IdClass(AppointmentId.class)
public class Appointment {
    @EmbeddedId
    private AppointmentId appointmentId;
    @Column(name = APPOINTMENT_DATE_COLUMN_NAME, nullable = false)
    private Date appointmentDate;
    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(name = APPLICATION_APPOINTMENT_TABLE_NAME,
            joinColumns = {
            @JoinColumn(name = FOREIGN_KEY_APPOINTMENT),
            @JoinColumn(name = FOREIGN_KEY_EXAM)
            },
            inverseJoinColumns = @JoinColumn(name = FOREIGN_KEY_APPLICATION)
    )
    private List<Application> applications;
    public Appointment() {
    }

    public Appointment(Date appointmentDate) {
        this.appointmentDate = appointmentDate;
    }

    public Appointment(AppointmentId appointmentId, Date appointmentDate) {
        this.appointmentId = appointmentId;
        this.appointmentDate = appointmentDate;
    }

    public AppointmentId getAppointmentId() {
        return appointmentId;
    }

    public void setAppointmentId(AppointmentId appointmentId) {
        this.appointmentId = appointmentId;
    }

    public Date getAppointmentDate() {
        return appointmentDate;
    }

    public void setAppointmentDate(Date appointmentDate) {
        this.appointmentDate = appointmentDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Appointment that = (Appointment) o;
        return Objects.equals(appointmentId, that.appointmentId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(appointmentId);
    }

    @Override
    public String toString() {
        return "Appointment{" +
                "appointmentId=" + appointmentId +
                ", appointmentDate=" + appointmentDate +
                '}';
    }
}

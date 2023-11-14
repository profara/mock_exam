package rs.ac.bg.fon.silab.mock_exam.domain.appointment.entity;

import jakarta.persistence.*;
import rs.ac.bg.fon.silab.mock_exam.domain.application.entity.Application;
import rs.ac.bg.fon.silab.mock_exam.domain.exam.entity.Exam;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import static rs.ac.bg.fon.silab.mock_exam.infrastructure.config.Constants.*;

@Entity
public class Appointment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = FOREIGN_KEY_EXAM)
    private Exam exam;

    @Column(name = APPOINTMENT_DATE_COLUMN_NAME, nullable = false)
    private LocalDateTime appointmentDate;

    @ManyToMany(mappedBy = "appointments")
    private List<Application> applications = new ArrayList<>();
    public Appointment() {
    }

    public Appointment(LocalDateTime appointmentDate) {
        this.appointmentDate = appointmentDate;
    }

    public Appointment(Exam exam, LocalDateTime appointmentDate, List<Application> applications) {
        this.exam = exam;
        this.appointmentDate = appointmentDate;
        this.applications = applications;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Exam getExam() {
        return exam;
    }

    public void setExam(Exam exam) {
        this.exam = exam;
    }

    public LocalDateTime getAppointmentDate() {
        return appointmentDate;
    }

    public void setAppointmentDate(LocalDateTime appointmentDate) {
        this.appointmentDate = appointmentDate;
    }

    public List<Application> getApplications() {
        return applications;
    }

    public void setApplications(List<Application> applications) {
        this.applications = applications;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Appointment that = (Appointment) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

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

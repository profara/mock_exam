package rs.ac.bg.fon.silab.mock_exam.entities.composite.keys;

import jakarta.persistence.*;
import rs.ac.bg.fon.silab.mock_exam.entities.Exam;

import java.io.Serializable;
import java.util.Objects;

import static rs.ac.bg.fon.silab.mock_exam.config.Constants.FOREIGN_KEY_EXAM;

@Embeddable
public class AppointmentId implements Serializable {

    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = FOREIGN_KEY_EXAM)
    private Exam exam;

    public AppointmentId() {
    }

    public AppointmentId(Exam exam) {
        this.exam = exam;
    }

    public AppointmentId(Long id, Exam exam) {
        this.id = id;
        this.exam = exam;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AppointmentId that = (AppointmentId) o;
        return Objects.equals(id, that.id) && Objects.equals(exam, that.exam);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, exam);
    }

    @Override
    public String toString() {
        return "AppointmentId{" +
                "id=" + id +
                ", exam=" + exam +
                '}';
    }
}

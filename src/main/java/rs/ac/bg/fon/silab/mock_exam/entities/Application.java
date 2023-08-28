package rs.ac.bg.fon.silab.mock_exam.entities;

import jakarta.persistence.*;
import rs.ac.bg.fon.silab.mock_exam.domain.candidate.entity.Candidate;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import static rs.ac.bg.fon.silab.mock_exam.infrastructure.config.Constants.*;

@Entity
public class Application {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = APPLICATION_DATE_COLUMN_NAME, nullable = false)
    private Date applicationDate;
    @Column(columnDefinition = "TINYINT(1)", nullable = false)
    private boolean privileged;
    @OneToOne(optional = false)
    @JoinColumn(name = FOREIGN_KEY_CANDIDATE)
    private Candidate candidate;
    @ManyToMany(mappedBy = "applications")
    private List<Appointment> appointments = new ArrayList<>();

    public Application() {
    }

    public Application(Date applicationDate, boolean privileged, Candidate candidate) {
        this.applicationDate = applicationDate;
        this.privileged = privileged;
        this.candidate = candidate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getApplicationDate() {
        return applicationDate;
    }

    public void setApplicationDate(Date applicationDate) {
        this.applicationDate = applicationDate;
    }

    public boolean isPrivileged() {
        return privileged;
    }

    public void setPrivileged(boolean privileged) {
        this.privileged = privileged;
    }

    public Candidate getCandidate() {
        return candidate;
    }

    public void setCandidate(Candidate candidate) {
        this.candidate = candidate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Application that = (Application) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

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

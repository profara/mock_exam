package rs.ac.bg.fon.silab.mock_exam.entities;

import jakarta.persistence.*;
import rs.ac.bg.fon.silab.mock_exam.domain.user.profile.entity.UserProfile;

import java.util.Objects;

import static rs.ac.bg.fon.silab.mock_exam.infrastructure.config.Constants.*;

@Entity
public class Candidate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(columnDefinition = "VARCHAR(45)", nullable = false)
    private String name;
    @Column(columnDefinition = "VARCHAR(45)", nullable = false)
    private String surname;
    @Column(name = ATTENDED_PREPARATION_COLUMN_NAME,columnDefinition = "TINYINT(1)", nullable = false)
    private boolean attendedPreparation;
    @OneToOne(optional = false)
    @JoinColumn(name = FOREIGN_KEY_USER_PROFILE)
    private UserProfile userProfile;
    @ManyToOne(optional = false)
    @JoinColumn(name = FOREIGN_KEY_SCHOOL)
    private School school;

    public Candidate() {
    }

    public Candidate(String name, String surname, boolean attendedPreparation, UserProfile userProfile, School school) {
        this.name = name;
        this.surname = surname;
        this.attendedPreparation = attendedPreparation;
        this.userProfile = userProfile;
        this.school = school;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public boolean isAttendedPreparation() {
        return attendedPreparation;
    }

    public void setAttendedPreparation(boolean attendedPreparation) {
        this.attendedPreparation = attendedPreparation;
    }

    public UserProfile getUserProfile() {
        return userProfile;
    }

    public void setUserProfile(UserProfile userProfile) {
        this.userProfile = userProfile;
    }

    public School getSchool() {
        return school;
    }

    public void setSchool(School school) {
        this.school = school;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Candidate candidate = (Candidate) o;
        return Objects.equals(id, candidate.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Candidate{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", attendedPreparation=" + attendedPreparation +
                ", userProfile=" + userProfile +
                ", school=" + school +
                '}';
    }
}

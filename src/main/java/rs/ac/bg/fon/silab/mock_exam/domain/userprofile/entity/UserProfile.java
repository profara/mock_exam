package rs.ac.bg.fon.silab.mock_exam.domain.userprofile.entity;

import jakarta.persistence.*;
import rs.ac.bg.fon.silab.mock_exam.domain.userrole.entity.UserRole;

import java.util.Objects;

import static rs.ac.bg.fon.silab.mock_exam.infrastructure.config.Constants.USER_PROFILE_TABLE_NAME;
import static rs.ac.bg.fon.silab.mock_exam.infrastructure.config.Constants.FOREIGN_KEY_USER_ROLE;

@Entity
@Table(name = USER_PROFILE_TABLE_NAME)
public class UserProfile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(columnDefinition = "VARCHAR(100)", nullable = false, unique = true)
    private String email;
    @Column(columnDefinition = "VARCHAR(100)", nullable = false)
    private String password;
    @ManyToOne(optional = false)
    @JoinColumn(name = FOREIGN_KEY_USER_ROLE)
    private UserRole userRole;

    public UserProfile() {
    }

    public UserProfile(String email, String password, UserRole userRole) {
        this.email = email;
        this.password = password;
        this.userRole = userRole;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public UserRole getUserRole() {
        return userRole;
    }

    public void setUserRole(UserRole userRole) {
        this.userRole = userRole;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserProfile that = (UserProfile) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "UserProfile{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", userRole=" + userRole +
                '}';
    }
}

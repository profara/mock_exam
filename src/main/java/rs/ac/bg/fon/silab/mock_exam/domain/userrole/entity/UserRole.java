package rs.ac.bg.fon.silab.mock_exam.domain.userrole.entity;

import jakarta.persistence.*;

import java.util.Objects;

import static rs.ac.bg.fon.silab.mock_exam.infrastructure.config.Constants.USER_ROLE_TABLE_NAME;

/**
 * Represents a user role in the system.
 * This entity defines different roles that a user can have.
 */
@Entity
@Table(name = USER_ROLE_TABLE_NAME)
public class UserRole {

    /**
     * Unique identifier for the user role. This is automatically generated.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * The name of the user role. It is unique and not nullable.
     */
    @Column(columnDefinition = "VARCHAR(45)", nullable = false,unique = true)
    private String name;

    /**
     * Default constructor for JPA.
     */
    public UserRole(){

    }

    /**
     * Creates a new UserRole with the specified name.
     *
     * @param name the name of the user role, not null
     */
    public UserRole(String name) {
        this.name = name;
    }

    /**
     * Gets the unique identifier of the user role.
     *
     * @return the identifier of the user role
     */
    public Long getId() {
        return id;
    }

    /**
     * Sets the unique identifier of the user role.
     *
     * @param id the identifier to be set for the user role
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Gets the name of the user role.
     *
     * @return the name of the user role
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the user role.
     *
     * @param name the name to be set for the user role, not null
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Indicates whether some other {@link UserRole} is "equal to" this one based on the id.
     *
     * @param o the reference object with which to compare
     * @return
     * <ul>
     *     <li>{@code true} if this UserRole is the same as the object argument</li>
     *     <li>{@code false} - otherwise</li>
     * </ul>
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserRole userRole = (UserRole) o;
        return Objects.equals(id, userRole.id);
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
     * Returns a string representation of the user role.
     *
     * @return a string representation of the user role
     */
    @Override
    public String toString() {
        return "UserRole{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}

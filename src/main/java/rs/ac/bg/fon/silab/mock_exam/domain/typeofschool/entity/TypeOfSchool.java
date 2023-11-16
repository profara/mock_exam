package rs.ac.bg.fon.silab.mock_exam.domain.typeofschool.entity;

import jakarta.persistence.*;

import java.util.Objects;

import static rs.ac.bg.fon.silab.mock_exam.infrastructure.config.Constants.TYPE_OF_SCHOOL_TABLE_NAME;

/**
 * Represents the type of school which candidate attended before applying for mock entrance exam.
 * This entity is characterized by a unique name.
 *
 * <p> This class includes methods to get and set its fields.
 * It also overrides the {@code equals}, {@code hashCode}, {@code toString} methods from the {@code Object} class. </p>
 */
@Entity
@Table(name = TYPE_OF_SCHOOL_TABLE_NAME)
public class TypeOfSchool {

    /**
     * Unique identifier for the type of school. This is automatically generated.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * The name of the type of school. This field is unique and required.
     */
    @Column(columnDefinition = "VARCHAR(45)",nullable = false, unique = true)
    private String name;

    /**
     * Default constructor required by JPA. It is not intended for direct use.
     */
    public TypeOfSchool() {
    }

    /**
     * Creates a new type of school with the specified name.
     *
     * @param name the name of the type of school, not null and unique
     */
    public TypeOfSchool(String name) {
        this.name = name;
    }

    /**
     * Gets the unique identifier of the type of school.
     *
     * @return the identifier of the type of school
     */
    public Long getId() {
        return id;
    }

    /**
     * Sets the unique identifier of the type of school.
     *
     * @param id the identifier to be set for the type of school
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Gets the name of the type of school.
     *
     * @return the name of the type of school
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the type of school.
     *
     * @param name the name to be set for the type of school, not null and unique
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Indicates whether some other {@link TypeOfSchool} is "equal to" this one based on the id.
     *
     * @param o the reference object with which to compare
     * @return
     * <ul>
     *     <li>{@code true} - if this TypeOfSchool is the same as the object argument</li>
     *     <li>{@code false} - otherwise</li>
     * </ul>
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TypeOfSchool that = (TypeOfSchool) o;
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
        return "TypeOfSchool{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}

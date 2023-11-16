package rs.ac.bg.fon.silab.mock_exam.domain.exam.entity;

import jakarta.persistence.*;

import java.util.Objects;


/**
 * Represents an exam for a mock entrance exam system.
 * This entity stores information about the exam, including its name.
 *
 * <p> This class includes methods to get and set its fields.
 * It also overrides the {@code equals}, {@code hashCode}, {@code toString} methods from the {@code Object} class.</p>
 */
@Entity
public class Exam {

    /**
     * Unique identifier for the exam. This is automatically generated.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Name of the exam. It is a required field.
     */
    @Column(columnDefinition = "VARCHAR(100)",nullable = false)
    private String name;

    /**
     * Default constructor required by JPA. It is not intended for direct use.
     */
    public Exam() {
    }

    /**
     * Creates a new Exam with a specified name.
     *
     * @param name the name of the exam, not null
     */
    public Exam(String name) {
        this.name = name;
    }

    /**
     * Gets the unique identifier of the exam.
     *
     * @return the identifier of the exam
     */
    public Long getId() {
        return id;
    }

    /**
     * Sets the unique identifier of the exam.
     *
     * @param id the identifier to be set for the exam
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Gets the name of the exam.
     *
     * @return the name of the exam
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the exam.
     *
     * @param name the name to be set for the exam, not null
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Indicates whether some other {@link Exam} is "equal to" this one based on the id.
     *
     * @param o the reference object with which to compare
     * @return
     * <ul>
     *     <li>{@code true} - if this Exam is the same as the object argument</li>
     *     <li>{@code false} - otherwise</li>
     * </ul>
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Exam exam = (Exam) o;
        return Objects.equals(id, exam.id);
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
     * Returns a string representation of the exam.
     *
     * @return a string representation of the exam
     */
    @Override
    public String toString() {
        return "Exam{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}

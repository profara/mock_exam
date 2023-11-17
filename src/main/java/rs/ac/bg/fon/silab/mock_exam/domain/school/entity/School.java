package rs.ac.bg.fon.silab.mock_exam.domain.school.entity;

import jakarta.persistence.*;
import rs.ac.bg.fon.silab.mock_exam.domain.city.entity.City;
import rs.ac.bg.fon.silab.mock_exam.domain.typeofschool.entity.TypeOfSchool;

import java.util.Objects;

import static rs.ac.bg.fon.silab.mock_exam.infrastructure.config.Constants.FOREIGN_KEY_CITY;
import static rs.ac.bg.fon.silab.mock_exam.infrastructure.config.Constants.FOREIGN_KEY_TYPE_OF_SCHOOL;

/**
 * Represents a school that candidate went to before applying for mock entrance exam.
 * This entity is linked to {@link TypeOfSchool} and {@link City} entities.
 * It includes details about the school such as its code, name, type, and location.
 */
@Entity
public class School {

    /**
     * Unique code for the school. This serves as the primary identifier.
     */
    @Id
    private Long code;

    /**
     * Name of the school. It is a mandatory field.
     */
    @Column(columnDefinition = "VARCHAR(100)",nullable = false)
    private String name;

    /**
     * Type of the school, indicating its educational focus.
     * This association is mandatory.
     */
    @ManyToOne(optional = false)
    @JoinColumn(name = FOREIGN_KEY_TYPE_OF_SCHOOL)
    private TypeOfSchool typeOfSchool;

    /**
     * The city where the school is located.
     * This association is mandatory.
     */
    @ManyToOne(optional = false)
    @JoinColumn(name = FOREIGN_KEY_CITY)
    private City city;

    /**
     * Default constructor.
     * Required for JPA entity initialization.
     */
    public School() {
    }

    /**
     * Creates a new school with the specified details.
     *
     * @param code         the unique code of the school
     * @param name         the name of the school
     * @param typeOfSchool the type of the school
     * @param city         the city where the school is located
     */
    public School(Long code, String name, TypeOfSchool typeOfSchool, City city) {
        this.code = code;
        this.name = name;
        this.typeOfSchool = typeOfSchool;
        this.city = city;
    }

    /**
     * Gets the unique code of the school.
     *
     * @return the code of the school
     */
    public Long getCode() {
        return code;
    }

    /**
     * Sets the unique code of the school.
     *
     * @param code the code to be set for the school
     */
    public void setCode(Long code) {
        this.code = code;
    }

    /**
     * Gets the name of the school.
     *
     * @return the name of the school
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the school.
     *
     * @param name the name to be set for the school
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the type of the school.
     *
     * @return the type of the school
     */
    public TypeOfSchool getTypeOfSchool() {
        return typeOfSchool;
    }

    /**
     * Sets the type of the school.
     *
     * @param typeOfSchool the type to be set for the school
     */
    public void setTypeOfSchool(TypeOfSchool typeOfSchool) {
        this.typeOfSchool = typeOfSchool;
    }

    /**
     * Gets the city where the school is located.
     *
     * @return the city of the school
     */
    public City getCity() {
        return city;
    }

    /**
     * Sets the city where the school is located.
     *
     * @param city the city to be set for the school
     */
    public void setCity(City city) {
        this.city = city;
    }

    /**
     * Determines if this School is equal to another object.
     * Comparison is based on the school's code.
     *
     * @param o the object to compare with
     * @return
     * <ul>
     *     <li>{@code true} - if this School has the same code as the other object</li>
     *     <li>{@code false} - otherwise</li>
     * </ul>
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        School school = (School) o;
        return Objects.equals(code, school.code);
    }

    /**
     * Calculates the hash code of this School object.
     * The hash code is based on the school's code.
     *
     * @return the hash code value for this School object
     */
    @Override
    public int hashCode() {
        return Objects.hash(code);
    }

    /**
     * Returns a string representation of the School object.
     *
     * @return a string representation of the School object
     */
    @Override
    public String toString() {
        return "School{" +
                "code=" + code +
                ", name='" + name + '\'' +
                ", typeOfSchool=" + typeOfSchool +
                ", city=" + city +
                '}';
    }
}

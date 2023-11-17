package rs.ac.bg.fon.silab.mock_exam.domain.city.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

import java.util.Objects;

/**
 * Represents a city where specific school is based or where candidate is from.
 * This entity stores information about the city, including its ZIP code and name.
 *
 * <p> This class includes methods to get and set its fields.
 * It also overrides the {@code equals}, {@code hashCode}, {@code toString} methods from the {@code Object} class.</p>
 */
@Entity
public class City {

    /**
     * Unique ZIP code for the city. It serves as the primary identifier.
     */
    @Id
    private Long zipCode;

    /**
     * Name of the city. It is a required field.
     */
    @Column(columnDefinition = "VARCHAR(45)", nullable = false)
    private String name;

    /**
     * Default constructor for JPA.
     */
    public City() {
    }

    /**
     * Constructs a new City with a specified ZIP code and name.
     *
     * @param zipCode the ZIP code of the city, not null
     * @param name the name of the city, not null
     */
    public City(Long zipCode, String name) {
        this.zipCode = zipCode;
        this.name = name;
    }

    /**
     * Gets the ZIP code of the city.
     *
     * @return the ZIP code of the city
     */
    public Long getZipCode() {
        return zipCode;
    }

    /**
     * Sets the ZIP code of the city.
     *
     * @param zipCode the ZIP code to be set for the city
     */
    public void setZipCode(Long zipCode) {
        this.zipCode = zipCode;
    }

    /**
     * Gets the name of the city.
     *
     * @return the name of the city
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the city.
     *
     * @param name the name to be set for the city, not null
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Indicates whether some other {@link City} is "equal to" this one based on the ZIP code.
     *
     * @param o the reference object with which to compare
     * @return
     * <ul>
     *     <li>{@code true} - if this City is the same as the object argument</li>
     *     <li>{@code false} - otherwise</li>
     * </ul>
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        City city = (City) o;
        return Objects.equals(zipCode,city.zipCode);
    }

    /**
     * Calculates hash code based on the ZIP code.
     *
     * @return hash code value for this object based on the ZIP code
     */
    @Override
    public int hashCode() {
        return Objects.hash(zipCode);
    }

    /**
     * Returns a string representation of the city.
     *
     * @return a string representation of the city
     */
    @Override
    public String toString() {
        return "City{" +
                "zipCode=" + zipCode +
                ", name='" + name + '\'' +
                '}';
    }
}

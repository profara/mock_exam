package rs.ac.bg.fon.silab.mock_exam.domain.currency.entity;

import jakarta.persistence.*;

import java.util.Objects;

/**
 * Represents a currency.
 * This entity stores information about the currency, including its name and code.
 *
 * <p> This class includes methods to get and set its fields.
 * It also overrides the {@code equals}, {@code hashCode}, {@code toString} methods from the {@code Object} class.</p>
 */
@Entity
public class Currency {

    /**
     * Unique identifier for the currency. This is automatically generated.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Name of the currency. It is a required field.
     */
    @Column(columnDefinition = "VARCHAR(100)", nullable = false)
    private String name;

    /**
     * Code of the currency, typically a 3-letter code. It is a required field.
     */
    @Column(columnDefinition = "VARCHAR(3)", nullable = false)
    private String code;

    /**
     * Default constructor required by JPA. It is not intended for direct use.
     */
    public Currency() {
    }

    /**
     * Creates a new Currency with a specified name and code.
     *
     * @param name the name of the currency, not null
     * @param code the 3-letter code of the currency, not null
     */
    public Currency(String name, String code) {
        this.name = name;
        this.code = code;
    }

    /**
     * Gets the unique identifier of the currency.
     *
     * @return the identifier of the currency
     */
    public Long getId() {
        return id;
    }

    /**
     * Sets the unique identifier of the currency.
     *
     * @param id the identifier to be set for the currency
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Gets the name of the currency.
     *
     * @return the name of the currency
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the currency.
     *
     * @param name the name to be set for the currency, not null
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the code of the currency.
     *
     * @return the 3-letter code of the currency
     */
    public String getCode() {
        return code;
    }

    /**
     * Sets the code of the currency.
     *
     * @param code the 3-letter code to be set for the currency, not null
     */
    public void setCode(String code) {
        this.code = code;
    }

    /**
     * Indicates whether some other {@link Currency} is "equal to" this one based on the id.
     *
     * @param o the reference object with which to compare
     * @return
     * <ul>
     *     <li>{@code true} - if this Currency is the same as the object argument</li>
     *     <li>{@code false} - otherwise</li>
     * </ul>
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Currency currency = (Currency) o;
        return Objects.equals(id, currency.id);
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
     * Returns a string representation of the currency.
     *
     * @return a string representation of the currency
     */
    @Override
    public String toString() {
        return "Currency{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", code='" + code + '\'' +
                '}';
    }
}

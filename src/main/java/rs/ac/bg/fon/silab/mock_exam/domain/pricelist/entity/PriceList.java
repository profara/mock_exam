package rs.ac.bg.fon.silab.mock_exam.domain.pricelist.entity;

import jakarta.persistence.*;

import java.time.Year;
import java.util.Objects;

import static rs.ac.bg.fon.silab.mock_exam.infrastructure.config.Constants.PRICE_LIST_TABLE_NAME;

/**
 * Represents a price list for a given year.
 * This entity keeps track of pricing information for various exams by different criteria for a specific year.
 */
@Entity
@Table(name = PRICE_LIST_TABLE_NAME)
public class PriceList {

    /**
     * Unique identifier for the price list. This is automatically generated.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * The year associated with this price list. This is a required field.
     */
    @Column(nullable = false)
    private Year year;

    /**
     * Default constructor required by JPA. It is not intended for direct use.
     */
    public PriceList() {
    }

    /**
     * Creates a new price list for the specified year.
     *
     * @param year the year for which the price list is valid, not null
     */
    public PriceList(Year year) {
        this.year = year;
    }

    /**
     * Gets the unique identifier of the price list.
     *
     * @return the identifier of the price list
     */
    public Long getId() {
        return id;
    }

    /**
     * Sets the unique identifier of the price list.
     *
     * @param id the identifier to be set for the price list
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Gets the year associated with the price list.
     *
     * @return the year linked to the price list
     */
    public Year getYear() {
        return year;
    }

    /**
     * Sets the year associated with the price list.
     *
     * @param year the year to be linked to the price list, not null
     */
    public void setYear(Year year) {
        this.year = year;
    }

    /**
     * Indicates whether some other {@link PriceList} is "equal to" this one based on the id.
     *
     * @param o the reference object with which to compare
     * @return
     * <ul>
     *     <li>{@code true} - if this PriceList is the same as the object argument</li>
     *     <li>{@code false} - otherwise</li>
     * </ul>
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PriceList priceList = (PriceList) o;
        return Objects.equals(id, priceList.id);
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
        return "PriceList{" +
                "id=" + id +
                ", year=" + year +
                '}';
    }
}

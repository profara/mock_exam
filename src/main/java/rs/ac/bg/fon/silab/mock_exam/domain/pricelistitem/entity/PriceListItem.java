package rs.ac.bg.fon.silab.mock_exam.domain.pricelistitem.entity;

import jakarta.persistence.*;
import org.springframework.data.domain.Persistable;
import rs.ac.bg.fon.silab.mock_exam.domain.currency.entity.Currency;
import rs.ac.bg.fon.silab.mock_exam.domain.exam.entity.Exam;
import rs.ac.bg.fon.silab.mock_exam.domain.pricelist.entity.PriceList;

import java.math.BigDecimal;
import java.util.Objects;

import static rs.ac.bg.fon.silab.mock_exam.infrastructure.config.Constants.*;

/**
 * Represents an item in a price list for mock exams.
 * This entity is linked to {@link PriceList}, {@link Currency}, and {@link Exam} entities.
 * It tracks the price of an exam for a specific year and whether the price is for privileged candidates.
 */
@Entity
@Table(name = PRICE_LIST_ITEM_TABLE_NAME)
public class PriceListItem   {

    /**
     * Unique identifier for the price list item. This is automatically generated.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * The price list to which this item belongs. The association is mandatory.
     */
    @ManyToOne(optional = false)
    @JoinColumn(name = FOREIGN_KEY_PRICE_LIST)
    private PriceList priceList;

    /**
     * The price of the exam. It is a required field.
     */
    @Column(columnDefinition = "DECIMAL(15,2)", nullable = false)
    private BigDecimal price;

    /**
     * Flag indicating whether this price is for privileged candidates.
     */
    @Column(columnDefinition = "TINYINT(1)", nullable = false)
    private boolean privileged;

    /**
     * The currency in which the price is set. The association is mandatory.
     */
    @ManyToOne(optional = false)
    @JoinColumn(name = FOREIGN_KEY_CURRENCY)
    private Currency currency;

    /**
     * The exam associated with this price list item. The association is mandatory.
     */
    @ManyToOne(optional = false)
    @JoinColumn(name = FOREIGN_KEY_EXAM)
    private Exam exam;

    /**
     * Default constructor for JPA.
     */
    public PriceListItem() {
    }

    /**
     * Constructs a new PriceListItem with specified price and privileged status.
     *
     * @param price The price of the exam.
     * @param privileged Indicates whether the price is for privileged candidates.
     */
    public PriceListItem(BigDecimal price, boolean privileged) {
        this.price = price;
        this.privileged = privileged;
    }

    /**
     * Constructs a new PriceListItem with detailed information.
     *
     * @param priceList The price list to which this item belongs.
     * @param price The price of the exam.
     * @param privileged Indicates whether the price is for privileged candidates.
     * @param currency The currency in which the price is set.
     * @param exam The exam associated with this price list item.
     */
    public PriceListItem(PriceList priceList, BigDecimal price, boolean privileged, Currency currency, Exam exam) {
        this.priceList = priceList;
        this.price = price;
        this.privileged = privileged;
        this.currency = currency;
        this.exam = exam;
    }

    /**
     * Gets the unique identifier of the price list item.
     *
     * @return the identifier of the price list item
     */
    public Long getId() {
        return id;
    }

    /**
     * Sets the unique identifier of the price list item.
     *
     * @param id the identifier to be set for the price list item
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Gets the PriceList to which this item belongs.
     *
     * @return The associated PriceList.
     */
    public PriceList getPriceList() {
        return priceList;
    }

    /**
     * Sets the PriceList to which this item belongs.
     *
     * @param priceList The PriceList to associate with this item.
     */
    public void setPriceList(PriceList priceList) {
        this.priceList = priceList;
    }

    /**
     * Gets the price of the exam in this PriceListItem.
     *
     * @return The price of the exam.
     */
    public BigDecimal getPrice() {
        return price;
    }

    /**
     * Sets the price of the exam in this PriceListItem.
     *
     * @param price The price to be set for the exam.
     */
    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    /**
     * Checks if this PriceListItem is for privileged candidates.
     *
     * @return
     * <ul>
     *     <li>{@code true} - if it is for privileged candidates</li>
     *     <li>{@code false} - otjerwise</li>
     * </ul>
     */
    public boolean isPrivileged() {
        return privileged;
    }

    /**
     * Sets the privileged status of this PriceListItem.
     *
     * @param privileged The privileged status to be set.
     */
    public void setPrivileged(boolean privileged) {
        this.privileged = privileged;
    }

    /**
     * Gets the currency of the price in this PriceListItem.
     *
     * @return The Currency of the price.
     */
    public Currency getCurrency() {
        return currency;
    }

    /**
     * Sets the currency of the price in this PriceListItem.
     *
     * @param currency The Currency to be set.
     */
    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    /**
     * Gets the Exam associated with this PriceListItem.
     *
     * @return The associated Exam.
     */
    public Exam getExam() {
        return exam;
    }

    /**
     * Sets the Exam associated with this PriceListItem.
     *
     * @param exam The Exam to be associated with.
     */
    public void setExam(Exam exam) {
        this.exam = exam;
    }

    /**
     * Indicates whether some other {@link PriceListItem} is "equal to" this one based on the id.
     *
     * @param o The reference object with which to compare.
     * @return
     * <ul>
     *     <li>{@code true} - if this object is the same as the o argument</li>
     *     <li>{@code false} - otherwise</li>
     * </ul>
     * true if this object is the same as the o argument; false otherwise.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PriceListItem that = (PriceListItem) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    /**
     * Returns a string representation of the PriceListItem object.
     *
     * @return A string representation of the PriceListItem object.
     */
    @Override
    public String toString() {
        return "PriceListItem{" +
                "id=" + id +
                ", priceList=" + priceList +
                ", price=" + price +
                ", privileged=" + privileged +
                ", currency=" + currency +
                ", exam=" + exam +
                '}';
    }
}

package rs.ac.bg.fon.silab.mock_exam.entities;

import jakarta.persistence.*;
import rs.ac.bg.fon.silab.mock_exam.entities.composite.keys.PriceListItemId;

import java.math.BigDecimal;
import java.util.Objects;

import static rs.ac.bg.fon.silab.mock_exam.config.Constants.*;

@Entity
@Table(name = PRICE_LIST_ITEM_TABLE_NAME)
public class PriceListItem {

    @EmbeddedId
    private PriceListItemId priceListItemId;
    @Column(columnDefinition = "DECIMAL(15,2)", nullable = false)
    private BigDecimal price;
    @Column(columnDefinition = "TINYINT(1)", nullable = false)
    private boolean privileged;
    @ManyToOne(optional = false)
    @JoinColumn(name = FOREIGN_KEY_CURRENCY)
    private Currency currency;
    @ManyToOne(optional = false)
    @JoinColumn(name = FOREIGN_KEY_EXAM)
    private Exam exam;

    public PriceListItem() {
    }

    public PriceListItem(BigDecimal price, boolean privileged, Currency currency, Exam exam) {
        this.price = price;
        this.privileged = privileged;
        this.currency = currency;
        this.exam = exam;
    }

    public PriceListItem(PriceListItemId priceListItemId, BigDecimal price, boolean privileged, Currency currency, Exam exam) {
        this.priceListItemId = priceListItemId;
        this.price = price;
        this.privileged = privileged;
        this.currency = currency;
        this.exam = exam;
    }

    public PriceListItemId getPriceListItemId() {
        return priceListItemId;
    }

    public void setPriceListItemId(PriceListItemId priceListItemId) {
        this.priceListItemId = priceListItemId;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public boolean isPrivileged() {
        return privileged;
    }

    public void setPrivileged(boolean privileged) {
        this.privileged = privileged;
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    public Exam getExam() {
        return exam;
    }

    public void setExam(Exam exam) {
        this.exam = exam;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PriceListItem that = (PriceListItem) o;
        return Objects.equals(priceListItemId, that.priceListItemId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(priceListItemId);
    }

    @Override
    public String toString() {
        return "PriceListItem{" +
                "priceListItemId=" + priceListItemId +
                ", price=" + price +
                ", privileged=" + privileged +
                ", currency=" + currency +
                ", exam=" + exam +
                '}';
    }
}

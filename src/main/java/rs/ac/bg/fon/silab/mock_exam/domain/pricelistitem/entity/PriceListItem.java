package rs.ac.bg.fon.silab.mock_exam.domain.pricelistitem.entity;

import jakarta.persistence.*;
import org.springframework.data.domain.Persistable;
import rs.ac.bg.fon.silab.mock_exam.domain.currency.entity.Currency;
import rs.ac.bg.fon.silab.mock_exam.domain.exam.entity.Exam;
import rs.ac.bg.fon.silab.mock_exam.domain.pricelist.entity.PriceList;

import java.math.BigDecimal;
import java.util.Objects;

import static rs.ac.bg.fon.silab.mock_exam.infrastructure.config.Constants.*;

@Entity
@Table(name = PRICE_LIST_ITEM_TABLE_NAME)
public class PriceListItem   {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(optional = false)
    @JoinColumn(name = FOREIGN_KEY_PRICE_LIST)
    private PriceList priceList;
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

    public PriceListItem(BigDecimal price, boolean privileged) {
        this.price = price;
        this.privileged = privileged;
    }

    public PriceListItem(PriceList priceList, BigDecimal price, boolean privileged, Currency currency, Exam exam) {
        this.priceList = priceList;
        this.price = price;
        this.privileged = privileged;
        this.currency = currency;
        this.exam = exam;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public PriceList getPriceList() {
        return priceList;
    }

    public void setPriceList(PriceList priceList) {
        this.priceList = priceList;
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
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

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

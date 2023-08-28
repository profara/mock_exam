package rs.ac.bg.fon.silab.mock_exam.entities.composite.keys;

import jakarta.persistence.Embeddable;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import rs.ac.bg.fon.silab.mock_exam.domain.pricelist.entity.PriceList;

import static rs.ac.bg.fon.silab.mock_exam.infrastructure.config.Constants.FOREIGN_KEY_PRICE_LIST;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class PriceListItemId implements Serializable {

    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = FOREIGN_KEY_PRICE_LIST)
    private PriceList priceList;

    public PriceListItemId() {
    }

    public PriceListItemId(PriceList priceList) {
        this.priceList = priceList;
    }

    public PriceListItemId(Long id, PriceList priceList) {
        this.id = id;
        this.priceList = priceList;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PriceListItemId that = (PriceListItemId) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "PriceListItemId{" +
                "id=" + id +
                ", priceList=" + priceList +
                '}';
    }
}

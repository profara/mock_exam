package rs.ac.bg.fon.silab.mock_exam.entities;

import jakarta.persistence.*;

import java.time.Year;
import java.util.Objects;

import static rs.ac.bg.fon.silab.mock_exam.config.Constants.PRICE_LIST_TABLE_NAME;

@Entity
@Table(name = PRICE_LIST_TABLE_NAME)
public class PriceList {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private Year year;

    public PriceList() {
    }

    public PriceList(Year year) {
        this.year = year;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Year getYear() {
        return year;
    }

    public void setYear(Year year) {
        this.year = year;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PriceList priceList = (PriceList) o;
        return Objects.equals(id, priceList.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "PriceList{" +
                "id=" + id +
                ", year=" + year +
                '}';
    }
}

package rs.ac.bg.fon.silab.mock_exam.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

import java.util.Objects;

@Entity
public class City {

    @Id
    private int zipCode;
    @Column(columnDefinition = "VARCHAR(45)", nullable = false)
    private String name;

    public City() {
    }

    public City(int zipCode, String name) {
        this.zipCode = zipCode;
        this.name = name;
    }

    public int getZipCode() {
        return zipCode;
    }

    public void setZipCode(int zipCode) {
        this.zipCode = zipCode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        City city = (City) o;
        return zipCode == city.zipCode;
    }

    @Override
    public int hashCode() {
        return Objects.hash(zipCode);
    }

    @Override
    public String toString() {
        return "City{" +
                "zipCode=" + zipCode +
                ", name='" + name + '\'' +
                '}';
    }
}

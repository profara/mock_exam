package rs.ac.bg.fon.silab.mock_exam.entities;

import jakarta.persistence.*;

import java.util.Objects;

import static rs.ac.bg.fon.silab.mock_exam.infrastructure.config.Constants.FOREIGN_KEY_CITY;
import static rs.ac.bg.fon.silab.mock_exam.infrastructure.config.Constants.FOREIGN_KEY_TYPE_OF_SCHOOL;

@Entity
public class School {

    @Id
    private int code;
    @Column(columnDefinition = "VARCHAR(100)",nullable = false)
    private String name;
    @ManyToOne(optional = false)
    @JoinColumn(name = FOREIGN_KEY_TYPE_OF_SCHOOL)
    private TypeOfSchool typeOfSchool;
    @ManyToOne(optional = false)
    @JoinColumn(name = FOREIGN_KEY_CITY)
    private City city;

    public School() {
    }

    public School(int code, String name, TypeOfSchool typeOfSchool, City city) {
        this.code = code;
        this.name = name;
        this.typeOfSchool = typeOfSchool;
        this.city = city;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public TypeOfSchool getTypeOfSchool() {
        return typeOfSchool;
    }

    public void setTypeOfSchool(TypeOfSchool typeOfSchool) {
        this.typeOfSchool = typeOfSchool;
    }

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        School school = (School) o;
        return code == school.code;
    }

    @Override
    public int hashCode() {
        return Objects.hash(code);
    }

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

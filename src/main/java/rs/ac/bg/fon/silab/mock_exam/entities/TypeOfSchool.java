package rs.ac.bg.fon.silab.mock_exam.entities;

import jakarta.persistence.*;

import java.util.Objects;

import static rs.ac.bg.fon.silab.mock_exam.config.Constants.TYPE_OF_SCHOOL_TABLE_NAME;

@Entity
@Table(name = TYPE_OF_SCHOOL_TABLE_NAME)
public class TypeOfSchool {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(columnDefinition = "VARCHAR(45)",nullable = false)
    private String name;

    public TypeOfSchool() {
    }

    public TypeOfSchool(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
        TypeOfSchool that = (TypeOfSchool) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "TypeOfSchool{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}

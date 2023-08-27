package rs.ac.bg.fon.silab.mock_exam.domain.typeofschool.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import rs.ac.bg.fon.silab.mock_exam.domain.typeofschool.entity.TypeOfSchool;
public interface TypeOfSchoolRepository extends JpaRepository<TypeOfSchool, Long> {
    TypeOfSchool findByName(String name);
}

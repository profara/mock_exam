package rs.ac.bg.fon.silab.mock_exam.domain.typeofschool.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import rs.ac.bg.fon.silab.mock_exam.domain.typeofschool.entity.TypeOfSchool;
@Repository
public interface TypeOfSchoolRepository extends JpaRepository<TypeOfSchool, Long> {
}

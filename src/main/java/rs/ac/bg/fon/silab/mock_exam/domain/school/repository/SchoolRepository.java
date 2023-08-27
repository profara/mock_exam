package rs.ac.bg.fon.silab.mock_exam.domain.school.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import rs.ac.bg.fon.silab.mock_exam.domain.school.entity.School;

public interface SchoolRepository extends JpaRepository<School, Long> {
}

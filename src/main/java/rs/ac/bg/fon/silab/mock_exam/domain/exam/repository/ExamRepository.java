package rs.ac.bg.fon.silab.mock_exam.domain.exam.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import rs.ac.bg.fon.silab.mock_exam.domain.exam.entity.Exam;

public interface ExamRepository extends JpaRepository<Exam, Long> {
    Exam findByName(String name);
}

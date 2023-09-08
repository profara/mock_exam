package rs.ac.bg.fon.silab.mock_exam.domain.application.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import rs.ac.bg.fon.silab.mock_exam.domain.application.entity.Application;

public interface ApplicationRepository extends JpaRepository<Application, Long> {
}

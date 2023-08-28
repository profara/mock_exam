package rs.ac.bg.fon.silab.mock_exam.domain.candidate.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import rs.ac.bg.fon.silab.mock_exam.domain.candidate.entity.Candidate;

public interface CandidateRepository extends JpaRepository<Candidate, Long> {
}

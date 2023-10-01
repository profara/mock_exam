package rs.ac.bg.fon.silab.mock_exam.domain.candidate.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import rs.ac.bg.fon.silab.mock_exam.domain.candidate.entity.Candidate;

import java.util.Optional;

public interface CandidateRepository extends JpaRepository<Candidate, Long> {
    Optional<Candidate> findByUserProfile_Email(String email);
}

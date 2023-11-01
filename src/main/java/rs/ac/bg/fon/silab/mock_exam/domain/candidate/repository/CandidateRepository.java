package rs.ac.bg.fon.silab.mock_exam.domain.candidate.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import rs.ac.bg.fon.silab.mock_exam.domain.candidate.entity.Candidate;

import java.util.List;
import java.util.Optional;

public interface CandidateRepository extends JpaRepository<Candidate, Long> {
    Optional<Candidate> findByUserProfile_Email(String email);

    @Query("SELECT app.candidate FROM Application app JOIN app.appointments apt WHERE apt.id = :appointmentId")
    Page<Candidate> findByAppointmentId(@Param("appointmentId") Long appointmentId, Pageable pageable);

    @Query("SELECT app.candidate FROM Application app JOIN app.appointments apt WHERE apt.id = :appointmentId")
    List<Candidate> findAllByAppointmentId(@Param("appointmentId") Long appointmentId);
}

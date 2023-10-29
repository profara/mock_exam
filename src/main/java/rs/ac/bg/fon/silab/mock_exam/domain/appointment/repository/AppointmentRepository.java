package rs.ac.bg.fon.silab.mock_exam.domain.appointment.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import rs.ac.bg.fon.silab.mock_exam.domain.appointment.entity.Appointment;
import rs.ac.bg.fon.silab.mock_exam.domain.candidate.entity.Candidate;

import java.util.List;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
    @Query("SELECT app.candidate FROM Application app JOIN app.appointments apt WHERE apt.id = :id")
    Page<Candidate> findCandidatesByAppointmentId(@Param("id") Long id, Pageable pageable);

    @Query("SELECT app.candidate FROM Application app JOIN app.appointments apt WHERE apt.id = :id")
    List<Candidate> findAllCandidatesByAppointmentId(Long id);
}

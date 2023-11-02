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
    @Query("SELECT apt FROM Appointment apt JOIN apt.applications app WHERE app.candidate.id = :candidateId")
    Page<Appointment> findByCandidateId(@Param("candidateId") Long candidateId, Pageable pageable);


    List<Appointment> findAllByOrderByAppointmentDateAscExamNameAsc();
}

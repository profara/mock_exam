package rs.ac.bg.fon.silab.mock_exam.domain.appointment.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import rs.ac.bg.fon.silab.mock_exam.domain.appointment.entity.Appointment;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
}

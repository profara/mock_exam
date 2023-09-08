package rs.ac.bg.fon.silab.mock_exam.domain.payment.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import rs.ac.bg.fon.silab.mock_exam.domain.payment.entity.Payment;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
}

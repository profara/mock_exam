package rs.ac.bg.fon.silab.mock_exam.infrastructure.email;

import rs.ac.bg.fon.silab.mock_exam.domain.appointment.entity.Appointment;
import rs.ac.bg.fon.silab.mock_exam.domain.candidate.entity.Candidate;

public interface EmailSender {

    void send(String to, String email);

    void sendAttachment(String to, byte[] image);

    void sendReminderEmail(Candidate candidate, Appointment appointment);
}

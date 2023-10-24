package rs.ac.bg.fon.silab.mock_exam.infrastructure.email;

public interface EmailSender {

    void send(String to, String email);
}

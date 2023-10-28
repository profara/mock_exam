package rs.ac.bg.fon.silab.mock_exam.domain.application.exception;

public class DuplicateAppointmentApplicationException extends RuntimeException{

    public DuplicateAppointmentApplicationException(String message) {
        super(message);
    }
}

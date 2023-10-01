package rs.ac.bg.fon.silab.mock_exam.domain.userprofile.exception;

public class DuplicateUserException extends RuntimeException{

    public DuplicateUserException(String message) {
        super(message);
    }
}

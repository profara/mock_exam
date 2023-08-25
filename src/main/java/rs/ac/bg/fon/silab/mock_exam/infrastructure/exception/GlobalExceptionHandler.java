package rs.ac.bg.fon.silab.mock_exam.infrastructure.exception;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ErrorResponse> handleInvalidArgument(ConstraintViolationException ex){
        List<Violation> violations = new ArrayList<>();

        for(ConstraintViolation<?> violation: ex.getConstraintViolations()){
            violations.add(new Violation(violation.getPropertyPath().toString(), ex.getMessage(), LocalDateTime.now()));
        }

        var error = new ErrorResponse(violations);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleInvalidMethodArgument(MethodArgumentNotValidException ex){
        List<Violation> violations = new ArrayList<>();
        ex.getBindingResult()
                .getFieldErrors()
                .forEach(error->
                        violations.add(new Violation(error.getField(), error.getDefaultMessage(), LocalDateTime.now())));

        var error = new ErrorResponse(violations);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleNotFoundException(RuntimeException ex){
        Violation violation = new Violation(null, ex.getMessage(), LocalDateTime.now());

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse(List.of(violation)));
    }

    private record Violation(String field, String error, LocalDateTime timestamp){}
    private record ErrorResponse(List<Violation> violations){}
}

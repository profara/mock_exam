package rs.ac.bg.fon.silab.mock_exam.infrastructure.exception;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import rs.ac.bg.fon.silab.mock_exam.domain.userprofile.exception.DuplicateUserException;

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

    @ExceptionHandler(DuplicateUserException.class)
    public ResponseEntity<ErrorResponse> handleDuplicateUserException(RuntimeException ex){
        Violation violation = new Violation(null, ex.getMessage(), LocalDateTime.now());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse(List.of(violation)));
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ApiError> handleException(BadCredentialsException e,
                                                    HttpServletRequest request) {
        ApiError apiError = new ApiError(
                request.getRequestURI(),
                e.getMessage(),
                HttpStatus.UNAUTHORIZED.value(),
                LocalDateTime.now()
        );

        return new ResponseEntity<>(apiError, HttpStatus.UNAUTHORIZED);
    }


    private record Violation(String field, String error, LocalDateTime timestamp){}
    private record ErrorResponse(List<Violation> violations){}
}

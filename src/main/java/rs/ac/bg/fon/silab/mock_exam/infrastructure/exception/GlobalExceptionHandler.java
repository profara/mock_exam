package rs.ac.bg.fon.silab.mock_exam.infrastructure.exception;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import rs.ac.bg.fon.silab.mock_exam.domain.userprofile.exception.DuplicateUserException;
import rs.ac.bg.fon.silab.mock_exam.infrastructure.security.exception.VerificationException;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

//@RestControllerAdvice
//public class GlobalExceptionHandler {
//
//    @ExceptionHandler(ConstraintViolationException.class)
//    public ResponseEntity<ErrorResponse> handleInvalidArgument(ConstraintViolationException ex){
//        List<Violation> violations = new ArrayList<>();
//
//        for(ConstraintViolation<?> violation: ex.getConstraintViolations()){
//            violations.add(new Violation(violation.getPropertyPath().toString(), ex.getMessage(), LocalDateTime.now()));
//        }
//
//        var error = new ErrorResponse(violations);
//        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
//    }
//
//    @ExceptionHandler(MethodArgumentNotValidException.class)
//    public ResponseEntity<ErrorResponse> handleInvalidMethodArgument(MethodArgumentNotValidException ex){
//        List<Violation> violations = new ArrayList<>();
//        ex.getBindingResult()
//                .getFieldErrors()
//                .forEach(error->
//                        violations.add(new Violation(error.getField(), error.getDefaultMessage(), LocalDateTime.now())));
//
//        var error = new ErrorResponse(violations);
//        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
//    }
//
//    @ExceptionHandler(EntityNotFoundException.class)
//    public ResponseEntity<ErrorResponse> handleNotFoundException(RuntimeException ex){
//        Violation violation = new Violation(null, ex.getMessage(), LocalDateTime.now());
//
//        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse(List.of(violation)));
//    }
//
//    @ExceptionHandler(DuplicateUserException.class)
//    public ResponseEntity<ApiError> handleDuplicateUserException(RuntimeException ex,
//                                                                      HttpServletRequest request){
////        Violation violation = new Violation(null, ex.getMessage(), LocalDateTime.now());
////
////        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse(List.of(violation)));
//        ApiError apiError = new ApiError(
//                request.getRequestURI(),
//                ex.getMessage(),
//                HttpStatus.BAD_REQUEST.value(),
//                LocalDateTime.now()
//        );
//
//        return new ResponseEntity<>(apiError, HttpStatus.BAD_REQUEST);
//    }
//
//    @ExceptionHandler(BadCredentialsException.class)
//    public ResponseEntity<ApiError> handleException(BadCredentialsException e,
//                                                    HttpServletRequest request) {
//        ApiError apiError = new ApiError(
//                request.getRequestURI(),
//                e.getMessage(),
//                HttpStatus.UNAUTHORIZED.value(),
//                LocalDateTime.now()
//        );
//
//        return new ResponseEntity<>(apiError, HttpStatus.UNAUTHORIZED);
//    }
//
//    @ExceptionHandler(VerificationException.class)
//    public ResponseEntity<ApiError> handleAuthenticationException(VerificationException e,
//                                                    HttpServletRequest request) {
//        ApiError apiError = new ApiError(
//                request.getRequestURI(),
//                e.getMessage(),
//                HttpStatus.UNAUTHORIZED.value(),
//                LocalDateTime.now()
//        );
//
//        return new ResponseEntity<>(apiError, HttpStatus.UNAUTHORIZED);
//    }
//
//
//    private record Violation(String field, String error, LocalDateTime timestamp){}
//    private record ErrorResponse(List<Violation> violations){}
//}

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ApiError> handleInvalidArgument(ConstraintViolationException ex, HttpServletRequest request) {
        String errorMsg = ex.getConstraintViolations()
                .stream()
                .map(v -> v.getPropertyPath().toString() + ": " + v.getMessage())
                .collect(Collectors.joining(", "));

        return buildErrorResponse(request, HttpStatus.BAD_REQUEST, errorMsg);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiError> handleInvalidMethodArgument(MethodArgumentNotValidException ex, HttpServletRequest request) {
        String errorMsg = ex.getBindingResult().getFieldErrors()
                .stream()
                .map(e -> e.getField() + ": " + e.getDefaultMessage())
                .collect(Collectors.joining(", "));

        return buildErrorResponse(request, HttpStatus.BAD_REQUEST, errorMsg);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ApiError> handleNotFoundException(RuntimeException ex, HttpServletRequest request) {
        return buildErrorResponse(request, HttpStatus.NOT_FOUND, ex.getMessage());
    }

    @ExceptionHandler(DuplicateUserException.class)
    public ResponseEntity<ApiError> handleDuplicateUserException(RuntimeException ex, HttpServletRequest request) {
        return buildErrorResponse(request, HttpStatus.BAD_REQUEST, ex.getMessage());
    }

    @ExceptionHandler({BadCredentialsException.class, VerificationException.class})
    public ResponseEntity<ApiError> handleAuthenticationExceptions(RuntimeException e, HttpServletRequest request) {
        return buildErrorResponse(request, HttpStatus.UNAUTHORIZED, e.getMessage());
    }

    private ResponseEntity<ApiError> buildErrorResponse(HttpServletRequest request, HttpStatus status, String errorMsg) {
        ApiError apiError = new ApiError(
                request.getRequestURI(),
                errorMsg,
                status.value(),
                LocalDateTime.now()
        );

        return new ResponseEntity<>(apiError, status);
    }

    private record ApiError(String path, String error, int status, LocalDateTime timestamp) {}
}


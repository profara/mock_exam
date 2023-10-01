package rs.ac.bg.fon.silab.mock_exam.infrastructure.exception;

import java.time.LocalDateTime;

public record ApiError(
        String path,
        String message,
        int statusCode,
        LocalDateTime localDateTime
) {
}

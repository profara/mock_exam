package rs.ac.bg.fon.silab.mock_exam.infrastructure.security.auth;

import jakarta.validation.Valid;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import rs.ac.bg.fon.silab.mock_exam.domain.userprofile.dto.UserProfileRequestUpdateDTO;

/**
 * Controller for handling authentication-related requests.
 * This controller provides endpoints for user authentication operations like logging in.
 */
@RestController
@RequestMapping("/api/auth")
public class AuthenticationController {

    private final AuthenticationService service;

    /**
     * Constructs an AuthenticationController with the necessary AuthenticationService.
     *
     * @param service Service to handle authentication logic.
     */
    public AuthenticationController(AuthenticationService service) {
        this.service = service;
    }

    /**
     * Endpoint for user login.
     * This method handles login requests, validates user credentials,
     * and returns a response with an authorization token if successful.
     *
     * @param request Data transfer object containing user login information.
     * @return ResponseEntity containing either an authorization token or error message.
     */
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody @Valid UserProfileRequestUpdateDTO request){
        AuthenticationResponseDTO response = service.login(request);
        return ResponseEntity.ok()
                .header(HttpHeaders.AUTHORIZATION, response.token())
                .body(response);
    }
}

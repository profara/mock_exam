package rs.ac.bg.fon.silab.mock_exam.infrastructure.security.auth;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;
import rs.ac.bg.fon.silab.mock_exam.domain.userprofile.dto.UserProfileRequestUpdateDTO;
import rs.ac.bg.fon.silab.mock_exam.domain.userprofile.dto.UserProfileResponseDTO;
import rs.ac.bg.fon.silab.mock_exam.domain.userprofile.entity.UserProfile;
import rs.ac.bg.fon.silab.mock_exam.domain.userprofile.mapper.UserProfileMapper;
import rs.ac.bg.fon.silab.mock_exam.infrastructure.jwt.JWTUtil;
import rs.ac.bg.fon.silab.mock_exam.infrastructure.security.exception.VerificationException;

/**
 * Service class for authentication operations.
 * This service provides functionalities to authenticate users and issue JWT tokens.
 */
@Service
public class AuthenticationService {

    private final AuthenticationManager authenticationManager;
    private final JWTUtil jwtUtil;
    private final UserProfileMapper userProfileMapper;

    /**
     * Constructs an AuthenticationService with the necessary dependencies.
     *
     * @param authenticationManager Manager to handle authentication processes.
     * @param jwtUtil Utility to handle JWT token operations.
     * @param userProfileMapper Mapper to convert UserProfile entities to DTOs.
     */
    public AuthenticationService(AuthenticationManager authenticationManager, JWTUtil jwtUtil, UserProfileMapper userProfileMapper) {
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
        this.userProfileMapper = userProfileMapper;
    }

    /**
     * Authenticates a user based on email and password.
     * If authentication is successful, a JWT token is issued.
     *
     * @param request DTO containing the user's email and password.
     * @return AuthenticationResponseDTO containing the JWT token and user profile details.
     * @throws BadCredentialsException if the credentials are incorrect.
     * @throws AuthenticationException if the account is not verified.
     */
    public AuthenticationResponseDTO login(UserProfileRequestUpdateDTO request){
            try {
                Authentication authentication = authenticationManager.authenticate(
                        new UsernamePasswordAuthenticationToken(
                                request.email(),
                                request.password()
                        )
                );
                UserProfile userProfile = (UserProfile) authentication.getPrincipal();

                UserProfileResponseDTO userProfileResponseDTO = userProfileMapper.map(userProfile);

                String token = jwtUtil.issueToken(userProfileResponseDTO.email(), userProfileResponseDTO.userRole().name());

                return new AuthenticationResponseDTO(token, userProfileResponseDTO);
            } catch(BadCredentialsException ex){
                throw new BadCredentialsException("Pogresan email/lozinka");
            } catch (AuthenticationException ex){
                throw new VerificationException("Nalog nije verifikovan!");
            }
    }
}

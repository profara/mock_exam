package rs.ac.bg.fon.silab.mock_exam.infrastructure.security.auth;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import rs.ac.bg.fon.silab.mock_exam.domain.userprofile.dto.UserProfileRequestUpdateDTO;
import rs.ac.bg.fon.silab.mock_exam.domain.userprofile.dto.UserProfileResponseDTO;
import rs.ac.bg.fon.silab.mock_exam.domain.userprofile.entity.UserProfile;
import rs.ac.bg.fon.silab.mock_exam.domain.userprofile.mapper.UserProfileMapper;
import rs.ac.bg.fon.silab.mock_exam.infrastructure.jwt.JWTUtil;

@Service
public class AuthenticationService {

    private final AuthenticationManager authenticationManager;
    private final JWTUtil jwtUtil;
    private final UserProfileMapper userProfileMapper;

    public AuthenticationService(AuthenticationManager authenticationManager, JWTUtil jwtUtil, UserProfileMapper userProfileMapper) {
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
        this.userProfileMapper = userProfileMapper;
    }

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
            } catch(AuthenticationException ex){
                throw new BadCredentialsException("Pogresan email/lozinka");
            }
    }
}

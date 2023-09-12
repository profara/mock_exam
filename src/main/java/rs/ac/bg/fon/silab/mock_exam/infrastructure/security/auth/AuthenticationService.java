package rs.ac.bg.fon.silab.mock_exam.infrastructure.security.auth;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import rs.ac.bg.fon.silab.mock_exam.domain.userprofile.entity.UserProfile;
import rs.ac.bg.fon.silab.mock_exam.domain.userprofile.repository.UserProfileRepository;
import rs.ac.bg.fon.silab.mock_exam.domain.userrole.repository.UserRoleRepository;
import rs.ac.bg.fon.silab.mock_exam.infrastructure.security.config.JwtService;

@Service
public class AuthenticationService {

    private final UserProfileRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final UserRoleRepository userRoleRepository;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthenticationService(UserProfileRepository repository, PasswordEncoder passwordEncoder, UserRoleRepository userRoleRepository, JwtService jwtService, AuthenticationManager authenticationManager) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
        this.userRoleRepository = userRoleRepository;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
    }


    public AuthenticationResponse register(RegisterRequest request) {
        UserProfile userProfile = new UserProfile(
                request.getEmail(),
                passwordEncoder.encode(request.getPassword()),
                userRoleRepository.findByName("user")
        );

        repository.save(userProfile);
        var jwtToken = jwtService.generateToken(userProfile);
        return new AuthenticationResponse(jwtToken);
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );
        var userProfile = repository.findByEmail(request.getEmail());
        var jwtToken = jwtService.generateToken(userProfile);
        return new AuthenticationResponse(jwtToken);
    }
}

package rs.ac.bg.fon.silab.mock_exam.domain.userprofile.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import rs.ac.bg.fon.silab.mock_exam.domain.userprofile.repository.UserProfileRepository;

/**
 * Service implementation of UserDetailsService for loading user-specific data.
 * It is used by the Spring Security framework to load user details during authentication.
 */
@Service
public class UserProfileUserDetailsService implements UserDetailsService {

    private final UserProfileRepository userProfileRepository;

    /**
     * Constructs a new UserProfileUserDetailsService with the user profile repository.
     *
     * @param userProfileRepository the repository for accessing user profile data
     */
    public UserProfileUserDetailsService(UserProfileRepository userProfileRepository) {
        this.userProfileRepository = userProfileRepository;
    }

    /**
     * Loads the user details by username (email in this context).
     *
     * @param username the username (email) to search for
     * @return UserDetails containing user information if found
     * @throws UsernameNotFoundException if no user is found with the given username (email)
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userProfileRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("Korisnik sa unetim email-om ne postoji!"));
    }
}

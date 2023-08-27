package rs.ac.bg.fon.silab.mock_exam.domain.userprofile.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import rs.ac.bg.fon.silab.mock_exam.domain.userprofile.entity.UserProfile;
public interface UserProfileRepository extends JpaRepository<UserProfile, Long> {
}

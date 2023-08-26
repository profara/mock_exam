package rs.ac.bg.fon.silab.mock_exam.domain.user.profile.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import rs.ac.bg.fon.silab.mock_exam.domain.user.profile.entity.UserProfile;
@Repository
public interface UserProfileRepository extends JpaRepository<UserProfile, Long> {
}

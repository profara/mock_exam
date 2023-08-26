package rs.ac.bg.fon.silab.mock_exam.domain.user.role.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import rs.ac.bg.fon.silab.mock_exam.domain.user.role.entity.UserRole;
@Repository
public interface UserRoleRepository extends JpaRepository<UserRole, Long> {
    UserRole findByName(String name);
}

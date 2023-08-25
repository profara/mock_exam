package rs.ac.bg.fon.silab.mock_exam.domain.user.role.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import rs.ac.bg.fon.silab.mock_exam.domain.user.role.dto.UserRoleRequestDTO;
import rs.ac.bg.fon.silab.mock_exam.domain.user.role.dto.UserRoleResponseDTO;
import rs.ac.bg.fon.silab.mock_exam.domain.user.role.entity.UserRole;

@Mapper(componentModel = "spring")
public interface UserRoleMapper {

    UserRole map(UserRoleRequestDTO dto);

    UserRoleResponseDTO map(UserRole userRole);

    void update(@MappingTarget UserRole userRole, UserRoleRequestDTO dto);
}

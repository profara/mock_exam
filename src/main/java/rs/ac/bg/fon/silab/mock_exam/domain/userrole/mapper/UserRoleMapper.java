package rs.ac.bg.fon.silab.mock_exam.domain.userrole.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import rs.ac.bg.fon.silab.mock_exam.domain.userrole.dto.UserRoleRequestDTO;
import rs.ac.bg.fon.silab.mock_exam.domain.userrole.dto.UserRoleResponseDTO;
import rs.ac.bg.fon.silab.mock_exam.domain.userrole.entity.UserRole;

@Mapper(componentModel = "spring")
public interface UserRoleMapper {

    UserRole map(UserRoleRequestDTO dto);

    UserRoleResponseDTO map(UserRole userRole);

    void update(@MappingTarget UserRole userRole, UserRoleRequestDTO dto);
}

package rs.ac.bg.fon.silab.mock_exam.domain.userprofile.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import rs.ac.bg.fon.silab.mock_exam.domain.userprofile.dto.UserProfileRequestDTO;
import rs.ac.bg.fon.silab.mock_exam.domain.userprofile.dto.UserProfileRequestUpdateDTO;
import rs.ac.bg.fon.silab.mock_exam.domain.userprofile.dto.UserProfileResponseDTO;
import rs.ac.bg.fon.silab.mock_exam.domain.userprofile.dto.UserProfileUpdateRoleRequestDTO;
import rs.ac.bg.fon.silab.mock_exam.domain.userprofile.entity.UserProfile;
import rs.ac.bg.fon.silab.mock_exam.domain.userrole.service.UserRoleService;

@Mapper(componentModel = "spring", uses = UserRoleService.class)
public interface UserProfileMapper {
    @Mapping(source = "userRole.name", target = "userRole")
    UserProfile map(UserProfileRequestDTO dto);
    UserProfileResponseDTO map(UserProfile userProfile);
    @Mapping(source = "userRole.name", target = "userRole")
    void updateUserRole(@MappingTarget UserProfile userProfile, UserProfileUpdateRoleRequestDTO dto);

    void update(@MappingTarget UserProfile userProfile, UserProfileRequestUpdateDTO dto);
}

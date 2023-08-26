package rs.ac.bg.fon.silab.mock_exam.domain.user.profile.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import rs.ac.bg.fon.silab.mock_exam.domain.user.profile.dto.UserProfileRequestDTO;
import rs.ac.bg.fon.silab.mock_exam.domain.user.profile.dto.UserProfileRequestUpdateDTO;
import rs.ac.bg.fon.silab.mock_exam.domain.user.profile.dto.UserProfileResponseDTO;
import rs.ac.bg.fon.silab.mock_exam.domain.user.profile.dto.UserProfileUpdateRoleRequestDTO;
import rs.ac.bg.fon.silab.mock_exam.domain.user.profile.entity.UserProfile;

@Mapper(componentModel = "spring")
public interface UserProfileMapper {

    UserProfile map(UserProfileRequestDTO dto);
    UserProfileResponseDTO map(UserProfile userProfile);
    void updateUserRole(@MappingTarget UserProfile userProfile, UserProfileUpdateRoleRequestDTO dto);
    void update(@MappingTarget UserProfile userProfile, UserProfileRequestUpdateDTO dto);
}

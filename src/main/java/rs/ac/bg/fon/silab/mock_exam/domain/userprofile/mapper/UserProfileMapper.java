package rs.ac.bg.fon.silab.mock_exam.domain.userprofile.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import rs.ac.bg.fon.silab.mock_exam.domain.userprofile.dto.UserProfileRequestDTO;
import rs.ac.bg.fon.silab.mock_exam.domain.userprofile.dto.UserProfileRequestUpdateDTO;
import rs.ac.bg.fon.silab.mock_exam.domain.userprofile.dto.UserProfileResponseDTO;
import rs.ac.bg.fon.silab.mock_exam.domain.userprofile.dto.UserProfileUpdateRoleRequestDTO;
import rs.ac.bg.fon.silab.mock_exam.domain.userprofile.entity.UserProfile;

@Mapper(componentModel = "spring")
public interface UserProfileMapper {

    UserProfile map(UserProfileRequestDTO dto);
    UserProfileResponseDTO map(UserProfile userProfile);
    void updateUserRole(@MappingTarget UserProfile userProfile, UserProfileUpdateRoleRequestDTO dto);
    void update(@MappingTarget UserProfile userProfile, UserProfileRequestUpdateDTO dto);
}

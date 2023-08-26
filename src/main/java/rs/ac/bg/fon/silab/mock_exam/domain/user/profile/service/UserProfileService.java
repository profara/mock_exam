package rs.ac.bg.fon.silab.mock_exam.domain.user.profile.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import rs.ac.bg.fon.silab.mock_exam.domain.user.profile.dto.UserProfileRequestDTO;
import rs.ac.bg.fon.silab.mock_exam.domain.user.profile.dto.UserProfileRequestUpdateDTO;
import rs.ac.bg.fon.silab.mock_exam.domain.user.profile.dto.UserProfileResponseDTO;
import rs.ac.bg.fon.silab.mock_exam.domain.user.profile.dto.UserProfileUpdateRoleRequestDTO;

public interface UserProfileService {

    UserProfileResponseDTO save(UserProfileRequestDTO userProfileDTO);

    UserProfileResponseDTO getById(Long id);

    Page<UserProfileResponseDTO> get(Pageable pageable);

    void delete(Long id);

    UserProfileResponseDTO updateUserRole(Long id, UserProfileUpdateRoleRequestDTO userProfileDTO);

    UserProfileResponseDTO update(Long id, UserProfileRequestUpdateDTO userProfileDTO);
}

package rs.ac.bg.fon.silab.mock_exam.domain.userprofile.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import rs.ac.bg.fon.silab.mock_exam.domain.userprofile.dto.UserProfileRequestDTO;
import rs.ac.bg.fon.silab.mock_exam.domain.userprofile.dto.UserProfileResponseDTO;
import rs.ac.bg.fon.silab.mock_exam.domain.userprofile.dto.UserProfileUpdateRoleRequestDTO;
import rs.ac.bg.fon.silab.mock_exam.domain.userprofile.dto.UserProfileRequestUpdateDTO;

public interface UserProfileService {

    UserProfileResponseDTO save(UserProfileRequestDTO userProfileDTO);

    UserProfileResponseDTO getById(Long id);

    Page<UserProfileResponseDTO> get(Pageable pageable);

    void delete(Long id);

    UserProfileResponseDTO updateUserRole(Long id, UserProfileUpdateRoleRequestDTO userProfileDTO);

    UserProfileResponseDTO update(Long id, UserProfileRequestUpdateDTO userProfileDTO);
}

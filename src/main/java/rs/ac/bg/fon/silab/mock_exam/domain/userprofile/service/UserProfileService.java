package rs.ac.bg.fon.silab.mock_exam.domain.userprofile.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import rs.ac.bg.fon.silab.mock_exam.domain.userprofile.dto.RegistrationResponseDTO;
import rs.ac.bg.fon.silab.mock_exam.domain.userprofile.dto.UserProfileResponseDTO;
import rs.ac.bg.fon.silab.mock_exam.domain.userprofile.dto.UserProfileUpdateRoleRequestDTO;
import rs.ac.bg.fon.silab.mock_exam.domain.userprofile.dto.UserProfileRequestUpdateDTO;
import rs.ac.bg.fon.silab.mock_exam.domain.userprofile.entity.UserProfile;

public interface UserProfileService {

    UserProfile findByEmail(String email);
    RegistrationResponseDTO save(UserProfileRequestUpdateDTO userProfileDTO);

    UserProfileResponseDTO getById(Long id);

    Page<UserProfileResponseDTO> get(Pageable pageable);

    void delete(Long id);

    UserProfileResponseDTO updateUserRole(Long id, UserProfileUpdateRoleRequestDTO userProfileDTO);

    UserProfileResponseDTO update(Long id, UserProfileRequestUpdateDTO userProfileDTO);

    UserProfileResponseDTO getByEmail(String email);

    UserProfileResponseDTO enableProfile(String token);
}

package rs.ac.bg.fon.silab.mock_exam.domain.user.profile.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import rs.ac.bg.fon.silab.mock_exam.domain.user.profile.dto.UserProfileRequestDTO;
import rs.ac.bg.fon.silab.mock_exam.domain.user.profile.dto.UserProfileResponseDTO;
import rs.ac.bg.fon.silab.mock_exam.domain.user.profile.entity.UserProfile;
import rs.ac.bg.fon.silab.mock_exam.domain.user.profile.mapper.UserProfileMapper;
import rs.ac.bg.fon.silab.mock_exam.domain.user.profile.repository.UserProfileRepository;
import rs.ac.bg.fon.silab.mock_exam.domain.user.role.entity.UserRole;
import rs.ac.bg.fon.silab.mock_exam.domain.user.role.repository.UserRoleRepository;
import rs.ac.bg.fon.silab.mock_exam.infrastructure.exception.EntityNotFoundException;

@Service
public class UserProfileServiceImpl implements UserProfileService{

    private final UserProfileRepository userProfileRepository;
    private final UserRoleRepository userRoleRepository;
    private final UserProfileMapper mapper;

    public UserProfileServiceImpl(UserProfileRepository userProfileRepository, UserRoleRepository userRoleRepository, UserProfileMapper mapper) {
        this.userProfileRepository = userProfileRepository;
        this.userRoleRepository = userRoleRepository;
        this.mapper = mapper;
    }

    @Override
    public UserProfileResponseDTO save(UserProfileRequestDTO userProfileDTO) {
        UserProfile userProfile = mapper.map(userProfileDTO);

        updateUserRole(userProfile, userProfileDTO);

        userProfileRepository.save(userProfile);

        return mapper.map(userProfile);
    }

    @Override
    public UserProfileResponseDTO getById(Long id) {
        var userProfile = userProfileRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(UserProfile.class.getSimpleName(), "id", id));

        return mapper.map(userProfile);
    }

    @Override
    public Page<UserProfileResponseDTO> get(Pageable pageable) {
        return userProfileRepository.findAll(pageable).map(mapper::map);
    }

    @Override
    public void delete(Long id) {
        if(!userProfileRepository.existsById(id)){
            throw new EntityNotFoundException(UserProfile.class.getSimpleName(),"id",id);
        }

        userProfileRepository.deleteById(id);
    }

    @Override
    public UserProfileResponseDTO update(Long id, UserProfileRequestDTO userProfileDTO) {
        var userProfile = userProfileRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(UserProfile.class.getSimpleName(), "id", id));


        updateUserRole(userProfile, userProfileDTO);

        mapper.update(userProfile, userProfileDTO);

        userProfileRepository.save(userProfile);

        return mapper.map(userProfile);
    }

    private void updateUserRole(UserProfile userProfile, UserProfileRequestDTO userProfileDTO){
        UserRole userRole = userRoleRepository.findByName(userProfileDTO.userRole().name());

        userProfile.setUserRole(userRole);

    }


}

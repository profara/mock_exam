package rs.ac.bg.fon.silab.mock_exam.domain.userprofile.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import rs.ac.bg.fon.silab.mock_exam.domain.userprofile.dto.UserProfileRequestDTO;
import rs.ac.bg.fon.silab.mock_exam.domain.userprofile.dto.UserProfileRequestUpdateDTO;
import rs.ac.bg.fon.silab.mock_exam.domain.userprofile.dto.UserProfileResponseDTO;
import rs.ac.bg.fon.silab.mock_exam.domain.userprofile.dto.UserProfileUpdateRoleRequestDTO;
import rs.ac.bg.fon.silab.mock_exam.domain.userprofile.repository.UserProfileRepository;
import rs.ac.bg.fon.silab.mock_exam.domain.userprofile.entity.UserProfile;
import rs.ac.bg.fon.silab.mock_exam.domain.userprofile.mapper.UserProfileMapper;
import rs.ac.bg.fon.silab.mock_exam.domain.userrole.entity.UserRole;
import rs.ac.bg.fon.silab.mock_exam.domain.userrole.repository.UserRoleRepository;
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

        UserRole userRole = userRoleRepository.findByName(userProfileDTO.userRole().name());

        userProfile.setUserRole(userRole);

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
    public UserProfileResponseDTO updateUserRole(Long id, UserProfileUpdateRoleRequestDTO userProfileDTO) {
        var userProfile = userProfileRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(UserProfile.class.getSimpleName(), "id", id));


        UserRole userRole = userRoleRepository.findByName(userProfileDTO.userRole().name());

        userProfile.setUserRole(userRole);

        mapper.updateUserRole(userProfile, userProfileDTO);

        userProfileRepository.save(userProfile);

        return mapper.map(userProfile);
    }

    @Override
    public UserProfileResponseDTO update(Long id, UserProfileRequestUpdateDTO userProfileDTO) {
        var userProfile = userProfileRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(UserProfile.class.getSimpleName(),"id",id));

        mapper.update(userProfile, userProfileDTO);

        userProfileRepository.save(userProfile);

        return mapper.map(userProfile);
    }



}

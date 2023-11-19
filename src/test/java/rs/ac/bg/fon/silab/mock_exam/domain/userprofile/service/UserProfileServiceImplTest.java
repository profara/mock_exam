package rs.ac.bg.fon.silab.mock_exam.domain.userprofile.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;

import rs.ac.bg.fon.silab.mock_exam.domain.userprofile.dto.*;
import rs.ac.bg.fon.silab.mock_exam.domain.userprofile.entity.UserProfile;
import rs.ac.bg.fon.silab.mock_exam.domain.userprofile.exception.DuplicateUserException;
import rs.ac.bg.fon.silab.mock_exam.domain.userprofile.mapper.UserProfileMapper;
import rs.ac.bg.fon.silab.mock_exam.domain.userprofile.repository.UserProfileRepository;
import rs.ac.bg.fon.silab.mock_exam.domain.userrole.dto.UserRoleRequestDTO;
import rs.ac.bg.fon.silab.mock_exam.domain.userrole.dto.UserRoleResponseDTO;
import rs.ac.bg.fon.silab.mock_exam.domain.userrole.entity.UserRole;
import rs.ac.bg.fon.silab.mock_exam.domain.userrole.repository.UserRoleRepository;
import rs.ac.bg.fon.silab.mock_exam.infrastructure.email.EmailSender;
import rs.ac.bg.fon.silab.mock_exam.infrastructure.exception.EntityNotFoundException;
import rs.ac.bg.fon.silab.mock_exam.infrastructure.jwt.JWTUtil;

import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class UserProfileServiceImplTest {

    @Mock
    private UserProfileRepository userProfileRepository;

    @Mock
    private UserRoleRepository userRoleRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private UserProfileMapper mapper;

    @Mock
    private JWTUtil jwtUtil;

    @Mock
    private EmailSender emailSender;

    @InjectMocks
    private UserProfileServiceImpl userProfileService;

    private UserProfile userProfile;
    private UserRole userRole;
    private UserProfileRequestUpdateDTO userProfileRequestUpdateDTO;
    private UserProfileResponseDTO userProfileResponseDTO;
    private Pageable pageable;
    private Page<UserProfile> userProfilePage;
    private String email;

    @BeforeEach
    void setUp() {
        userRole = new UserRole();
        userProfile = new UserProfile("test@example.com", "password", true, userRole);
        userProfileRequestUpdateDTO = new UserProfileRequestUpdateDTO("test@example.com", "newPassword");
        userProfileResponseDTO = new UserProfileResponseDTO(1L, "test@example.com", true, new UserRoleResponseDTO(1L, "ROLE_USER"));
        pageable = mock(Pageable.class);
        userProfilePage = new PageImpl<>(List.of(userProfile));
        email = "test@example.com";
    }

    @Test
    void testFindByEmailWhenUserProfileExists() {
        when(userProfileRepository.findByEmail(email)).thenReturn(Optional.of(userProfile));

        UserProfile result = userProfileService.findByEmail(email);

        verify(userProfileRepository).findByEmail(email);
        assertEquals(userProfile, result);
    }

    @Test
    void testFindByEmailWhenUserProfileDoesNotExist() {
        String email = "nonexistent@example.com";
        when(userProfileRepository.findByEmail(email)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> {
            userProfileService.findByEmail(email);
        });

        verify(userProfileRepository).findByEmail(email);
    }

    @Test
    void testSaveWhenUserIsNotRegistered() {
        when(userProfileRepository.findByEmail(userProfileRequestUpdateDTO.email())).thenReturn(Optional.empty());
        when(userRoleRepository.findByName(anyString())).thenReturn(userRole);
        when(passwordEncoder.encode(userProfile.getPassword())).thenReturn("encodedPassword");
        when(mapper.map(any(UserProfileRequestUpdateDTO.class))).thenReturn(userProfile);
        when(userProfileRepository.save(any(UserProfile.class))).thenReturn(userProfile);
        when(jwtUtil.issueShortToken(userProfileRequestUpdateDTO.email(), userRole.getName())).thenReturn("token");
        when(mapper.map(userProfile, "token")).thenReturn(new RegistrationResponseDTO(userProfileResponseDTO, "token"));

        RegistrationResponseDTO result = userProfileService.save(userProfileRequestUpdateDTO);

        assertNotNull(result);
        assertEquals("token", result.jwtToken());
    }

    @Test
    void testSaveWhenUserIsAlreadyRegistered() {
        String email = "existing@example.com";
        UserProfile existingUserProfile = new UserProfile();
        existingUserProfile.setEmail(email);
        existingUserProfile.setEnabled(true);

        UserProfileRequestUpdateDTO userProfileDTO = new UserProfileRequestUpdateDTO(email, "password");
        when(userProfileRepository.findByEmail(email)).thenReturn(Optional.of(existingUserProfile));

        assertThrows(DuplicateUserException.class, () -> {
            userProfileService.save(userProfileDTO);
        });

        verify(userProfileRepository).findByEmail(email);
    }

    @Test
    void testGetByIdWhenUserExists() {
        Long id = 1L;
        when(userProfileRepository.findById(id)).thenReturn(Optional.of(userProfile));
        when(mapper.map(userProfile)).thenReturn(userProfileResponseDTO);

        UserProfileResponseDTO result = userProfileService.getById(id);

        verify(userProfileRepository).findById(id);
        assertEquals(userProfileResponseDTO, result);
    }

    @Test
    void testGetByIdWhenUserDoesNotExist() {
        Long nonExistentUserId = 999L;
        when(userProfileRepository.findById(nonExistentUserId)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> {
            userProfileService.getById(nonExistentUserId);
        });

        verify(userProfileRepository).findById(nonExistentUserId);
    }

    @Test
    void testGet() {
        when(userProfileRepository.findAll(pageable)).thenReturn(userProfilePage);
        when(mapper.map(any(UserProfile.class))).thenReturn(userProfileResponseDTO);

        Page<UserProfileResponseDTO> result = userProfileService.get(pageable);

        verify(userProfileRepository).findAll(pageable);
        assertEquals(1, result.getContent().size());
        assertEquals(userProfileResponseDTO, result.getContent().get(0));
    }

    @Test
    void testDeleteExistingUser() {
        Long id = 1L;
        when(userProfileRepository.existsById(id)).thenReturn(true);

        userProfileService.delete(id);

        verify(userProfileRepository).deleteById(id);
    }

    @Test
    void testDeleteNonExistingUser() {
        Long id = 1L;
        when(userProfileRepository.existsById(id)).thenReturn(false);

        assertThrows(EntityNotFoundException.class, () -> userProfileService.delete(id));
        verify(userProfileRepository, never()).deleteById(id);
    }

    @Test
    void testUpdateUserRoleWhenUserExists() {
        Long id = 1L;
        UserProfileUpdateRoleRequestDTO dto = new UserProfileUpdateRoleRequestDTO(new UserRoleRequestDTO("ROLE_USER"));
        when(userProfileRepository.findById(id)).thenReturn(Optional.of(userProfile));
        when(userProfileRepository.save(userProfile)).thenReturn(userProfile);
        when(mapper.map(userProfile)).thenReturn(userProfileResponseDTO);

        UserProfileResponseDTO result = userProfileService.updateUserRole(id, dto);

        verify(userProfileRepository).findById(id);
        verify(userProfileRepository).save(userProfile);
        assertEquals(userProfileResponseDTO, result);
    }

    @Test
    void testUpdateUserRoleWhenUserDoesNotExist() {
        Long nonExistentUserId = 999L;
        UserProfileUpdateRoleRequestDTO updateRoleRequestDTO = new UserProfileUpdateRoleRequestDTO(new UserRoleRequestDTO("ROLE_USER"));
        when(userProfileRepository.findById(nonExistentUserId)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> {
            userProfileService.updateUserRole(nonExistentUserId, updateRoleRequestDTO);
        });

        verify(userProfileRepository).findById(nonExistentUserId);
    }

    @Test
    void testUpdateWhenUserExists() {
        Long id = 1L;
        when(userProfileRepository.findById(id)).thenReturn(Optional.of(userProfile));
        when(userProfileRepository.save(userProfile)).thenReturn(userProfile);
        when(mapper.map(userProfile)).thenReturn(userProfileResponseDTO);

        UserProfileResponseDTO result = userProfileService.update(id, userProfileRequestUpdateDTO);

        verify(userProfileRepository).findById(id);
        verify(userProfileRepository).save(userProfile);
        assertEquals(userProfileResponseDTO, result);
    }

    @Test
    void testUpdateWhenUserDoesNotExist() {
        Long nonExistentUserId = 999L;
        when(userProfileRepository.findById(nonExistentUserId)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> {
            userProfileService.update(nonExistentUserId, userProfileRequestUpdateDTO);
        });

        verify(userProfileRepository).findById(nonExistentUserId);
    }

    @Test
    void testGetByEmailWhenUserExists() {
        when(userProfileRepository.findByEmail(email)).thenReturn(Optional.of(userProfile));
        when(mapper.map(userProfile)).thenReturn(userProfileResponseDTO);

        UserProfileResponseDTO result = userProfileService.getByEmail(email);

        verify(userProfileRepository).findByEmail(email);
        assertEquals(userProfileResponseDTO, result);
    }

    @Test
    void testGetByEmailWhenUserDoesNotExist() {
        String nonExistentEmail = "nonexistent@example.com";
        when(userProfileRepository.findByEmail(nonExistentEmail)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> {
            userProfileService.getByEmail(nonExistentEmail);
        });

        verify(userProfileRepository).findByEmail(nonExistentEmail);
    }

    @Test
    void testEnableProfileWhenUserExists() {
        String token = "jwtToken";
        when(jwtUtil.getSubject(token)).thenReturn(email);
        when(userProfileRepository.findByEmail(email)).thenReturn(Optional.of(userProfile));
        when(userProfileRepository.save(userProfile)).thenReturn(userProfile);
        when(mapper.map(userProfile)).thenReturn(userProfileResponseDTO);

        UserProfileResponseDTO result = userProfileService.enableProfile(token);

        assertTrue(userProfile.isEnabled());
        verify(userProfileRepository).findByEmail(email);
        verify(userProfileRepository).save(userProfile);
        assertEquals(userProfileResponseDTO, result);
    }

    @Test
    void testEnableProfileWhenUserDoesNotExist() {
        String token = "testToken";
        String nonExistentEmail = "nonexistent@example.com";
        when(jwtUtil.getSubject(token)).thenReturn(nonExistentEmail);
        when(userProfileRepository.findByEmail(nonExistentEmail)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> {
            userProfileService.enableProfile(token);
        });

        verify(jwtUtil).getSubject(token);
        verify(userProfileRepository).findByEmail(nonExistentEmail);
    }

}

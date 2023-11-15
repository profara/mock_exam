package rs.ac.bg.fon.silab.mock_exam.domain.userrole.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import rs.ac.bg.fon.silab.mock_exam.domain.userrole.dto.UserRoleRequestDTO;
import rs.ac.bg.fon.silab.mock_exam.domain.userrole.dto.UserRoleResponseDTO;
import rs.ac.bg.fon.silab.mock_exam.domain.userrole.entity.UserRole;
import rs.ac.bg.fon.silab.mock_exam.domain.userrole.mapper.UserRoleMapper;
import rs.ac.bg.fon.silab.mock_exam.domain.userrole.repository.UserRoleRepository;
import rs.ac.bg.fon.silab.mock_exam.infrastructure.exception.EntityNotFoundException;

import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class UserRoleServiceImplTest {

    @Mock
    private UserRoleRepository userRoleRepository;

    @Mock
    private UserRoleMapper mapper;

    @InjectMocks
    private UserRoleServiceImpl userRoleService;

    @Test
    void testFindByName() {
        String name = "ROLE_USER";
        UserRole mockUserRole = new UserRole(name);
        when(userRoleRepository.findByName(name)).thenReturn(mockUserRole);

        UserRole result = userRoleService.findByName(name);
        assertEquals(mockUserRole, result);
    }

    @Test
    void testSave() {
        UserRoleRequestDTO dto = new UserRoleRequestDTO("ROLE_USER");
        UserRole mappedUserRole = new UserRole("ROLE_USER");
        UserRole savedUserRole = new UserRole("ROLE_USER");
        UserRoleResponseDTO responseDTO = new UserRoleResponseDTO(1L, "ROLE_USER");

        when(mapper.map(dto)).thenReturn(mappedUserRole);
        when(userRoleRepository.save(mappedUserRole)).thenReturn(savedUserRole);
        when(mapper.map(savedUserRole)).thenReturn(responseDTO);

        UserRoleResponseDTO resultDTO = userRoleService.save(dto);

        verify(mapper).map(dto);
        verify(userRoleRepository).save(mappedUserRole);
        verify(mapper).map(savedUserRole);

        assertEquals(responseDTO, resultDTO);
    }

    @Test
    void testGetById() {
        Long id = 1L;
        UserRole mockUserRole = new UserRole("ROLE_USER");
        UserRoleResponseDTO expectedResponseDTO = new UserRoleResponseDTO(1L, "ROLE_USER");

        when(userRoleRepository.findById(id)).thenReturn(Optional.of(mockUserRole));
        when(mapper.map(mockUserRole)).thenReturn(expectedResponseDTO);

        UserRoleResponseDTO actualResponseDTO = userRoleService.getById(id);

        verify(userRoleRepository).findById(id);
        verify(mapper).map(mockUserRole);

        assertEquals(expectedResponseDTO, actualResponseDTO);
    }

    @Test
    void testGet() {
        Pageable pageable = mock(Pageable.class);
        UserRole mockUserRole = new UserRole("ROLE_USER");
        UserRoleResponseDTO mockDTO = new UserRoleResponseDTO(1L, "ROLE_USER");
        Page<UserRole> page = new PageImpl<>(List.of(new UserRole()));

        when(userRoleRepository.findAll(pageable)).thenReturn(page);
        when(mapper.map(mockUserRole)).thenReturn(mockDTO);

        Page<UserRoleResponseDTO> resultPage = userRoleService.get(pageable);

        verify(mapper, times(page.getContent().size())).map(any(UserRole.class));

        assertNotNull(resultPage);
        assertEquals(1, resultPage.getContent().size());
        assertEquals(mockDTO, resultPage.getContent().get(0));
    }

    @Test
    void testDeleteWhenUserRoleExists() {
        Long id = 1L;
        when(userRoleRepository.existsById(id)).thenReturn(true);

        userRoleService.delete(id);

        verify(userRoleRepository).deleteById(id);
    }

    @Test
    void testDeleteWhenUserRoleDoesNotExist() {
        Long id = 1L;
        when(userRoleRepository.existsById(id)).thenReturn(false);

        assertThrows(EntityNotFoundException.class, () -> userRoleService.delete(id));

        verify(userRoleRepository, never()).deleteById(id);
    }

    @Test
    void testUpdate() {
        Long id = 1L;
        UserRoleRequestDTO dto = new UserRoleRequestDTO("ROLE_ADMIN");
        UserRole existingUserRole = new UserRole("ROLE_USER");
        UserRoleResponseDTO responseDTO = new UserRoleResponseDTO(1L, "ROLE_ADMIN");

        when(userRoleRepository.findById(id)).thenReturn(Optional.of(existingUserRole));
        when(userRoleRepository.save(existingUserRole)).thenReturn(existingUserRole);
        when(mapper.map(existingUserRole)).thenReturn(responseDTO);

        UserRoleResponseDTO resultDTO = userRoleService.update(id, dto);

        verify(mapper).update(any(), any());
        verify(userRoleRepository).save(existingUserRole);
        verify(mapper).map(existingUserRole);

        assertEquals(responseDTO, resultDTO);
    }
}

package rs.ac.bg.fon.silab.mock_exam.domain.user.role.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import rs.ac.bg.fon.silab.mock_exam.domain.user.role.dto.UserRoleRequestDTO;
import rs.ac.bg.fon.silab.mock_exam.domain.user.role.dto.UserRoleResponseDTO;

public interface UserRoleService {

    UserRoleResponseDTO save(UserRoleRequestDTO userRoleDTO);

    UserRoleResponseDTO getById(Long id);

    Page<UserRoleResponseDTO> get(Pageable pageable);

    void delete(Long id);

    UserRoleResponseDTO update(Long id, UserRoleRequestDTO userRoleDTO);
}

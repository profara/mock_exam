package rs.ac.bg.fon.silab.mock_exam.domain.userrole.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import rs.ac.bg.fon.silab.mock_exam.domain.userrole.dto.UserRoleRequestDTO;
import rs.ac.bg.fon.silab.mock_exam.domain.userrole.dto.UserRoleResponseDTO;
import rs.ac.bg.fon.silab.mock_exam.domain.userrole.entity.UserRole;

public interface UserRoleService {

    UserRole findByName(String name);

    UserRoleResponseDTO save(UserRoleRequestDTO userRoleDTO);

    UserRoleResponseDTO getById(Long id);

    Page<UserRoleResponseDTO> get(Pageable pageable);

    void delete(Long id);

    UserRoleResponseDTO update(Long id, UserRoleRequestDTO userRoleDTO);
}

package rs.ac.bg.fon.silab.mock_exam.domain.userrole.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import rs.ac.bg.fon.silab.mock_exam.domain.userrole.dto.UserRoleRequestDTO;
import rs.ac.bg.fon.silab.mock_exam.domain.userrole.dto.UserRoleResponseDTO;
import rs.ac.bg.fon.silab.mock_exam.domain.userrole.entity.UserRole;
import rs.ac.bg.fon.silab.mock_exam.domain.userrole.mapper.UserRoleMapper;
import rs.ac.bg.fon.silab.mock_exam.domain.userrole.repository.UserRoleRepository;
import rs.ac.bg.fon.silab.mock_exam.infrastructure.exception.EntityNotFoundException;

@Service
public class UserRoleServiceImpl implements UserRoleService{

    private final UserRoleRepository userRoleRepository;
    private final UserRoleMapper mapper;

    public UserRoleServiceImpl(UserRoleRepository userRoleRepository, UserRoleMapper mapper) {
        this.userRoleRepository = userRoleRepository;
        this.mapper = mapper;
    }

    @Override
    public UserRole findByName(String name) {
        return userRoleRepository.findByName(name);
    }

    @Override
    public UserRoleResponseDTO save(UserRoleRequestDTO userRoleDTO) {
        UserRole userRole = mapper.map(userRoleDTO);

        userRoleRepository.save(userRole);

        return mapper.map(userRole);

    }

    @Override
    public UserRoleResponseDTO getById(Long id) {
        var userRole = userRoleRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(UserRole.class.getSimpleName(),"id",id));

        return mapper.map(userRole);
    }

    @Override
    public Page<UserRoleResponseDTO> get(Pageable pageable) {
        return userRoleRepository.findAll(pageable).map(mapper::map);
    }

    @Override
    public void delete(Long id) {
        if(!userRoleRepository.existsById(id)){
            throw new EntityNotFoundException(UserRole.class.getSimpleName(),"id",id);
        }
        userRoleRepository.deleteById(id);
    }

    @Override
    public UserRoleResponseDTO update(Long id, UserRoleRequestDTO userRoleDTO) {
        var userRole = userRoleRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(UserRole.class.getSimpleName(),"id", id));

        mapper.update(userRole,userRoleDTO);;

        userRoleRepository.save(userRole);

        return mapper.map(userRole);
    }

}

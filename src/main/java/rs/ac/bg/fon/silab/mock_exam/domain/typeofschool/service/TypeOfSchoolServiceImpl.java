package rs.ac.bg.fon.silab.mock_exam.domain.typeofschool.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import rs.ac.bg.fon.silab.mock_exam.domain.typeofschool.dto.TypeOfSchoolRequestDTO;
import rs.ac.bg.fon.silab.mock_exam.domain.typeofschool.dto.TypeOfSchoolResponseDTO;
import rs.ac.bg.fon.silab.mock_exam.domain.typeofschool.entity.TypeOfSchool;
import rs.ac.bg.fon.silab.mock_exam.domain.typeofschool.mapper.TypeOfSchoolMapper;
import rs.ac.bg.fon.silab.mock_exam.domain.typeofschool.repository.TypeOfSchoolRepository;
import rs.ac.bg.fon.silab.mock_exam.infrastructure.exception.EntityNotFoundException;

/**
 * Implementation of the TypeOfSchoolService interface.
 * Manages operations related to the types of schools, including retrieving, saving, updating, and deleting.
 */
@Service
public class TypeOfSchoolServiceImpl implements TypeOfSchoolService{

    private final TypeOfSchoolRepository typeOfSchoolRepository;
    private final TypeOfSchoolMapper mapper;

    /**
     * Constructs a new TypeOfSchoolServiceImpl with necessary dependencies.
     *
     * @param typeOfSchoolRepository the repository for accessing type of school data
     * @param mapper the mapper for converting between entity and DTO
     */
    public TypeOfSchoolServiceImpl(TypeOfSchoolRepository typeOfSchoolRepository, TypeOfSchoolMapper mapper) {
        this.typeOfSchoolRepository = typeOfSchoolRepository;
        this.mapper = mapper;
    }

    @Override
    @Transactional(readOnly = true)
    public TypeOfSchool findByName(String name) {
        return typeOfSchoolRepository.findByName(name);
    }

    @Override
    @Transactional
    public TypeOfSchoolResponseDTO save(TypeOfSchoolRequestDTO typeOfSchoolDTO) {
        TypeOfSchool typeOfSchool = mapper.map(typeOfSchoolDTO);

        typeOfSchoolRepository.save(typeOfSchool);

        return mapper.map(typeOfSchool);
    }

    @Override
    @Transactional(readOnly = true)
    public TypeOfSchoolResponseDTO getById(Long id) {
        var typeOfSchool = typeOfSchoolRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(TypeOfSchool.class.getSimpleName(), "id", id));

        return mapper.map(typeOfSchool);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<TypeOfSchoolResponseDTO> get(Pageable pageable) {
        return typeOfSchoolRepository.findAll(pageable).map(mapper::map);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        if(!typeOfSchoolRepository.existsById(id)){
            throw new EntityNotFoundException(TypeOfSchool.class.getSimpleName(),"id", id);
        }

        typeOfSchoolRepository.deleteById(id);
    }

    @Override
    @Transactional
    public TypeOfSchoolResponseDTO update(Long id, TypeOfSchoolRequestDTO typeOfSchoolDTO) {
        var typeOfSchool = typeOfSchoolRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(TypeOfSchool.class.getSimpleName(),"id",id));

        mapper.update(typeOfSchool, typeOfSchoolDTO);

        typeOfSchoolRepository.save(typeOfSchool);

        return mapper.map(typeOfSchool);
    }
}

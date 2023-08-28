package rs.ac.bg.fon.silab.mock_exam.domain.typeofschool.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import rs.ac.bg.fon.silab.mock_exam.domain.typeofschool.dto.TypeOfSchoolRequestDTO;
import rs.ac.bg.fon.silab.mock_exam.domain.typeofschool.dto.TypeOfSchoolResponseDTO;
import rs.ac.bg.fon.silab.mock_exam.domain.typeofschool.entity.TypeOfSchool;
import rs.ac.bg.fon.silab.mock_exam.domain.typeofschool.mapper.TypeOfSchoolMapper;
import rs.ac.bg.fon.silab.mock_exam.domain.typeofschool.repository.TypeOfSchoolRepository;
import rs.ac.bg.fon.silab.mock_exam.infrastructure.exception.EntityNotFoundException;

@Service
public class TypeOfSchoolServiceImpl implements TypeOfSchoolService{

    private final TypeOfSchoolRepository typeOfSchoolRepository;
    private final TypeOfSchoolMapper mapper;

    public TypeOfSchoolServiceImpl(TypeOfSchoolRepository typeOfSchoolRepository, TypeOfSchoolMapper mapper) {
        this.typeOfSchoolRepository = typeOfSchoolRepository;
        this.mapper = mapper;
    }

    @Override
    public TypeOfSchool findByName(String name) {
        return typeOfSchoolRepository.findByName(name);
    }

    @Override
    public TypeOfSchoolResponseDTO save(TypeOfSchoolRequestDTO typeOfSchoolDTO) {
        TypeOfSchool typeOfSchool = mapper.map(typeOfSchoolDTO);

        typeOfSchoolRepository.save(typeOfSchool);

        return mapper.map(typeOfSchool);
    }

    @Override
    public TypeOfSchoolResponseDTO getById(Long id) {
        var typeOfSchool = typeOfSchoolRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(TypeOfSchool.class.getSimpleName(), "id", id));

        return mapper.map(typeOfSchool);
    }

    @Override
    public Page<TypeOfSchoolResponseDTO> get(Pageable pageable) {
        return typeOfSchoolRepository.findAll(pageable).map(mapper::map);
    }

    @Override
    public void delete(Long id) {
        if(!typeOfSchoolRepository.existsById(id)){
            throw new EntityNotFoundException(TypeOfSchool.class.getSimpleName(),"id", id);
        }

        typeOfSchoolRepository.deleteById(id);
    }

    @Override
    public TypeOfSchoolResponseDTO update(Long id, TypeOfSchoolRequestDTO typeOfSchoolDTO) {
        var typeOfSchool = typeOfSchoolRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(TypeOfSchool.class.getSimpleName(),"id",id));

        mapper.update(typeOfSchool, typeOfSchoolDTO);

        typeOfSchoolRepository.save(typeOfSchool);

        return mapper.map(typeOfSchool);
    }
}

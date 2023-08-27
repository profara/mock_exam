package rs.ac.bg.fon.silab.mock_exam.domain.school.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import rs.ac.bg.fon.silab.mock_exam.domain.city.entity.City;
import rs.ac.bg.fon.silab.mock_exam.domain.school.dto.SchoolRequestDTO;
import rs.ac.bg.fon.silab.mock_exam.domain.school.dto.SchoolResponseDTO;
import rs.ac.bg.fon.silab.mock_exam.domain.school.dto.SchoolUpdateRequestDTO;
import rs.ac.bg.fon.silab.mock_exam.domain.school.entity.School;
import rs.ac.bg.fon.silab.mock_exam.domain.school.mapper.SchoolMapper;
import rs.ac.bg.fon.silab.mock_exam.domain.school.repository.SchoolRepository;
import rs.ac.bg.fon.silab.mock_exam.domain.typeofschool.entity.TypeOfSchool;
import rs.ac.bg.fon.silab.mock_exam.domain.typeofschool.repository.TypeOfSchoolRepository;
import rs.ac.bg.fon.silab.mock_exam.infrastructure.exception.EntityNotFoundException;

@Service
public class SchoolServiceImpl implements SchoolService{

    private final SchoolRepository schoolRepository;
    private final TypeOfSchoolRepository typeOfSchoolRepository;
    private final SchoolMapper mapper;

    public SchoolServiceImpl(SchoolRepository schoolRepository, TypeOfSchoolRepository typeOfSchoolRepository, SchoolMapper mapper) {
        this.schoolRepository = schoolRepository;
        this.typeOfSchoolRepository = typeOfSchoolRepository;
        this.mapper = mapper;
    }

    @Override
    public SchoolResponseDTO save(SchoolRequestDTO schoolDTO) {
        School school = mapper.map(schoolDTO);

        TypeOfSchool typeOfSchool = typeOfSchoolRepository.findByName(schoolDTO.typeOfSchool().name());
        school.setTypeOfSchool(typeOfSchool);

        schoolRepository.save(school);

        return mapper.map(school);
    }

    @Override
    public SchoolResponseDTO getById(Long code) {
        var school = schoolRepository.findById(code)
                .orElseThrow(() -> new EntityNotFoundException(School.class.getSimpleName(),"code",code));

        return mapper.map(school);
    }

    @Override
    public Page<SchoolResponseDTO> get(Pageable pageable) {
        return schoolRepository.findAll(pageable).map(mapper::map);
    }

    @Override
    public void delete(Long code) {
        if(!schoolRepository.existsById(code)){
            throw new EntityNotFoundException(School.class.getSimpleName(),"code",code);
        }

        schoolRepository.deleteById(code);
    }

    @Override
    public SchoolResponseDTO update(Long code, SchoolUpdateRequestDTO schoolDTO) {
        var school = schoolRepository.findById(code)
                .orElseThrow(() -> new EntityNotFoundException(School.class.getSimpleName(),"code", code));

        mapper.update(school,schoolDTO);

        schoolRepository.save(school);

        return mapper.map(school);
    }


}

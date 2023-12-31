package rs.ac.bg.fon.silab.mock_exam.domain.school.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import rs.ac.bg.fon.silab.mock_exam.domain.school.dto.SchoolRequestDTO;
import rs.ac.bg.fon.silab.mock_exam.domain.school.dto.SchoolResponseDTO;
import rs.ac.bg.fon.silab.mock_exam.domain.school.dto.SchoolUpdateRequestDTO;
import rs.ac.bg.fon.silab.mock_exam.domain.school.entity.School;
import rs.ac.bg.fon.silab.mock_exam.domain.school.mapper.SchoolMapper;
import rs.ac.bg.fon.silab.mock_exam.domain.school.repository.SchoolRepository;
import rs.ac.bg.fon.silab.mock_exam.infrastructure.exception.EntityNotFoundException;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Implementation of the SchoolService interface.
 * Handles business logic related to school management, such as finding, saving, updating, and deleting schools.
 */
@Service
public class SchoolServiceImpl implements SchoolService{

    private final SchoolRepository schoolRepository;
    private final SchoolMapper mapper;

    /**
     * Constructs a new SchoolServiceImpl with necessary dependencies.
     *
     * @param schoolRepository the repository for school data access
     * @param mapper the mapper for converting between entity and DTO
     */
    public SchoolServiceImpl(SchoolRepository schoolRepository, SchoolMapper mapper) {
        this.schoolRepository = schoolRepository;
        this.mapper = mapper;
    }

    @Override
    @Transactional(readOnly = true)
    public School findById(Long code) {
        return schoolRepository.findById(code)
                .orElseThrow(() -> new EntityNotFoundException(School.class.getSimpleName(),"code",code));
    }

    @Override
    @Transactional
    public SchoolResponseDTO save(SchoolRequestDTO schoolDTO) {
        School school = mapper.map(schoolDTO);

        schoolRepository.save(school);

        return mapper.map(school);
    }

    @Override
    @Transactional(readOnly = true)
    public SchoolResponseDTO getById(Long code) {
        var school = schoolRepository.findById(code)
                .orElseThrow(() -> new EntityNotFoundException(School.class.getSimpleName(),"code",code));

        return mapper.map(school);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<SchoolResponseDTO> get(Pageable pageable) {
        return schoolRepository.findAll(pageable).map(mapper::map);
    }

    @Override
    @Transactional
    public void delete(Long code) {
        if(!schoolRepository.existsById(code)){
            throw new EntityNotFoundException(School.class.getSimpleName(),"code",code);
        }

        schoolRepository.deleteById(code);
    }

    @Override
    @Transactional
    public SchoolResponseDTO update(Long code, SchoolUpdateRequestDTO schoolDTO) {
        var school = schoolRepository.findById(code)
                .orElseThrow(() -> new EntityNotFoundException(School.class.getSimpleName(),"code", code));

        mapper.update(school,schoolDTO);

        schoolRepository.save(school);

        return mapper.map(school);
    }

    @Override
    public List<SchoolResponseDTO> getAll() {
        List<School> schools = schoolRepository.findAll();

        return schools.stream()
                .map(mapper::map)
                .collect(Collectors.toList());
    }


}

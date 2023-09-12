package rs.ac.bg.fon.silab.mock_exam.domain.exam.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import rs.ac.bg.fon.silab.mock_exam.domain.exam.dto.ExamRequestDTO;
import rs.ac.bg.fon.silab.mock_exam.domain.exam.dto.ExamResponseDTO;
import rs.ac.bg.fon.silab.mock_exam.domain.exam.entity.Exam;
import rs.ac.bg.fon.silab.mock_exam.domain.exam.mapper.ExamMapper;
import rs.ac.bg.fon.silab.mock_exam.domain.exam.repository.ExamRepository;
import rs.ac.bg.fon.silab.mock_exam.infrastructure.exception.EntityNotFoundException;

@Service
public class ExamServiceImpl implements ExamService{

    private final ExamRepository examRepository;
    private final ExamMapper mapper;

    public ExamServiceImpl(ExamRepository examRepository, ExamMapper mapper) {
        this.examRepository = examRepository;
        this.mapper = mapper;
    }

    @Override
    @Transactional(readOnly = true)
    public Exam findByName(String name) {
        return examRepository.findByName(name);
    }

    @Override
    @Transactional(readOnly = true)
    public Exam findById(Long id) {
        return examRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(Exam.class.getSimpleName(), "id", id));
    }

    @Override
    @Transactional
    public ExamResponseDTO save(ExamRequestDTO examDTO) {
        Exam exam = mapper.map(examDTO);

        examRepository.save(exam);

        return mapper.map(exam);
    }

    @Override
    @Transactional(readOnly = true)
    public ExamResponseDTO getById(Long id) {
        var exam = examRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(Exam.class.getSimpleName(), "id", id));

        return mapper.map(exam);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ExamResponseDTO> get(Pageable pageable) {
        return examRepository.findAll(pageable).map(mapper::map);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        if(!examRepository.existsById(id)){
            throw new EntityNotFoundException(Exam.class.getSimpleName(), "id", id);
        }

        examRepository.deleteById(id);
    }

    @Override
    @Transactional
    public ExamResponseDTO update(Long id, ExamRequestDTO examDTO) {
        var exam = examRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(Exam.class.getSimpleName(), "id", id));

        mapper.update(exam,examDTO);

        examRepository.save(exam);

        return mapper.map(exam);
    }
}

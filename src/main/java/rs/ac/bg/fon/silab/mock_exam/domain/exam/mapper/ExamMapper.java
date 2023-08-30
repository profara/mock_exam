package rs.ac.bg.fon.silab.mock_exam.domain.exam.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import rs.ac.bg.fon.silab.mock_exam.domain.exam.dto.ExamRequestDTO;
import rs.ac.bg.fon.silab.mock_exam.domain.exam.dto.ExamResponseDTO;
import rs.ac.bg.fon.silab.mock_exam.domain.exam.entity.Exam;

@Mapper(componentModel = "spring")
public interface ExamMapper {
    Exam map(ExamRequestDTO examDTO);

    ExamResponseDTO map(Exam exam);


    void update(@MappingTarget Exam exam, ExamRequestDTO examDTO);
}

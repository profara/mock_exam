package rs.ac.bg.fon.silab.mock_exam.domain.school.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import rs.ac.bg.fon.silab.mock_exam.domain.school.dto.SchoolRequestDTO;
import rs.ac.bg.fon.silab.mock_exam.domain.school.dto.SchoolResponseDTO;
import rs.ac.bg.fon.silab.mock_exam.domain.school.dto.SchoolUpdateRequestDTO;
import rs.ac.bg.fon.silab.mock_exam.domain.school.entity.School;

@Mapper(componentModel = "spring")
public interface SchoolMapper {
    School map(SchoolRequestDTO schoolDTO);

    SchoolResponseDTO map(School school);

    void update(@MappingTarget School school, SchoolUpdateRequestDTO schoolDTO);
}

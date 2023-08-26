package rs.ac.bg.fon.silab.mock_exam.domain.typeofschool.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import rs.ac.bg.fon.silab.mock_exam.domain.typeofschool.dto.TypeOfSchoolRequestDTO;
import rs.ac.bg.fon.silab.mock_exam.domain.typeofschool.dto.TypeOfSchoolResponseDTO;
import rs.ac.bg.fon.silab.mock_exam.domain.typeofschool.entity.TypeOfSchool;

@Mapper(componentModel = "spring")
public interface TypeOfSchoolMapper {

    TypeOfSchool map(TypeOfSchoolRequestDTO typeOfSchoolDTO);

    TypeOfSchoolResponseDTO map(TypeOfSchool typeOfSchool);

    void update(@MappingTarget TypeOfSchool typeOfSchool, TypeOfSchoolRequestDTO typeOfSchoolDTO);
}

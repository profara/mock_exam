package rs.ac.bg.fon.silab.mock_exam.domain.school.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import rs.ac.bg.fon.silab.mock_exam.domain.city.service.CityService;
import rs.ac.bg.fon.silab.mock_exam.domain.school.dto.SchoolRequestDTO;
import rs.ac.bg.fon.silab.mock_exam.domain.school.dto.SchoolResponseDTO;
import rs.ac.bg.fon.silab.mock_exam.domain.school.dto.SchoolUpdateRequestDTO;
import rs.ac.bg.fon.silab.mock_exam.domain.school.entity.School;
import rs.ac.bg.fon.silab.mock_exam.domain.typeofschool.service.TypeOfSchoolService;

@Mapper(componentModel = "spring", uses = {TypeOfSchoolService.class, CityService.class})
public interface SchoolMapper {
    @Mapping(source = "typeOfSchool.name", target = "typeOfSchool")
    @Mapping(source = "city.zipCode", target = "city")
    School map(SchoolRequestDTO schoolDTO);

    SchoolResponseDTO map(School school);

    void update(@MappingTarget School school, SchoolUpdateRequestDTO schoolDTO);
}

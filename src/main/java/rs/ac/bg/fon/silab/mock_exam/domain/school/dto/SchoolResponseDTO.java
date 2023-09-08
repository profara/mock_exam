package rs.ac.bg.fon.silab.mock_exam.domain.school.dto;

import rs.ac.bg.fon.silab.mock_exam.domain.city.dto.CityResponseDTO;
import rs.ac.bg.fon.silab.mock_exam.domain.city.entity.City;
import rs.ac.bg.fon.silab.mock_exam.domain.typeofschool.dto.TypeOfSchoolResponseDTO;
import rs.ac.bg.fon.silab.mock_exam.domain.typeofschool.entity.TypeOfSchool;

public record SchoolResponseDTO(
        Long code,
        String name,
        TypeOfSchoolResponseDTO typeOfSchool,
        CityResponseDTO city
) {
}

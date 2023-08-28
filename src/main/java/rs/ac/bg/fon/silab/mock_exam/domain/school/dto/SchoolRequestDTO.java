package rs.ac.bg.fon.silab.mock_exam.domain.school.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import rs.ac.bg.fon.silab.mock_exam.domain.city.dto.CitySimpleRequestDTO;
import rs.ac.bg.fon.silab.mock_exam.domain.typeofschool.dto.TypeOfSchoolRequestDTO;

public record SchoolRequestDTO(
        @NotNull(message = "School code is mandatory")
        @Max(value = 9999999, message = "School code can't have more than 7 digits")
        @Min(value = 100000, message = "School code can't have less than 6 digits")
        Long code,
        @NotBlank(message = "Name is mandatory")
        String name,
        @NotNull(message = "Type of school is mandatory")
        @Valid
        TypeOfSchoolRequestDTO typeOfSchool,
        @NotNull(message = "City is mandatory")
        @Valid
        CitySimpleRequestDTO city

) {
}

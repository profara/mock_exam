package rs.ac.bg.fon.silab.mock_exam.domain.city.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import rs.ac.bg.fon.silab.mock_exam.domain.city.dto.CityRequestDTO;
import rs.ac.bg.fon.silab.mock_exam.domain.city.dto.CityRequestUpdateDTO;
import rs.ac.bg.fon.silab.mock_exam.domain.city.dto.CityResponseDTO;
import rs.ac.bg.fon.silab.mock_exam.domain.city.entity.City;

@Mapper(componentModel = "spring")
public interface CityMapper {

    City map(CityRequestDTO cityDTO);

    CityResponseDTO map(City city);

    void update(@MappingTarget City city, CityRequestUpdateDTO cityDTO);
}

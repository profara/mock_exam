package rs.ac.bg.fon.silab.mock_exam.domain.pricelist.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import rs.ac.bg.fon.silab.mock_exam.domain.pricelist.dto.PriceListRequestDTO;
import rs.ac.bg.fon.silab.mock_exam.domain.pricelist.dto.PriceListResponseDTO;
import rs.ac.bg.fon.silab.mock_exam.domain.pricelist.entity.PriceList;

@Mapper(componentModel = "spring")
public interface PriceListMapper {
    PriceList map(PriceListRequestDTO priceListDTO);

    PriceListResponseDTO map(PriceList priceList);

    void update(@MappingTarget PriceList priceList, PriceListRequestDTO priceListDTO);
}

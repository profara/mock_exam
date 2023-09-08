package rs.ac.bg.fon.silab.mock_exam.domain.pricelistitem.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import rs.ac.bg.fon.silab.mock_exam.domain.currency.service.CurrencyService;
import rs.ac.bg.fon.silab.mock_exam.domain.exam.service.ExamService;
import rs.ac.bg.fon.silab.mock_exam.domain.pricelist.service.PriceListService;
import rs.ac.bg.fon.silab.mock_exam.domain.pricelistitem.dto.PriceListItemRequestDTO;
import rs.ac.bg.fon.silab.mock_exam.domain.pricelistitem.dto.PriceListItemResponseDTO;
import rs.ac.bg.fon.silab.mock_exam.domain.pricelistitem.entity.PriceListItem;

@Mapper(componentModel = "spring", uses = {PriceListService.class, CurrencyService.class, ExamService.class})
public interface PriceListItemMapper {
    @Mapping(source = "exam.name",target = "exam")
    @Mapping(source = "currency.code", target = "currency")
    @Mapping(source = "priceList.year", target = "priceList")
    PriceListItem map(PriceListItemRequestDTO priceListItemDTO);

    PriceListItemResponseDTO map(PriceListItem priceListItem);

    void update(@MappingTarget PriceListItem priceListItem, PriceListItemRequestDTO priceListItemDTO);

//    @Mapping(source = "priceListItemId.priceList", target = "id.priceList")
//    @Mapping(source = "priceListItemId.id", target = "id.id")
//    PriceListItemResponseDTO map(PriceListItem priceListItem);

}

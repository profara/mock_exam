package rs.ac.bg.fon.silab.mock_exam.domain.pricelistitem.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import rs.ac.bg.fon.silab.mock_exam.domain.currency.service.CurrencyService;
import rs.ac.bg.fon.silab.mock_exam.domain.exam.service.ExamService;
import rs.ac.bg.fon.silab.mock_exam.domain.pricelist.entity.PriceList;
import rs.ac.bg.fon.silab.mock_exam.domain.pricelist.service.PriceListService;
import rs.ac.bg.fon.silab.mock_exam.domain.pricelistitem.dto.PriceListItemIdRequestDTO;
import rs.ac.bg.fon.silab.mock_exam.domain.pricelistitem.dto.PriceListItemRequestDTO;
import rs.ac.bg.fon.silab.mock_exam.domain.pricelistitem.dto.PriceListItemResponseDTO;
import rs.ac.bg.fon.silab.mock_exam.domain.pricelistitem.entity.PriceListItem;
import rs.ac.bg.fon.silab.mock_exam.domain.pricelistitem.entity.PriceListItemId;

@Mapper(componentModel = "spring", uses = {PriceListService.class, CurrencyService.class, ExamService.class})
public interface PriceListItemMapper {
    @Mapping(source = "exam.name",target = "exam")
    @Mapping(source = "currency.code", target = "currency")
    @Mapping(source = "priceList.priceList.year", target = "priceListItemId.priceList")
    PriceListItem map(PriceListItemRequestDTO priceListItemDTO);

//    @Mapping(source = "priceListItemId.priceList", target = "priceList")
    //PriceListItemId map(PriceListItemIdRequestDTO priceListItemId);

    @Mapping(source = "priceListItemId.priceList", target = "id.priceList")
    @Mapping(source = "priceListItemId.id", target = "id.id")
    PriceListItemResponseDTO map(PriceListItem priceListItem);

}

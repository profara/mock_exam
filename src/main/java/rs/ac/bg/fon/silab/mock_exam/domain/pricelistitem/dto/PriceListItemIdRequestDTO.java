package rs.ac.bg.fon.silab.mock_exam.domain.pricelistitem.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import rs.ac.bg.fon.silab.mock_exam.domain.pricelist.dto.PriceListRequestDTO;

public record PriceListItemIdRequestDTO(
        @Valid
        @NotNull
        PriceListRequestDTO priceList
) {
}

package rs.ac.bg.fon.silab.mock_exam.domain.pricelistitem.dto;

import rs.ac.bg.fon.silab.mock_exam.domain.currency.dto.CurrencyResponseDTO;
import rs.ac.bg.fon.silab.mock_exam.domain.exam.dto.ExamResponseDTO;
import rs.ac.bg.fon.silab.mock_exam.domain.pricelist.dto.PriceListResponseDTO;

import java.math.BigDecimal;

public record PriceListItemResponseDTO(
        Long id,

        PriceListResponseDTO priceList,

        BigDecimal price,
        boolean privileged,
        CurrencyResponseDTO currency,
        ExamResponseDTO exam
) {
}

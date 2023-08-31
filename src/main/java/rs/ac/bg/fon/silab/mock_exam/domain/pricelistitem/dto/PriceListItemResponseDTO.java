package rs.ac.bg.fon.silab.mock_exam.domain.pricelistitem.dto;

import rs.ac.bg.fon.silab.mock_exam.domain.currency.entity.Currency;
import rs.ac.bg.fon.silab.mock_exam.domain.exam.entity.Exam;
import rs.ac.bg.fon.silab.mock_exam.domain.pricelistitem.entity.PriceListItemId;

import java.math.BigDecimal;

public record PriceListItemResponseDTO(
        PriceListItemIdResponseDTO id,
        BigDecimal price,
        boolean privileged,
        Currency currency,
        Exam exam
) {
}

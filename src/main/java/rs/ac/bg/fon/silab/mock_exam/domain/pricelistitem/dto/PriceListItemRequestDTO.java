package rs.ac.bg.fon.silab.mock_exam.domain.pricelistitem.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import rs.ac.bg.fon.silab.mock_exam.domain.currency.dto.CurrencyCodeRequestDTO;
import rs.ac.bg.fon.silab.mock_exam.domain.currency.dto.CurrencyRequestDTO;
import rs.ac.bg.fon.silab.mock_exam.domain.exam.dto.ExamRequestDTO;
import rs.ac.bg.fon.silab.mock_exam.domain.pricelist.dto.PriceListRequestDTO;

import java.math.BigDecimal;

public record PriceListItemRequestDTO(
        @Valid
        @NotNull(message = "Price list is mandatory")
        PriceListRequestDTO priceList,
        @NotNull(message = "Price is mandatory")
        @Min(value = 1)
        BigDecimal price,
        boolean privileged,
        @Valid
        @NotNull(message = "Currency is mandatory")
        CurrencyCodeRequestDTO currency,
        @Valid
        @NotNull(message = "Exam is mandatory")
        ExamRequestDTO exam
) {
}

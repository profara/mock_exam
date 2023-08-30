package rs.ac.bg.fon.silab.mock_exam.domain.currency.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import rs.ac.bg.fon.silab.mock_exam.domain.currency.dto.CurrencyRequestDTO;
import rs.ac.bg.fon.silab.mock_exam.domain.currency.dto.CurrencyResponseDTO;
import rs.ac.bg.fon.silab.mock_exam.domain.currency.entity.Currency;

@Mapper(componentModel = "spring")
public interface CurrencyMapper {
    Currency map(CurrencyRequestDTO currencyDTO);

    CurrencyResponseDTO map(Currency currency);

    void update(@MappingTarget Currency currency, CurrencyRequestDTO currencyDTO);
}

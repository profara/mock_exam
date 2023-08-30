package rs.ac.bg.fon.silab.mock_exam.domain.currency.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import rs.ac.bg.fon.silab.mock_exam.domain.currency.dto.CurrencyRequestDTO;
import rs.ac.bg.fon.silab.mock_exam.domain.currency.dto.CurrencyResponseDTO;
import rs.ac.bg.fon.silab.mock_exam.domain.currency.entity.Currency;
import rs.ac.bg.fon.silab.mock_exam.domain.currency.mapper.CurrencyMapper;
import rs.ac.bg.fon.silab.mock_exam.domain.currency.repository.CurrencyRepository;
import rs.ac.bg.fon.silab.mock_exam.infrastructure.exception.EntityNotFoundException;

@Service
public class CurrencyServiceImpl implements CurrencyService{

    private final CurrencyRepository currencyRepository;
    private final CurrencyMapper mapper;

    public CurrencyServiceImpl(CurrencyRepository currencyRepository, CurrencyMapper mapper) {
        this.currencyRepository = currencyRepository;
        this.mapper = mapper;
    }

    @Override
    public CurrencyResponseDTO save(CurrencyRequestDTO currencyDTO) {
        Currency currency = mapper.map(currencyDTO);

        currencyRepository.save(currency);

        return mapper.map(currency);
    }

    @Override
    public CurrencyResponseDTO getById(Long id) {
        var currency = currencyRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(Currency.class.getSimpleName(),"id", id));

        return mapper.map(currency);
    }

    @Override
    public Page<CurrencyResponseDTO> get(Pageable pageable) {
        return currencyRepository.findAll(pageable).map(mapper::map);
    }

    @Override
    public void delete(Long id) {
        if(!currencyRepository.existsById(id)){
            throw new EntityNotFoundException(Currency.class.getSimpleName(), "id", id);
        }

        currencyRepository.deleteById(id);
    }

    @Override
    public CurrencyResponseDTO update(Long id, CurrencyRequestDTO currencyDTO) {
        var currency = currencyRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(Currency.class.getSimpleName(),"id",id));

        mapper.update(currency,currencyDTO);

        return mapper.map(currency);
    }
}

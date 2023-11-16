package rs.ac.bg.fon.silab.mock_exam.domain.currency.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import rs.ac.bg.fon.silab.mock_exam.domain.currency.dto.CurrencyRequestDTO;
import rs.ac.bg.fon.silab.mock_exam.domain.currency.dto.CurrencyResponseDTO;
import rs.ac.bg.fon.silab.mock_exam.domain.currency.entity.Currency;
import rs.ac.bg.fon.silab.mock_exam.domain.currency.mapper.CurrencyMapper;
import rs.ac.bg.fon.silab.mock_exam.domain.currency.repository.CurrencyRepository;
import rs.ac.bg.fon.silab.mock_exam.infrastructure.exception.EntityNotFoundException;

/**
 * Implementation of the CurrencyService interface.
 * Handles business logic related to currency management, such as finding, saving, updating, and deleting currencies.
 */
@Service
public class CurrencyServiceImpl implements CurrencyService{

    private final CurrencyRepository currencyRepository;
    private final CurrencyMapper mapper;

    /**
     * Constructs a new CurrencyServiceImpl with necessary dependencies.
     *
     * @param currencyRepository the repository for currency data access
     * @param mapper the mapper for converting between entity and DTO
     */
    public CurrencyServiceImpl(CurrencyRepository currencyRepository, CurrencyMapper mapper) {
        this.currencyRepository = currencyRepository;
        this.mapper = mapper;
    }

    @Override
    public Currency findById(Long id) {
        return currencyRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(Currency.class.getSimpleName(),"id", id));
    }

    @Override
    @Transactional(readOnly = true)
    public Currency findByCode(String code) {
        return currencyRepository.findByCode(code);
    }

    @Override
    @Transactional
    public CurrencyResponseDTO save(CurrencyRequestDTO currencyDTO) {
        Currency currency = mapper.map(currencyDTO);

        currencyRepository.save(currency);

        return mapper.map(currency);
    }

    @Override
    @Transactional(readOnly = true)
    public CurrencyResponseDTO getById(Long id) {
        var currency = currencyRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(Currency.class.getSimpleName(),"id", id));

        return mapper.map(currency);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<CurrencyResponseDTO> get(Pageable pageable) {
        return currencyRepository.findAll(pageable).map(mapper::map);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        if(!currencyRepository.existsById(id)){
            throw new EntityNotFoundException(Currency.class.getSimpleName(), "id", id);
        }

        currencyRepository.deleteById(id);
    }

    @Override
    @Transactional
    public CurrencyResponseDTO update(Long id, CurrencyRequestDTO currencyDTO) {
        var currency = currencyRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(Currency.class.getSimpleName(),"id",id));

        mapper.update(currency,currencyDTO);

        currencyRepository.save(currency);

        return mapper.map(currency);
    }
}

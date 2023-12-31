package rs.ac.bg.fon.silab.mock_exam.domain.pricelist.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import rs.ac.bg.fon.silab.mock_exam.domain.pricelist.dto.PriceListRequestDTO;
import rs.ac.bg.fon.silab.mock_exam.domain.pricelist.dto.PriceListResponseDTO;
import rs.ac.bg.fon.silab.mock_exam.domain.pricelist.entity.PriceList;
import rs.ac.bg.fon.silab.mock_exam.domain.pricelist.mapper.PriceListMapper;
import rs.ac.bg.fon.silab.mock_exam.domain.pricelist.repository.PriceListRepository;
import rs.ac.bg.fon.silab.mock_exam.infrastructure.exception.EntityNotFoundException;

import java.time.Year;

/**
 * Implementation of the PriceListService interface.
 * Handles business logic related to price list management, such as finding, saving, updating, and deleting price lists.
 */
@Service
public class PriceListServiceImpl implements PriceListService{

    private final PriceListRepository priceListRepository;
    private final PriceListMapper mapper;

    /**
     * Constructs a new PriceListServiceImpl with necessary dependencies.
     *
     * @param priceListRepository the repository for price list data access
     * @param mapper the mapper for converting between entity and DTO
     */
    public PriceListServiceImpl(PriceListRepository priceListRepository, PriceListMapper mapper) {
        this.priceListRepository = priceListRepository;
        this.mapper = mapper;
    }

    @Override
    @Transactional(readOnly = true)
    public PriceList findByYear(Year year) {
        return priceListRepository.findByYear(year);
    }

    @Override
    @Transactional
    public PriceListResponseDTO save(PriceListRequestDTO priceListDTO) {
        PriceList priceList = mapper.map(priceListDTO);

        priceListRepository.save(priceList);

        return mapper.map(priceList);
    }

    @Override
    @Transactional(readOnly = true)
    public PriceListResponseDTO getById(Long id) {
        var priceList = priceListRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(PriceList.class.getSimpleName(), "id", id));

        return mapper.map(priceList);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<PriceListResponseDTO> get(Pageable pageable) {
        return priceListRepository.findAll(pageable).map(mapper::map);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        if(!priceListRepository.existsById(id)){
            throw new EntityNotFoundException(PriceList.class.getSimpleName(), "id", id);
        }

        priceListRepository.deleteById(id);
    }

    @Override
    @Transactional
    public PriceListResponseDTO update(Long id, PriceListRequestDTO priceListDTO) {
        var priceList = priceListRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(PriceList.class.getSimpleName(), "id", id));

        mapper.update(priceList, priceListDTO);

        priceListRepository.save(priceList);

        return mapper.map(priceList);
    }
}

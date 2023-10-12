package rs.ac.bg.fon.silab.mock_exam.domain.pricelistitem.service;

import jakarta.persistence.EntityManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import rs.ac.bg.fon.silab.mock_exam.domain.pricelistitem.dto.PriceListItemCriteriaRequestDTO;
import rs.ac.bg.fon.silab.mock_exam.domain.pricelistitem.dto.PriceListItemRequestDTO;
import rs.ac.bg.fon.silab.mock_exam.domain.pricelistitem.dto.PriceListItemResponseDTO;
import rs.ac.bg.fon.silab.mock_exam.domain.pricelistitem.entity.PriceListItem;
import rs.ac.bg.fon.silab.mock_exam.domain.pricelistitem.mapper.PriceListItemMapper;
import rs.ac.bg.fon.silab.mock_exam.domain.pricelistitem.repository.PriceListItemRepository;
import rs.ac.bg.fon.silab.mock_exam.infrastructure.exception.EntityNotFoundException;

import java.time.Year;
import java.util.List;

@Service
public class PriceListItemServiceImpl implements PriceListItemService{

    private final PriceListItemRepository priceListItemRepository;
    private final PriceListItemMapper mapper;

    public PriceListItemServiceImpl(PriceListItemRepository priceListItemRepository, PriceListItemMapper mapper) {
        this.priceListItemRepository = priceListItemRepository;
        this.mapper = mapper;
    }

    @Override
    @Transactional
    public PriceListItemResponseDTO save(PriceListItemRequestDTO priceListItemDTO) {
        PriceListItem priceListItem = mapper.map(priceListItemDTO);

        priceListItemRepository.save(priceListItem);

        return mapper.map(priceListItem);
    }

    @Override
    @Transactional(readOnly = true)
    public PriceListItemResponseDTO getById(Long id) {
        var priceListItem = priceListItemRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(PriceListItem.class.getSimpleName(), "id", id));

        return mapper.map(priceListItem);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<PriceListItemResponseDTO> get(Pageable pageable) {
        return priceListItemRepository.findAll(pageable).map(mapper::map);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        if(!priceListItemRepository.existsById(id)){
            throw new EntityNotFoundException(PriceListItem.class.getSimpleName(), "id", id);
        }

        priceListItemRepository.deleteById(id);
    }

    @Override
    @Transactional
    public PriceListItemResponseDTO update(Long id, PriceListItemRequestDTO priceListItemDTO) {
        var priceListItem = priceListItemRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(PriceListItem.class.getSimpleName(), "id", id));

        mapper.update(priceListItem, priceListItemDTO);

        priceListItemRepository.save(priceListItem);

        return mapper.map(priceListItem);
    }

    @Override
    public List<PriceListItem> findByPriceList(Long id) {
        return priceListItemRepository.findByPriceList_Id(id);
    }

    @Override
    public PriceListItemResponseDTO getByCriteria(PriceListItemCriteriaRequestDTO criteriaDTO) {
        PriceListItem criteria = mapper.criteria(criteriaDTO);

        var priceListItem = priceListItemRepository.findByExamAndPriceListAndPrivilegedAndCurrency(
                criteria.getExam(),
                criteria.getPriceList(),
                criteria.isPrivileged(),
                criteria.getCurrency()
        ).orElseThrow(() -> new EntityNotFoundException(PriceListItem.class.getSimpleName(), "criteria", criteria));

        return mapper.map(priceListItem);


    }
}

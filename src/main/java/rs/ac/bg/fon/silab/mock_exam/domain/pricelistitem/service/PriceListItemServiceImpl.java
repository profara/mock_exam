package rs.ac.bg.fon.silab.mock_exam.domain.pricelistitem.service;

import jakarta.persistence.EntityManager;
import org.springframework.stereotype.Service;
import rs.ac.bg.fon.silab.mock_exam.domain.pricelistitem.dto.PriceListItemRequestDTO;
import rs.ac.bg.fon.silab.mock_exam.domain.pricelistitem.dto.PriceListItemResponseDTO;
import rs.ac.bg.fon.silab.mock_exam.domain.pricelistitem.entity.PriceListItem;
import rs.ac.bg.fon.silab.mock_exam.domain.pricelistitem.mapper.PriceListItemMapper;
import rs.ac.bg.fon.silab.mock_exam.domain.pricelistitem.repository.PriceListItemRepository;

@Service
public class PriceListItemServiceImpl implements PriceListItemService{

    private final PriceListItemRepository priceListItemRepository;
    private final PriceListItemMapper mapper;

    public PriceListItemServiceImpl(PriceListItemRepository priceListItemRepository, PriceListItemMapper mapper) {
        this.priceListItemRepository = priceListItemRepository;
        this.mapper = mapper;
    }

    @Override
    public PriceListItemResponseDTO save(PriceListItemRequestDTO priceListItemDTO) {
        PriceListItem priceListItem = mapper.map(priceListItemDTO);

        priceListItemRepository.save(priceListItem);

        priceListItemRepository.flush();

        return mapper.map(priceListItem);
    }
}

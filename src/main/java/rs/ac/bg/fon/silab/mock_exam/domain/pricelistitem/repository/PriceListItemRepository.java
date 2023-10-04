package rs.ac.bg.fon.silab.mock_exam.domain.pricelistitem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import rs.ac.bg.fon.silab.mock_exam.domain.pricelistitem.entity.PriceListItem;

import java.util.List;

public interface PriceListItemRepository extends JpaRepository<PriceListItem, Long> {

    List<PriceListItem> findByPriceList_Id(Long priceListId);

}

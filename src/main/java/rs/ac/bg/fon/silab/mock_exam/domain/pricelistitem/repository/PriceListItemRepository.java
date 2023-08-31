package rs.ac.bg.fon.silab.mock_exam.domain.pricelistitem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import rs.ac.bg.fon.silab.mock_exam.domain.pricelistitem.entity.PriceListItem;
import rs.ac.bg.fon.silab.mock_exam.domain.pricelistitem.entity.PriceListItemId;

public interface PriceListItemRepository extends JpaRepository<PriceListItem, PriceListItemId> {

}

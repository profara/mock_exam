package rs.ac.bg.fon.silab.mock_exam.domain.pricelist.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import rs.ac.bg.fon.silab.mock_exam.domain.pricelist.entity.PriceList;
import rs.ac.bg.fon.silab.mock_exam.domain.pricelistitem.entity.PriceListItem;

import java.time.Year;

public interface PriceListRepository extends JpaRepository<PriceList, Long> {
    PriceList findByYear(Year year);

}

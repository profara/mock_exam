package rs.ac.bg.fon.silab.mock_exam.domain.pricelist.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import rs.ac.bg.fon.silab.mock_exam.domain.pricelist.entity.PriceList;

public interface PriceListRepository extends JpaRepository<PriceList, Long> {
}

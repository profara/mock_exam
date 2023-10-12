package rs.ac.bg.fon.silab.mock_exam.domain.pricelistitem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import rs.ac.bg.fon.silab.mock_exam.domain.currency.entity.Currency;
import rs.ac.bg.fon.silab.mock_exam.domain.exam.entity.Exam;
import rs.ac.bg.fon.silab.mock_exam.domain.pricelist.entity.PriceList;
import rs.ac.bg.fon.silab.mock_exam.domain.pricelistitem.entity.PriceListItem;

import java.util.List;
import java.util.Optional;

public interface PriceListItemRepository extends JpaRepository<PriceListItem, Long> {

    List<PriceListItem> findByPriceList_Id(Long priceListId);

    Optional<PriceListItem> findByExamAndPriceListAndPrivilegedAndCurrency(
            Exam exam,
            PriceList priceList,
            Boolean privileged,
            Currency currency
    );

}

package rs.ac.bg.fon.silab.mock_exam.domain.pricelistitem.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import rs.ac.bg.fon.silab.mock_exam.domain.pricelistitem.dto.PriceListItemCriteriaRequestDTO;
import rs.ac.bg.fon.silab.mock_exam.domain.pricelistitem.dto.PriceListItemRequestDTO;
import rs.ac.bg.fon.silab.mock_exam.domain.pricelistitem.dto.PriceListItemResponseDTO;
import rs.ac.bg.fon.silab.mock_exam.domain.pricelistitem.entity.PriceListItem;

import java.time.Year;
import java.util.List;

/**
 * Interface for managing price list items.
 * Provides operations such as saving, updating, deleting, and retrieving price list items.
 */
public interface PriceListItemService {

    /**
     * Saves a new PriceListItem or updates an existing one based on the provided DTO.
     *
     * @param priceListItemDTO the DTO containing price list item details
     * @return the saved or updated PriceListItem as a DTO
     */
    PriceListItemResponseDTO save(PriceListItemRequestDTO priceListItemDTO);

    /**
     * Retrieves a PriceListItem by its ID.
     *
     * @param id the ID of the PriceListItem to retrieve
     * @return the found PriceListItem as a DTO
     * @throws rs.ac.bg.fon.silab.mock_exam.infrastructure.exception.EntityNotFoundException if the PriceListItem is not found
     */
    PriceListItemResponseDTO getById(Long id);

    /**
     * Retrieves a paginated list of price list items.
     *
     * @param pageable the pagination information
     * @return a page of PriceListItemResponseDTO objects
     */
    Page<PriceListItemResponseDTO> get(Pageable pageable);

    /**
     * Deletes a PriceListItem by its ID.
     *
     * @param id the ID of the PriceListItem to delete
     * @throws rs.ac.bg.fon.silab.mock_exam.infrastructure.exception.EntityNotFoundException if PriceListItem with entered ID doesn't exist
     */
    void delete(Long id);

    /**
     * Updates an existing PriceListItem with new details provided in the DTO.
     *
     * @param id the ID of the PriceListItem to update
     * @param priceListItemDTO the DTO containing updated details
     * @return the updated PriceListItem as a DTO
     * @throws rs.ac.bg.fon.silab.mock_exam.infrastructure.exception.EntityNotFoundException if PriceListItem with entered ID doesn't exist
     */
    PriceListItemResponseDTO update(Long id, PriceListItemRequestDTO priceListItemDTO);

    /**
     * Finds PriceListItems by the ID of their associated PriceList.
     *
     * @param id the ID of the PriceList
     * @return a list of PriceListItem entities
     */
    List<PriceListItem> findByPriceList(Long id);

    /**
     * Retrieves a PriceListItem based on specified criteria.
     *
     * @param criteriaDTO the DTO containing search criteria
     * @return the PriceListItem that matches the criteria as a DTO
     * @throws rs.ac.bg.fon.silab.mock_exam.infrastructure.exception.EntityNotFoundException if no PriceListItem matches the criteria
     */
    PriceListItemResponseDTO getByCriteria(PriceListItemCriteriaRequestDTO criteriaDTO);
}

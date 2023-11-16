package rs.ac.bg.fon.silab.mock_exam.domain.pricelist.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import rs.ac.bg.fon.silab.mock_exam.domain.pricelist.dto.PriceListRequestDTO;
import rs.ac.bg.fon.silab.mock_exam.domain.pricelist.dto.PriceListResponseDTO;
import rs.ac.bg.fon.silab.mock_exam.domain.pricelist.entity.PriceList;

import java.time.Year;

/**
 * Service interface for managing price lists.
 * Offers functionalities such as retrieving, creating, updating, and deleting price lists.
 */
public interface PriceListService {

    /**
     * Finds a price list by its associated year.
     *
     * @param year the year of the price list to find
     * @return the found PriceList entity
     */
    PriceList findByYear(Year year);

    /**
     * Saves a new price list or updates an existing one based on the provided DTO.
     *
     * @param priceListDTO the DTO containing price list details
     * @return the saved or updated price list as a DTO
     */
    PriceListResponseDTO save(PriceListRequestDTO priceListDTO);

    /**
     * Retrieves a price list by its ID.
     *
     * @param id the ID of the price list to retrieve
     * @return the found PriceList entity
     * @throws rs.ac.bg.fon.silab.mock_exam.infrastructure.exception.EntityNotFoundException if the PriceList is not found
     */
    PriceListResponseDTO getById(Long id);

    /**
     * Retrieves a paginated list of price lists.
     *
     * @param pageable the pagination information
     * @return a page of PriceListResponseDTO objects
     */
    Page<PriceListResponseDTO> get(Pageable pageable);

    /**
     * Deletes a price list by its ID.
     *
     * @param id the ID of the price list to delete
     * @throws rs.ac.bg.fon.silab.mock_exam.infrastructure.exception.EntityNotFoundException if PriceList with entered ID doesn't exist
     */
    void delete(Long id);

    /**
     * Updates an existing price list with new details provided in the DTO.
     *
     * @param id the ID of the price list to update
     * @param priceListDTO the DTO containing updated details
     * @return the updated price list as a DTO
     * @throws rs.ac.bg.fon.silab.mock_exam.infrastructure.exception.EntityNotFoundException if PriceList with entered ID doesn't exist
     */
    PriceListResponseDTO update(Long id, PriceListRequestDTO priceListDTO);
}

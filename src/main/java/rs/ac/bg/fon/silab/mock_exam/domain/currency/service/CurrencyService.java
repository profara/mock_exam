package rs.ac.bg.fon.silab.mock_exam.domain.currency.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import rs.ac.bg.fon.silab.mock_exam.domain.currency.dto.CurrencyRequestDTO;
import rs.ac.bg.fon.silab.mock_exam.domain.currency.dto.CurrencyResponseDTO;
import rs.ac.bg.fon.silab.mock_exam.domain.currency.entity.Currency;

/**
 * Service interface for managing currencies.
 * Offers functionalities such as retrieving, creating, updating, and deleting currency records.
 */
public interface CurrencyService {

    /**
     * Retrieves a currency by its ID.
     *
     * @param id the ID of the currency to retrieve
     * @return the found Currency entity
     * @throws rs.ac.bg.fon.silab.mock_exam.infrastructure.exception.EntityNotFoundException if the Currency is not found
     */
    Currency findById(Long id);

    /**
     * Retrieves a currency by its code.
     *
     * @param code the code of the currency to retrieve
     * @return the found Currency entity
     */
    Currency findByCode(String code);

    /**
     * Saves a new currency or updates an existing one based on the provided DTO.
     *
     * @param currencyDTO the DTO containing currency details
     * @return the saved or updated currency as a DTO
     */
    CurrencyResponseDTO save(CurrencyRequestDTO currencyDTO);

    /**
     * Retrieves a currency by its ID and returns it as a DTO.
     *
     * @param id the ID of the currency to retrieve
     * @return the currency as a DTO
     * @throws rs.ac.bg.fon.silab.mock_exam.infrastructure.exception.EntityNotFoundException if the currency is not found
     */
    CurrencyResponseDTO getById(Long id);

    /**
     * Retrieves a paginated list of currencies.
     *
     * @param pageable the pagination information
     * @return a page of CurrencyResponseDTO objects
     */
    Page<CurrencyResponseDTO> get(Pageable pageable);

    /**
     * Deletes a currency by its ID.
     *
     * @param id the ID of the currency to delete
     * @throws rs.ac.bg.fon.silab.mock_exam.infrastructure.exception.EntityNotFoundException if the currency with entered ID does not exist
     */
    void delete(Long id);

    /**
     * Updates an existing currency with new details provided in the DTO.
     *
     * @param id the ID of the currency to update
     * @param currencyDTO the DTO containing updated details
     * @return the updated currency as a DTO
     * @throws rs.ac.bg.fon.silab.mock_exam.infrastructure.exception.EntityNotFoundException if the currency with entered ID does not exist
     */
    CurrencyResponseDTO update(Long id, CurrencyRequestDTO currencyDTO);
}

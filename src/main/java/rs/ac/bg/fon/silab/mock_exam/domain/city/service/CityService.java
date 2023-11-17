package rs.ac.bg.fon.silab.mock_exam.domain.city.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import rs.ac.bg.fon.silab.mock_exam.domain.city.dto.CityRequestDTO;
import rs.ac.bg.fon.silab.mock_exam.domain.city.dto.CityRequestUpdateDTO;
import rs.ac.bg.fon.silab.mock_exam.domain.city.dto.CityResponseDTO;
import rs.ac.bg.fon.silab.mock_exam.domain.city.entity.City;

import java.util.List;

/**
 * Service interface for managing cities.
 * Provides functionalities such as retrieving, creating, updating, and deleting city records.
 */
public interface CityService {

    /**
     * Retrieves a city by its ZIP code.
     *
     * @param zipCode the ZIP code of the city to retrieve
     * @return the found City entity
     * @throws rs.ac.bg.fon.silab.mock_exam.infrastructure.exception.EntityNotFoundException if the city is not found
     */
    City getById(Long zipCode);

    /**
     * Saves a new city or updates an existing one based on the provided DTO.
     *
     * @param cityDTO the DTO containing city details
     * @return the saved or updated city as a DTO
     */
    CityResponseDTO save(CityRequestDTO cityDTO);

    /**
     * Retrieves a city by its ZIP code and returns it as a DTO.
     *
     * @param zipCode the ZIP code of the city to retrieve
     * @return the city as a DTO
     * @throws rs.ac.bg.fon.silab.mock_exam.infrastructure.exception.EntityNotFoundException if the city is not found
     */
    CityResponseDTO getByZipCode(Long zipCode);

    /**
     * Retrieves a paginated list of cities.
     *
     * @param pageable the pagination information
     * @return a page of CityResponseDTO objects
     */
    Page<CityResponseDTO> get(Pageable pageable);

    /**
     * Deletes a city by its ZIP code.
     *
     * @param zipCode the ZIP code of the city to delete
     * @throws rs.ac.bg.fon.silab.mock_exam.infrastructure.exception.EntityNotFoundException if the city with entered zip code does not exist
     */
    void delete(Long zipCode);

    /**
     * Updates an existing city with new details provided in the DTO.
     *
     * @param zipCode the ZIP code of the city to update
     * @param cityDTO the DTO containing updated details
     * @return the updated city as a DTO
     * @throws rs.ac.bg.fon.silab.mock_exam.infrastructure.exception.EntityNotFoundException if the city with entered zip code does not exist
     */
    CityResponseDTO update(Long zipCode, CityRequestUpdateDTO cityDTO);

    /**
     * Retrieves a list of all cities.
     *
     * @return a list of CityResponseDTO objects
     */
    List<CityResponseDTO> getAll();
}

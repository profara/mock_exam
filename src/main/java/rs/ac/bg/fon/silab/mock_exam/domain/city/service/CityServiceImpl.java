package rs.ac.bg.fon.silab.mock_exam.domain.city.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import rs.ac.bg.fon.silab.mock_exam.domain.city.dto.CityRequestDTO;
import rs.ac.bg.fon.silab.mock_exam.domain.city.dto.CityRequestUpdateDTO;
import rs.ac.bg.fon.silab.mock_exam.domain.city.dto.CityResponseDTO;
import rs.ac.bg.fon.silab.mock_exam.domain.city.entity.City;
import rs.ac.bg.fon.silab.mock_exam.domain.city.mapper.CityMapper;
import rs.ac.bg.fon.silab.mock_exam.domain.city.repository.CityRepository;
import rs.ac.bg.fon.silab.mock_exam.domain.typeofschool.entity.TypeOfSchool;
import rs.ac.bg.fon.silab.mock_exam.infrastructure.exception.EntityNotFoundException;

@Service
public class CityServiceImpl implements CityService{

    private final CityRepository cityRepository;
    private final CityMapper mapper;

    public CityServiceImpl(CityRepository cityRepository, CityMapper mapper) {
        this.cityRepository = cityRepository;
        this.mapper = mapper;
    }

    @Override
    @Transactional(readOnly = true)
    public City getById(Long zipCode) {
        var city = cityRepository.findById(zipCode)
                .orElseThrow(() -> new EntityNotFoundException(City.class.getSimpleName(), "zip code", zipCode));

        return city;
    }

    @Override
    @Transactional
    public CityResponseDTO save(CityRequestDTO cityDTO) {
        City city = mapper.map(cityDTO);

        cityRepository.save(city);

        return mapper.map(city);
    }

    @Override
    @Transactional(readOnly = true)
    public CityResponseDTO getByZipCode(Long zipCode) {
        var city = cityRepository.findById(zipCode)
                .orElseThrow(() -> new EntityNotFoundException(City.class.getSimpleName(),"zip code",zipCode));

        return mapper.map(city);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<CityResponseDTO> get(Pageable pageable) {
        return cityRepository.findAll(pageable).map(mapper::map);
    }

    @Override
    @Transactional
    public void delete(Long zipCode) {
        if(!cityRepository.existsById(zipCode)){
            throw new EntityNotFoundException(City.class.getSimpleName(),"zip code",zipCode);
        }

        cityRepository.deleteById(zipCode);
    }

    @Override
    @Transactional
    public CityResponseDTO update(Long zipCode, CityRequestUpdateDTO cityDTO) {
        var city = cityRepository.findById(zipCode)
                .orElseThrow(() -> new EntityNotFoundException(City.class.getSimpleName(),"zip code", zipCode));

        mapper.update(city, cityDTO);

        cityRepository.save(city);

        return mapper.map(city);
    }
}

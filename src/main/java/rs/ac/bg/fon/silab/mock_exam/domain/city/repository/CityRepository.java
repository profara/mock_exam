package rs.ac.bg.fon.silab.mock_exam.domain.city.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import rs.ac.bg.fon.silab.mock_exam.domain.city.entity.City;

public interface CityRepository extends JpaRepository<City, Long> {
}

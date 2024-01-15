package softuni.exam.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import softuni.exam.models.entities.city.City;

@Repository
public interface CityRepository extends JpaRepository<City, Long> {

}

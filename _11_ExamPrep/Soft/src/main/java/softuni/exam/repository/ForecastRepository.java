package softuni.exam.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import softuni.exam.models.entities.forecast.Forecast;

@Repository
public interface ForecastRepository extends JpaRepository<Forecast, Long> {

}

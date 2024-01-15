package softuni.exam.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import softuni.exam.models.entities.forecast.Forecast;
import softuni.exam.models.entities.forecast.dto.ForecastExportDto;

import java.util.List;

@Repository
public interface ForecastRepository extends JpaRepository<Forecast, Long> {

    @Query("select f.id, c.name, f.minTemperature, f.maxTemperature, f.sunrise, f.sunset " +
            "from Forecast as f " +
            "inner join City as c on f.city = c " +
            "where c.population < 150000 and " +
            "f.dayOfWeek = 'SUNDAY'")
    List<Object[]> getAllForecastsForSundayWithConstraints();
}

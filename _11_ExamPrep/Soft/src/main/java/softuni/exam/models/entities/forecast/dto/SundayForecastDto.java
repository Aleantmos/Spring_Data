package softuni.exam.models.entities.forecast.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SundayForecastDto {
    private String cityName;
    private Double minTemp;
    private Double maxTemp;
    private LocalTime sunrise;
    private LocalTime sunset;
}

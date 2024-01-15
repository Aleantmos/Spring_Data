package softuni.exam.models.entities.forecast.dto;

import lombok.*;

import java.time.LocalTime;
import java.util.Locale;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ForecastExportDto {
    private Long id;
    private String cityName;
    private double minTemp;
    private double maxTemp;
    private LocalTime sunrise;
    private LocalTime sunset;

    @Override
    public String toString() {
        return String.format(Locale.US, """
                City: %s:
                   \t\t-min temperature: %.2f
                   \t\t--max temperature: %.2f
                   \t\t---sunrise: %s
                   \t\t----sunset: %s
                """, this.cityName, this.minTemp,
                this.maxTemp, sunrise.toString(), sunset.toString());
    }
}

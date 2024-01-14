package softuni.exam.models.entities.city.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CityImportDto {
    private String cityName;
    private String description;
    private Long population;
    private Long country;

}

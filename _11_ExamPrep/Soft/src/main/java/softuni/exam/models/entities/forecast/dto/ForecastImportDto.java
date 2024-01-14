package softuni.exam.models.entities.forecast.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import softuni.exam.util.LocalTimeAdapter;


import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.time.LocalTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@XmlRootElement(name = "forecast")
@XmlAccessorType(XmlAccessType.FIELD)
public class ForecastImportDto {
    @XmlElement(name = "day_of_week")
    private String dayOfWeek;
    @XmlElement(name = "max_temperature")
    private Double maxTemperature;
    @XmlElement(name = "min_temperature")
    private Double minTemperature;
    @XmlElement
    @XmlJavaTypeAdapter(LocalTimeAdapter.class)
    private LocalTime sunrise;
    @XmlElement
    @XmlJavaTypeAdapter(LocalTimeAdapter.class)
    private LocalTime sunset;
    @XmlElement
    private Long city;
}
